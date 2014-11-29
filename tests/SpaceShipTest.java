package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import logic.Player;
import logic.SpaceShip;

public class SpaceShipTest {
    @Test
    public void testIsAlive() {
        SpaceShip s = new SpaceShip(0, 0, null, 123, null);
        assertTrue(s.isAlive());
    }
    @Test
    public void testMaster() {
        Player p = new Player(1, null);
        SpaceShip s = new SpaceShip(0, 0, p, 123, null);
        assertTrue(s.getMaster().equals(p));
    }
    @Test
    public void testX() {
        SpaceShip s = new SpaceShip(0.3, 0.5, null, 123, null);
        assertTrue(Math.abs(s.getX() - 0.3) < 0.01);
    }
    @Test
    public void testY() {
        SpaceShip s = new SpaceShip(0.3, 0.5, null, 123, null);
        assertTrue(Math.abs(s.getY() - 0.5) < 0.01);
    }
}
