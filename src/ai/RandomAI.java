package ai;


import logic.Planet;
import logic.Player;
import logic.SpaceShip;
import logic.Universe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ai.Action.actions.MOVE_TO_EMPTY_PLANET;

public class RandomAI implements AIInterface {

    private Universe universe;
    private Player player;
    private Random random;


    public RandomAI(Universe u, Player p) {
        universe = u;
        player = p;
        random = new Random();
    }

    private ArrayList<Planet> getEmptyPlanets() {
        ArrayList<Planet> pl = new ArrayList<Planet>();
        for (Planet p: universe.getPlanets()) {
            if (p.isEmpty()) {
                pl.add(p);
            }
        }
        return pl;
    }

    private ArrayList<Planet> getEnemyPlanets() {
        ArrayList<Planet> pl = new ArrayList<Planet>();
        for (Planet p: universe.getPlanets()) {
        if (p.getMaster() != null && !p.getMaster().equals(player)) {
                pl.add(p);
            }
        }
        return pl;
    }

    private ArrayList<SpaceShip> getEnemyShips() {
        ArrayList<SpaceShip> sl = new ArrayList<SpaceShip>();
        for (Player p: universe.getPlayers()) {
            if (p.equals(player)) {
                continue;
            }
            sl.addAll(p.getShips());
        }
        return sl;
    }

    private Action getAction(SpaceShip s) {
        Action a;
        switch (random.nextInt(3)) {
            case 0:
                a = new Action(MOVE_TO_EMPTY_PLANET);
                List<Planet> pl = getEmptyPlanets();
                if (pl.size() == 0)
                    break;
                a.target = pl.get(random.nextInt(pl.size()));
                return a;
            case 1:
                a = new Action(Action.actions.MOVE_TO_ENEMY_PLANET);
                pl = getEnemyPlanets();
                if (pl.size() == 0)
                    break;
                a.target = pl.get(random.nextInt(pl.size()));
                return a;
            case 2:
                a = new Action(Action.actions.MOVE_TO_ENEMY_SHIP);
                List<SpaceShip> sl = getEnemyShips();
                if (sl.size() == 0)
                    break;
                a.target = sl.get(random.nextInt(sl.size()));
                return a;
            case 3:
                a = new Action(Action.actions.STAY);
                return a;
        }
        a = new Action(Action.actions.MOVE_TO_ENEMY_SHIP);
        a.target = getEnemyShips().get(0);
        return a;
    }

    @Override
    public void action() {
        for (SpaceShip s: player.getShips()) {
            Action action = getAction(s);
            switch (action.action) {
                case MOVE_TO_EMPTY_PLANET:
                case MOVE_TO_ENEMY_PLANET:
                case MOVE_TO_ENEMY_SHIP:
                    s.move(action.target.getX(), action.target.getY());
                    break;
                case STAY:
                    break;
            }
        }
        for (Planet p: universe.getPlanets()) {
            if (!player.equals(p.getMaster()))
                continue;
            switch (random.nextInt(3)) {
                case 0:
                    p.buildAngar();
                    break;
                case 1:
                    p.buildGoldFactory();
                    break;
                case 2:
                    p.buildIronFactory();
                    break;
                case 3:
                    p.makeShip();
                    break;
             }
        }
    }
}
