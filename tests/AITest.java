package tests;

import ai.RandomAI;
import ai.SingletonAI;
import ai.TeamAI;

import logic.Bullet;
import logic.Player;

import static org.junit.Assert.*;

import logic.Universe;
import org.junit.Test;
import logic.UniverseLoader;


public class AITest {

    @Test
    public void hasFinish() {
        UniverseLoader ul = new UniverseLoader();
        Universe u = ul.loadUniverse("out.txt");
        SingletonAI ai = new SingletonAI(u, u.getPlayers().get(0));
        for (int i = 0; i < 10000000 || u.isEndGame(); i++) {
            u.tick();
            ai.action();
            if (u.isEndGame())
                return;
        }
        assertTrue(false);
    }

    @Test
    public void singletonWon() {
        UniverseLoader ul = new UniverseLoader();
        Universe u = ul.loadUniverse("out.txt");
        SingletonAI ai = new SingletonAI(u, u.getPlayers().get(0));
        RandomAI rai = new RandomAI(u, u.getPlayers().get(1));
        for (int i = 0; i < 10000000 || u.isEndGame(); i++) {
            u.tick();
            ai.action();
            rai.action();
            if (u.isEndGame()) {
                assertTrue(u.getWinner().equals(u.getPlayers().get(0)));
                return;
            }
        }
    }

    @Test
    public void teamWon() {
        UniverseLoader ul = new UniverseLoader();
        Universe u = ul.loadUniverse("out.txt");
        TeamAI ai = new TeamAI(u, u.getPlayers().get(0));
        RandomAI rai = new RandomAI(u, u.getPlayers().get(1));
        for (int i = 0; i < 10000000 || u.isEndGame(); i++) {
            u.tick();
            ai.action();
            rai.action();
            if (u.isEndGame()){
                assertTrue(u.getWinner().equals(u.getPlayers().get(0)));
                return;
            }
        }
    }

}