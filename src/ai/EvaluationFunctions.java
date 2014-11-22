package ai;


import logic.*;

import java.util.ArrayList;

public class EvaluationFunctions {

    /* Static Evaluation Function */
    public static double SEF(Universe u, Player p) {
        double result = 1;
        result *= 0.1 * p.getShips().size();
        int amountOfPlanets = 0;
        for (Planet planet: u.getPlanets()) {
            if (p.equals(planet.getMaster()))
                amountOfPlanets++;
        }

        for (Planet planet: u.getPlanets()) {
            for (SpaceShip s: p.getShips()) {
                result -= s.dist(planet) * 0.005;
            }
        }
        /* Если нет планет, то их надо срочно захватить */
        if (amountOfPlanets == 0) {
            result /= 100;
        }

        result *= p.getGold() / 100 + p.getIron() / 100;

        return result;
    }

    /* Move Ranging function */
    public static double MR(final Universe u, Player p, ArrayList<Action> a) {
        double result = 1;
        final Universe afterAction = new Universe(u);
        Player playerInNewUniverse;
        int i;
        for (i = 0; !u.getPlayers()[i].equals(p); i++)
            ;
        playerInNewUniverse = afterAction.getPlayers()[i];
        for (Action action: a) {
            SpaceShip shipInNewUniverse;
            int j;
            for (j = 0; !p.getShips().get(i).equals(action.ship); i++)
                ;
            shipInNewUniverse = playerInNewUniverse.getShips().get(i);

            switch (action.action) {
                case MOVE_TO_EMPTY_PLANET:
                case MOVE_TO_ENEMY_PLANET:
                case MOVE_TO_ENEMY_SHIP:
                    shipInNewUniverse.setX(action.target.getX());
                    shipInNewUniverse.setY(action.target.getY());
                    break;
            }
        }
        return SEF(afterAction, playerInNewUniverse) - SEF(u, p);
    }

    /* Action Ranging function */
    public static double AR(Universe u, Player p, SpaceShip s, Action a) {
        double result = 0;

        double myDist = Universe.distanse(s.getX(), s.getY(), a.target.getX(), a.target.getY());
        double enemyDist = 3;
        for (Player enemy: u.getPlayers()) {
            if (enemy.equals(p))
                continue;
            for (SpaceShip enemyShip: enemy.getShips()) {
                if (a.equals(enemyShip))
                    continue;
                double d = Universe.distanse(enemyShip.getX(), enemyShip.getY()
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
