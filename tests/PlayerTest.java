package tests;


import static org.junit.Assert.*;

import org.junit.Test;
import logic.Player;
import logic.SpaceShip;
import logic.Universe;

public class PlayerTest {
    @Test
    public void testGold() {
        Player p = new Player(123, null);
        p.addGold(200);
        assertTrue(Math.abs(p.getGold() - 300) < 0.01);
    }
    @Test
    public void testIron() {
        Player p = new Player(123, null);
        p.addIron(321);
        assertTrue(Math.abs(p.getIron() - 421) < 0.01);
    }
    @Test
    public void testGetNearestShipOne() {
        Universe u = new Universe();
        Player p = new Player(1, u);
        u.addPlayer(p);
        p.addShip(new SpaceShip(-0.8, -0.8, p, 1, u));
        assertTrue(p.getNearestShip(-0.8, -0.8).getMaster().equals(p));
    }
    @Test
    public void testGetNearestShipTwo() {
        Universe u = new Universe();
        Player p = new Player(1, u);
        u.addPlayer(p);
        p.addShip(new SpaceShip(-0.8, -0.8, p, 1, u));
        assertTrue(Math.abs(p.getNearestShip(-0.8, -0.8).getX() + 0.8) < 0.01);
    }
    @Test
    public void testAddShip() {
        Player p = new Player(56, null);
        SpaceShip s = new SpaceShip(0, 0, p, 123, null);
        p.addShip(s);
        assertTrue(p.getShips().contains(s));
    }
    @Test
    public void testRemoveShip() {
        Player p = new Player(56, null);
        SpaceShip s = new SpaceShip(0, 0, p, 123, null);
        p.addShip(s);
        p.removeShip(s);
        assertTrue(!p.getShips().contains(s));
    }


}
