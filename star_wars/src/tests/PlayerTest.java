package tests;

import logic.Player;
import logic.SpaceShip;

public class PlayerTest {
    public Boolean test() {
        int passed = 0, failed;
        System.out.println("-- Player test --");
        if (testAddShip()) passed++; else System.out.println("AddShip failed!");
        if (testRemoveShip()) passed++; else System.out.println("Remove ship failed!");
        if (testGetNearestShipOne()) passed++; else System.out.println("Nearest 1 failed!");
        if (testGetNearestShipTwo()) passed++; else System.out.println("Nearest 2 failed!");
        if (testGold()) passed++; else System.out.println("Gold failed!");
        if (testIron()) passed++; else System.out.println("Iron failed!");

        failed = 6 - passed;
        System.out.println(passed + " - passed");
        System.out.println(failed + " - failed");
        System.out.println("-- End player test --");
        return passed == 6;
    }

    public Boolean testGold() {
        Player p = new Player(123, null);
        p.addGold(200);
        return Math.abs(p.getGold() - 300) < 0.01;
    }

    public Boolean testIron() {
        Player p = new Player(123, null);
        p.addIron(321);
        return Math.abs(p.getIron() - 421) < 0.01;
    }

    public Boolean testGetNearestShipOne() {
        Player p = new Player(1, null);
        return p.getNearestShip(-0.8, -0.8).getMaster().equals(p);
    }

    public Boolean testGetNearestShipTwo() {
        Player p = new Player(1, null);
        return Math.abs(p.getNearestShip(-0.8, -0.8).getX() + 0.8) < 0.01;
    }

    public Boolean testAddShip() {
        Player p = new Player(56, null);
        SpaceShip s = new SpaceShip(0, 0, p, 123, null);
        p.addShip(s);
        return p.getShips().contains(s);
    }

    public Boolean testRemoveShip() {
        Player p = new Player(56, null);
        SpaceShip s = new SpaceShip(0, 0, p, 123, null);
        p.addShip(s);
        p.removeShip(s);
        return !p.getShips().contains(s);
    }


}
