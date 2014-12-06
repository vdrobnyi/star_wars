package tests;

import ai.RandomAI;
import ai.SingletonAI;
import logic.Bullet;
import logic.Player;

import static org.junit.Assert.*;

import logic.Universe;
import org.junit.Test;


public class AITest {

    @Test
    public void test() {
        assertTrue("as".equals("as"));
    }

    @Test
    public void hasFinish() {
        Universe u = new Universe(true);
        SingletonAI ai = new SingletonAI(u, u.getPlayers().get(0));
        for (int i = 0; i < 10000000; i++) {
            u.tick();
            ai.action();
            if (u.isEndGame())
                return;
        }
        assertTrue(false);
    }

    @Test
    public void singletonWon() {
        Universe u = new Universe(true);
        SingletonAI ai = new SingletonAI(u, u.getPlayers().get(0));
        RandomAI rai = new RandomAI(u, u.getPlayers().get(1));
        for (int i = 0; i < 10000000; i++) {
            u.tick();
            ai.action();
            rai.action();
            if (u.isEndGame())){
                assertTrue(u.getWinner().equals(u.getPlayers().get(0));
                return;
            }
        }
    }

    @Test
    public void teamWon() {
        Universe u = new Universe(true);
        TeamAI ai = new TeamAI(u, u.getPlayers().get(0));
        RandomAI rai = new RandomAI(u, u.getPlayers().get(1));
        for (int i = 0; i < 10000000; i++) {
            u.tick();
            ai.action();
            rai.action();
            if (u.isEndGame())){
                assertTrue(u.getWinner().equals(u.getPlayers().get(0));
                return;
            }
        }
    }

}