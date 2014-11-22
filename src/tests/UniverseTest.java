package tests;

import logic.Player;
import logic.SpaceShip;
import logic.Universe;

public class UniverseTest {

    public Boolean test() {
        int passed = 0, failed;
        System.out.println("-- Space ship test --");

        if (testGetNearest()) passed++; else System.out.println("GetNearest failed!");
        if (testBullets()) passed++;  else System.out.println("Bullets failed!");
        if (testMoveShip()) passed++; else System.out.println("MoveShip failed!");
        if (testPlanet()) passed++; else System.out.println("Planet failed!");
        if (testAttack()) passed++; else System.out.println("Attack failed!");
        failed = 5 - passed;
        System.out.println(passed + " - passed");
        System.out.println(failed + " - failed");
        System.out.println("-- End space ship test --");
        return passed == 5;
    }

    public Boolean testGetNearest() {
        Universe u = new Universe();
        return u.getNearestPlanet(0.05, -0.05).equals(u.getPlanets()[0]);
    }

    public Boolean testBullets() {
        Universe u = new Universe();
        SpaceShip s1 = new SpaceShip(0, 0, null, 1, u);
        SpaceShip s2 = new SpaceShip(0.05, 0.05, null, 11, u);
        u.makeBullet(s1, s2);
        return u.getBullets().size() > 0;
    }

    public Boolean testMoveShip() {
        Universe u = new Universe();
        SpaceShip s = u.getPlayers()[0].getShips().get(0);
        s.move(0, 0);
        for (int i = 0; i < 2000; i++) {
            u.tick();
        }
        return Math.abs(s.getX()) < 0.02;
    }

    public Boolean testPlanet() {
        Universe u = new Universe();
        u.getPlayers()[0].getShips().get(0).move(0, 0);
        for (int i = 0; i < 1000; i++) {
            u.tick();
        }
        return u.getPlanet(0, 0).getMaster().equals(u.getPlayers()[0]);
    }

    public Boolean testAttack() {
        Universe u = new Universe();
        Player p1 = u.getPlayers()[0];
        Player p2 = u.getPlayers()[1];
        for (int i = 0; i < 10; i++) {
            p1.addShip(new SpaceShip(0, 0, p1, i + 100, u));
        }
        SpaceShip s = new SpaceShip(0, 0, p2, 324, u);
        p2.addShip(s);
        for (int i = 0; i < 1000; i++) {
            u.tick();
        }
        return !s.isAlive();
    }
}
