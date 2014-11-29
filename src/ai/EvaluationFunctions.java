package ai;


import logic.*;

import java.util.ArrayList;

public class EvaluationFunctions {

    /* Static Evaluation Function */
    public static double SEF(Universe u, Player p) {
        double result = 1;
        int amountOfPlanets = 0;
        for (SpaceShip s: p.getShips()) {
            for (Planet planet : u.getPlanets()) {
                if (p.equals(planet.getMaster())) {
                    amountOfPlanets++;
                    continue;
                }
                result -= u.distance(s.getX(), s.getY(), planet.getX(), planet.getY()) / 500;
            }
        }
        result += 0.1 * p.getShips().size();

        /* Если нет планет, то их надо срочно захватить */
        if (amountOfPlanets == 0) {
            result /= 100;
        }
        result += amountOfPlanets / 5 + p.getShips().size() / 10;

        result *= p.getGold() / 100 + p.getIron() / 100;

        return result;
    }

    /* Move Ranging function */
    public static double MR(final Universe u, Player p, ArrayList<Action> a) {
        final Universe afterAction = new Universe(u);
        Player playerInNewUniverse;
        int i = 0;
        for (Player player: u.getPlayers()) {
            if (p.equals(player)) {
                break;
            }
            i++;
        }
        playerInNewUniverse = afterAction.getPlayers().get(i);
        for (Action action: a) {
            SpaceShip shipInNewUniverse;
            shipInNewUniverse = playerInNewUniverse.getNearestShip(action.ship.getX(), action.ship.getY());

            switch (action.action) {
                case MOVE_TO_EMPTY_PLANET:
                case MOVE_TO_ENEMY_PLANET:
                case MOVE_TO_ENEMY_SHIP:
                    shipInNewUniverse.setX(action.target.getX());
                    shipInNewUniverse.setY(action.target.getY());
                    break;
            }
        }
        for (int j = 0; j < 10; j++) {
            afterAction.tick();
        }
        return SEF(afterAction, playerInNewUniverse) - SEF(u, p);
    }

    /* Action Ranging function */
    public static double AR(Universe u, Player p, SpaceShip s, Action a) {
        double result = 3;

        double myDist = Universe.distance(s.getX(), s.getY(), a.target.getX(), a.target.getY());
        double enemyDist = 3;
        for (Player enemy: u.getPlayers()) {
            if (enemy.equals(p))
                continue;
            for (SpaceShip enemyShip: enemy.getShips()) {
                if (a.equals(enemyShip))
                    continue;
                double d = Universe.distance(enemyShip.getX(), enemyShip.getY()
                        , a.target.getX(), a.target.getY());
                if (d < enemyDist)
                    enemyDist = d;
            }
        }
        boolean hasPlanet = false;
        for (Planet planet: u.getPlanets()) {
            if (p.equals(planet.getMaster())) {
                hasPlanet = true;
                break;
            }
        }
        result += enemyDist - myDist;
        switch (a.action) {
            case MOVE_TO_EMPTY_PLANET:
                if (!hasPlanet)
                    result *= 10;
                break;
            case MOVE_TO_ENEMY_PLANET:
                if (!hasPlanet)
                    result *= 10;
                result *= 1.5;
                break;
            case MOVE_TO_ENEMY_SHIP:
                result *= s.getLive() / Properties.properties.SHIP_LIVE;
                break;
        }
        return result;
    }
}
