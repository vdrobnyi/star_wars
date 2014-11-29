package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import logic.Player;
import logic.SpaceShip;
import logic.Universe;

public class UniverseTest {

    @Test
    public void testGetNearest() {
        Universe u = new Universe();
        assertTrue(u.getNearestPlanet(0.05, -0.05).equals(u.getPlanets().get(0)));
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
        SpaceShip s = u.getPlayers().get(0).getShips().get(0);
        s.move(0, 0);
        for (int i = 0; i < 2000; i++) {
            u.tick();
        }
        assertTrue(Math.abs(s.getX()) < 0.02);
    }
    @Test
    public void testPlanet() {
        Universe u = new Universe();
        u.getPlayers().get(0).getShips().get(0).move(0, 0);
        for (int i = 0; i < 1000; i++) {
            u.tick();
        }
        assertTrue(u.getPlanet(0, 0).getMaster().equals(u.getPlayers().get(0)));
    }
    @Test
    public void testAttack() {
        Universe u = new Universe();
        Player p1 = u.getPlayers().get(0);
        Player p2 = u.getPlayers().get(0);
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
