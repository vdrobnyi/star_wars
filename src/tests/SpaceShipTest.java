package tests;

import logic.Player;
import logic.SpaceShip;

public class SpaceShipTest {
    public Boolean test() {
        int passed = 0, failed;
        System.out.println("-- Space ship test --");

        if (testIsAlive()) passed++; else System.out.println("IsAlive failed!");
        if (testMaster()) passed++;  else System.out.println("Master failed!");
        if (testX()) passed++; else System.out.println("X failed!");
        if (testY()) passed++; else System.out.println("Y failed!");
        failed = 4 - passed;
        System.out.println(passed + " - passed");
        System.out.println(failed + " - failed");
        System.out.println("-- End space ship test --");
        return passed == 4;
    }

    public Boolean testIsAlive() {
        SpaceShip s = new SpaceShip(0, 0, null, 123, null);
        return s.isAlive();
    }

    public Boolean testMaster() {
        Player p = new Player(1, null);
        SpaceShip s = new SpaceShip(0, 0, p, 123, null);
        return s.getMaster().equals(p);
    }

    public Boolean testX() {
        SpaceShip s = new SpaceShip(0.3, 0.5, null, 123, null);
        return Math.abs(s.getX() - 0.3) < 0.01;
    }

    public Boolean testY() {
        SpaceShip s = new SpaceShip(0.3, 0.5, null, 123, null);
        return Math.abs(s.getY() - 0.5) < 0.01;
    }
}
