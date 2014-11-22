package logic;

import java.util.ArrayList;
import java.util.Iterator;

public class Planet extends UniverseObject {

    private Player master;
    private final double radius;
    private final int id;
    private final double y;
    private final double x;
    private final int orbit_size;
    private ArrayList<SpaceShip> orbit = new ArrayList<SpaceShip>();
    private boolean has_angar = false;
    private double gold_per_sec = 0;
    private double iron_per_sec = 0;

    public Player getMaster() {
        return master;
    }

    public void leavePlanet(SpaceShip s) {
        if (orbit.contains(s))
            orbit.remove(s);
    }

    public boolean landPlanet(SpaceShip s) {
        if (s.isAlive() && (s.getMaster() == getMaster() || isEmpty() || orbit.size() == 0)
                && dist(s) < Properties.properties.SHIP_RADIOUS * radius && orbit.size() < orbit_size) {
            if (!contains(s))
                orbit.add(s);
            changeMaster(s.getMaster());
            return true;
        }
        return false;
    }

    public boolean contains(SpaceShip s) {
        if (orbit.contains(s)) {
            return true;
        }
        return false;
    }

    public double dist(SpaceShip p) {
        return Math.sqrt(Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2));
    }

    public void changeMaster(Player new_master) {
        master = new_master;
    }

    public double getX() {
        return this.x;
    }

    public double getRadius() {
        return radius;
    }

    public double getY() {
        return this.y;
    }

    public Planet(double x, double y, int i, double rad) {
        this.x = x;
        this.y = y;
        master = null;
        id = i;
        radius = rad;
        orbit_size = (int)radius * 1;
    }

    public int getId() {
        return id;
    }

    public Planet(double x, double y, Player p, int id, double rad) {
        this.x = x;
        this.y = y;
        master = p;
        this.id = id;
        radius = rad;
        orbit_size = (int)radius * 1;
    }


    public void buildGoldFactory() {
        if (gold_per_sec == 0 && master != null && master.addGold(-Properties.properties.GOLD_PER_BUIDING)) {
            gold_per_sec = Properties.properties.GOLD_PER_SEC;
        }
    }

    public void buildIronFactory() {
        if (iron_per_sec == 0 && master != null && master.addGold(-Properties.properties.GOLD_PER_BUIDING)) {
            iron_per_sec = Properties.properties.IRON_PER_SEC;
        }
    }

    public void buildAngar() {
        if (has_angar == false && master != null && master.addGold(-Properties.properties.GOLD_PER_ANGAR)
                && master.addIron(-Properties.properties.IRON_PER_ANGAR)) {
            has_angar = true;
        }
    }

    public void makeShip() {
        if (has_angar && master != null && master.addGold(-Properties.properties.GOLD_PER_SHIP)
                && master.addIron(-Properties.properties.IRON_PER_SHIP)) {
            getMaster().addShip(new SpaceShip(getX(), getY(), getMaster(), IdGenerator.getNewId(), getMaster().getUniverse()));
        }
    }

    public double getGold() {
        return gold_per_sec * radius;
    }

    public double getIron() {
        return iron_per_sec * radius;
    }

    public boolean isEmpty() {
        if (master == null)
            return true;
        ArrayList ships = master.getShips();
        Iterator<SpaceShip> iter = ships.iterator();
        while (iter.hasNext()) {
            SpaceShip s = iter.next();
            if (Math.sqrt(Math.pow(this.x - s.getX(), 2) + Math.pow(this.y - s.getY(), 2)) < 0.05) {
                return false;
            }
        }
        return true;
    }
}
