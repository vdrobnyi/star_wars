package logic;

import events.Event;

import java.util.ArrayList;

public class Player {
    private ArrayList<SpaceShip> ships;


    private Universe universe;
    private double gold = Properties.properties.START_GOLD;
    private double iron = Properties.properties.START_IRON;
    private int id;

    public double getGold() {
        return gold;
    }

    public double getIron() {
        return iron;
    }

    public Player(int id, Universe u) {
        ships = new ArrayList<SpaceShip>();
        universe = u;
        this.id = id;
    }

    public Player(int id, Universe u, Player p) {
        ships = new ArrayList<SpaceShip>();
        universe = u;
        this.id = id;
        gold = p.getGold();
        iron = p.getIron();
        for (SpaceShip s: p.getShips()) {
            ships.add(new SpaceShip(s.getX(), s.getY(), this, IdGenerator.getNewId(), u));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player)) {
            return false;
        }
        return id == ((Player) o).getId();
    }

    public Universe getUniverse() {
        return universe;
    }

    public SpaceShip getNearestShip(double x, double y) {
        double dist = Properties.properties.UNIVERSE_MAX_DIST;
        SpaceShip res = null;
        for (SpaceShip s: ships) {
            double d = Universe.distance(x, y, s.getX(), s.getY());
            if (d < dist) {
                dist = d;
                res = s;
            }
        }
        return res;
    }

    public void addShip(SpaceShip s) {
        addShip(s, true);
    }

    public void addShip(SpaceShip s, boolean notify) {
        ships.add(s);
        if (notify)
            getUniverse().notify(Event.getShipCreateEvent(s, s.getX(), s.getY()), null);
    }

    public boolean addGold(double g) {
        if (gold + g >= 0) {
            gold += g;
            return true;
        }
        return false;
    }

    public void removeShip(SpaceShip s) {
        ships.remove(s);
    }

    public boolean addIron(double i) {
        if (iron + i >= 0) {
            iron += i;
            return true;
        }
        return false;
    }

    public ArrayList<SpaceShip> getShips() {
        return ships;
    }

    public int getId() {
        return id;
    }
}
