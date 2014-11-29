package tests;

import logic.Planet;
import logic.Player;
import logic.SpaceShip;

import static org.junit.Assert.*;

import org.junit.Test;
public class PlanetTest {
    /*
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
    }*/

    @Test
    public void testId() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        assertTrue(p.getId() == 100);
    }

    @Test
    public void testX() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        assertTrue(Math.abs(p.getX() - 0.1) < 0.01);
    }

    @Test
    public void testY() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        assertTrue(Math.abs(p.getY() - 0.2) < 0.01);
    }
    @Test
    public void testLand() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        SpaceShip s = new SpaceShip(0.1, 0.2, null, 1, null);
        p.landPlanet(s);
        assertTrue(p.contains(s));
    }
    @Test
    public void testLeave() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        SpaceShip s = new SpaceShip(0.1, 0.2, null, 1, null);
        p.landPlanet(s);
        p.leavePlanet(s);
        assertTrue(!p.contains(s));
    }
    @Test
    public void testMaster() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        Player pl = new Player(123, null);
        p.changeMaster(pl);
        assertTrue(p.getMaster().equals(pl));
    }
    @Test
    public void testBuildGold() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        Player pl = new Player(123, null);
        p.changeMaster(pl);
        p.buildGoldFactory();
        assertTrue(p.getGold() > 0);
    }
    @Test
    public void testBuildIron() {
        Planet p = new Planet(0.1, 0.2, 100, 1);
        Player pl = new Player(123, null);
        p.changeMaster(pl);
        p.buildIronFactory();
        assertTrue(p.getIron() > 0);
    }
}
