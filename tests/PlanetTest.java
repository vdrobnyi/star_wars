package tests;

import logic.Planet;
import logic.Player;
import logic.SpaceShip;

import static org.junit.Assert.*;

import logic.Universe;
import org.junit.Test;
public class PlanetTest {

    @Test
    public void testId() {
        Planet p = new Planet(null, 0.1, 0.2, 100, 1);
        assertTrue(p.getId() == 100);
    }

    @Test
    public void testX() {
        Planet p = new Planet(null, 0.1, 0.2, 100, 1);
        assertTrue(Math.abs(p.getX() - 0.1) < 0.01);
    }

    @Test
    public void testY() {
        Planet p = new Planet(null, 0.1, 0.2, 100, 1);
        assertTrue(Math.abs(p.getY() - 0.2) < 0.01);
    }
    @Test
    public void testLand() {
        Universe u = new Universe();
        Player pl = new Player(1, u);
        u.addPlayer(pl);
        Planet p = new Planet(u, 0.1, 0.2, 100, 1);
        u.addPlanet(p);
        SpaceShip s = new SpaceShip(0.1, 0.2, pl, 1, u);
        pl.addShip(s);
        p.landPlanet(s);
        assertTrue(p.contains(s));
    }
    @Test
    public void testLeave() {
        Universe u = new Universe();
        Player pl = new Player(1, u);
        u.addPlayer(pl);
        Planet p = new Planet(u, 0.1, 0.2, 100, 1);
        u.addPlanet(p);
        SpaceShip s = new SpaceShip(0.1, 0.2, pl, 1, u);
        pl.addShip(s);
        p.landPlanet(s);
        p.leavePlanet(s);
        assertTrue(!p.contains(s));
    }
    @Test
    public void testMaster() {
        Planet p = new Planet(null, 0.1, 0.2, 100, 1);
        Player pl = new Player(123, null);
        p.changeMaster(pl);
        assertTrue(p.getMaster().equals(pl));
    }
    @Test
    public void testBuildGold() {
        Planet p = new Planet(null, 0.1, 0.2, 100, 1);
        Player pl = new Player(123, null);
        p.changeMaster(pl);
        p.buildGoldFactory();
        assertTrue(p.getGold() > 0);
    }
    @Test
    public void testBuildIron() {
        Planet p = new Planet(null, 0.1, 0.2, 100, 1);
        Player pl = new Player(123, null);
        p.changeMaster(pl);
        p.buildIronFactory();
        assertTrue(p.getIron() > 0);
    }
}
