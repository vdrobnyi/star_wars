package net;

import javafx.util.Pair;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

public class Game {
    public List<Pair<InetAddress, Integer>> players = new LinkedList<>();
    public Boolean isStarted = false;

    public Integer getNumberOfPlayers() {
        return players.size();
    }

    public void addPlayer(Pair<InetAddress, Integer> player) {
        players.add(player);
    }

    public boolean contains(Pair<InetAddress, Integer> player) {
        for (Pair<InetAddress, Integer> p : players ) {
            if (p.equals(player))
                return true;
        }
        return false;
    }
}
