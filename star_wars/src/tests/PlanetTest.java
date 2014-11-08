package tests;

import logic.Planet;
import logic.Player;
import logic.SpaceShip;

public class PlanetTest {
    public Boolean test() {
        int passed = 0, failed;
        System.out.println("-- Planet test --");

        if (testBuildGold()) passed++; else System.out.println("BuildGold failed!");
        if (testBuildIron()) passed++; else System.out.println("BuildIron failed!");
        if (testId()) passed++; else System.out.println("Id failed!");
        if (testLand()) passed++;  else System.out.println("Land failed!");
        if (testLeave()) passed++;  else System.out.println("Leave failed!");
        if (testMaster()) passed++;  else System.out.println("Master failed!");
        if (testX()) passed++; else System.out.println("X failed!");
        if (testY()) passed++; else System.out.println("Y failed!");
        failed = 8 - passed;
        System.out.println(passed + " - passed");
        System.out.println(failed + " - failed");
        System.out.println("-- End planet test --");
        return passed == 8;
    }

    public Boolean testId() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        return p.getId() == 100;
    }

    public Boolean testX() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        return Math.abs(p.getX() - 0.1) < 0.01;
    }

    public Boolean testY() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        return Math.abs(p.getY() - 0.2) < 0.01;
    }

    public Boolean testLand() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        SpaceShip s = new SpaceShip(0.1, 0.2, null, 1, null);
        p.landPlanet(s);
        return p.contains(s);
    }

    public Boolean testLeave() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        SpaceShip s = new SpaceShip(0.1, 0.2, null, 1, null);
        p.landPlanet(s);
        p.leavePlanet(s);
        return !p.contains(s);
    }

    public Boolean testMaster() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        Player pl = new Player(123, null);
        p.changeMaster(pl);
        return p.getMaster().equals(pl);
    }

    public Boolean testBuildGold() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        Player pl = new Player(123, null);
        p.changeMaster(pl);
        p.buildGoldFactory();
        return p.getGold() > 0;
    }

    public Boolean testBuildIron() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        Player pl = new Player(123, null);
        p.changeMaster(pl);
        p.buildIronFactory();
        return p.getIron() > 0;
    }
}
