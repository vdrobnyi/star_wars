package tests;

import static org.junit.Assert.*;

import logic.Planet;
import org.junit.Test;

import logic.Player;
import logic.SpaceShip;
import logic.Universe;

public class UniverseTest {

    @Test
    public void testGetNearest() {
        Universe u = new Universe();
        Planet p = new Planet(u, 0.05, -0.05, 1, 1);
        u.addPlanet(p);
        assertTrue(u.getNearestPlanet(0.05, -0.05).equals(p));
    }
    @Test
    public void testBullets() {
        Universe u = new Universe();
        SpaceShip s1 = new SpaceShip(0, 0, null, 1, u);
        SpaceShip s2 = new SpaceShip(0.05, 0.05, null, 11, u);
        u.makeBullet(s1, s2);
        assertTrue(u.getBullets().size() > 0);
    }
    @Test
    public void testMoveShip() {
        Universe u = new Universe();
        Player p = new Player(1, u);
        SpaceShip s = new SpaceShip(0.8, 0.8, p, 1, u);
        u.addPlayer(p);
        p.addShip(s);
        s.move(0, 0);
        for (int i = 0; i < 2000; i++) {
            u.tick();
        }
        assertTrue(Math.abs(s.getX()) < 0.02);
    }
    @Test
    public void testPlanet() {
        Universe u = new Universe();
        Player p = new Player(1, u);
        SpaceShip s = new SpaceShip(0.8, 0.8, p, 1, u);
        u.addPlayer(p);
        p.addShip(s);
        s.move(0, 0);
        Planet pl = new Planet(u, 0, 0, 1, 1);
        u.addPlanet(pl);
        for (int i = 0; i < 1000; i++) {
            u.tick();
        }
        assertTrue(u.getPlanet(0, 0).getMaster().equals(p));
    }
    @Test
    public void testAttack() {
        Universe u = new Universe();
        Player p1 = new Player(1, u);
        Player p2 = new Player(2, u);
        u.addPlayer(p1);
        u.addPlayer(p2);
        for (int i = 0; i < 10; i++) {
            p1.addShip(new SpaceShip(0, 0, p1, i + 100, u));
        }
        SpaceShip s = new SpaceShip(0, 0, p2, 324, u);
        p2.addShip(s);
        for (int i = 0; i < 1000; i++) {
            u.tick();
        }
        assertTrue(!s.isAlive());
    }
}
