package ai;


import logic.Planet;
import logic.Player;
import logic.SpaceShip;
import logic.Universe;

import java.util.ArrayList;

public class SingletonAI implements AIInterface {
    private Universe universe;
    private Player player;

    public SingletonAI(Universe u, Player p) {
        universe = u;
        player = p;
    }

    private ArrayList<Action> getActionsToShip(SpaceShip ship) {
        ArrayList<Action> actions = new ArrayList<Action>();
        for (Player p : universe.getPlayers()) {
            if (player.equals(p))
                continue;
            for (SpaceShip s: p.getShips()) {
                actions.add(new Action(Action.actions.MOVE_TO_ENEMY_SHIP, s));
            }
        }
        for (Planet p: universe.getPlanets()) {
            if (p.getMaster() != null && !p.getMaster().equals(player))
                actions.add(new Action(Action.actions.MOVE_TO_ENEMY_PLANET, p));
            else if (p.getMaster() == null)
                actions.add(new Action(Action.actions.MOVE_TO_EMPTY_PLANET, p));
        }
        return actions;
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

    private void planetAction(Planet p) {
        p.buildGoldFactory();
        p.buildIronFactory();
        p.buildAngar();
        p.makeShip();
    }

    @Override
    public void action() {
        for (SpaceShip s: player.getShips()) {
            ArrayList<Action> actions = getActionsToShip(s);
            if (actions.size() == 0) {
                universe.end();
                return;
            }
            Action maxAction = actions.get(0);
            for (Action a: actions) {
                if (EvaluationFunctions.AR(universe, player, s, a)
                        > EvaluationFunctions.AR(universe, player, s, maxAction)) {
                    maxAction = a;
                }
            }
            shipAction(s, maxAction);
        }
        for (Planet p: universe.getPlanets()) {
            if (p.getMaster() == null || !player.equals(p.getMaster()))
                continue;
            //planetAction(p);
            p.buildGoldFactory();
        }
        for (Planet p: universe.getPlanets()) {
            if (p.getMaster() == null || !player.equals(p.getMaster()))
                continue;
            //planetAction(p);
            p.buildIronFactory();
        }
        for (Planet p: universe.getPlanets()) {
            if (p.getMaster() == null || !player.equals(p.getMaster()))
                continue;
            //planetAction(p);
            p.buildAngar();
        }
        for (Planet p: universe.getPlanets()) {
            if (p.getMaster() == null || !player.equals(p.getMaster()))
                continue;
            //planetAction(p);
            p.makeShip();
        }
    }
}
