package ai;

import logic.Planet;
import logic.Player;
import logic.SpaceShip;
import logic.Universe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TeamAI implements AIInterface {
    private Universe universe;
    private Player player;
    private int k = 2;

    private int[] maxMas;
    private int[] ind;
    private double maxVal = -10e10;


    public TeamAI(Universe u, Player p) {
        universe = u;
        player = p;
        k = 2;
    }

    public TeamAI(Universe u, Player p, int k) {
        universe = u;
        player = p;
        this.k = k;
    }

    private void planetAction(Planet p) {
        p.buildGoldFactory();
        p.buildIronFactory();
        p.buildAngar();
        p.makeShip();
    }

    private void shipAction(SpaceShip s, Action a) {
        switch (a.action) {
            case MOVE_TO_EMPTY_PLANET:
            case MOVE_TO_ENEMY_PLANET:
            case MOVE_TO_ENEMY_SHIP:
                s.move(a.target.getX(), a.target.getY());
                break;
            case STAY:
                break;
        }
    }

    private ArrayList<Action> getActionsToShip(SpaceShip ship) {
        ArrayList<Action> actions = new ArrayList<Action>();
        for (Player p : universe.getPlayers()) {
            if (player.equals(p))
                continue;
            for (SpaceShip s: p.getShips()) {
                actions.add(new Action(Action.actions.MOVE_TO_ENEMY_SHIP, s, ship));
            }
        }
        for (Planet p: universe.getPlanets()) {
            if (p.getMaster() != null && !p.getMaster().equals(player))
                actions.add(new Action(Action.actions.MOVE_TO_ENEMY_PLANET, p, ship));
            else if (p.getMaster() == null)
                actions.add(new Action(Action.actions.MOVE_TO_EMPTY_PLANET, p, ship));
        }
        return actions;
    }

    private void getMax(int l, HashMap<SpaceShip, List<Action>> actions) {
        maxVal = -10e10;
        if (l == player.getShips().size()) {
            ArrayList<Action> a = new ArrayList<Action>();
            for (int i = 0; i < player.getShips().size(); i++) {
                a.add(actions.get(player.getShips().get(i)).get(ind[i]));
            }
            if (EvaluationFunctions.MR(universe, player,  a) > maxVal) {
                for (int i = 0; i < player.getShips().size(); i++)
                    maxMas[i] = ind[i];
                maxVal = EvaluationFunctions.MR(universe, player,  a);
            }
        } else {
            for (int i = 0; i < k; i++) {
                ind[l] = i;
                getMax(l + 1, actions);
            }
        }
    }

    @Override
    public void action() {
        HashMap<SpaceShip, List<Action>> map = new HashMap<SpaceShip, List<Action>>();
        for (Planet p: universe.getPlanets()) {
            if (!player.equals(p.getMaster()))
                continue;
            planetAction(p);
        }
        for (final SpaceShip s: player.getShips()) {
            ArrayList<Action> actions = getActionsToShip(s);
            actions.sort(new Comparator<Action>() {
                @Override
                public int compare(Action o1, Action o2) {
                    if (EvaluationFunctions.AR(universe, player, s, o1) < EvaluationFunctions.AR(universe, player, s, o2))
                        return 1;
                    else if (EvaluationFunctions.AR(universe, player, s, o1) == EvaluationFunctions.AR(universe, player, s, o2))
                        return 0;
                    return 1;
                }
            });
            map.put(s, actions.subList(0, k));
        }
        maxMas = new int[player.getShips().size()];
        ind = new int[player.getShips().size()];

        getMax(0, map);
        int i = 0;
        for (SpaceShip s: player.getShips()) {
            Action a = map.get(s).get(maxMas[i]);
            shipAction(s, a);
            i++;
        }
    }
}
