package logic;

import events.Event;

import java.util.ArrayList;
import java.util.Iterator;

public class Planet extends UniverseObject {

    private Player master;
    private Universe universe;
    private final double radius;
    private final int id;
    private final double y;
    private final double x;
    private double targetX;
    private double targetY;
    private final int orbitSize;
    private ArrayList<SpaceShip> orbit = new ArrayList<SpaceShip>();
    private boolean hasAngar = false;
    private double goldPerSec = 0;
    private double ironPerSec = 0;
    private boolean buildShips = false;
    private int lastShip = 0;

    public Planet(Universe u, double x, double y, int i, double rad) {
        universe = u;
        this.x = targetX = x;

        this.y = targetY = y;
        master = null;
        id = i;
        radius = rad;
        orbitSize = (int)radius * 1;
    }

    public Planet(Universe u, double x, double y, Player p, int id, double rad) {
        universe = u;
        this.x = x;
        this.y = y;
        master = p;
        this.id = id;
        radius = rad;
        orbitSize = (int)radius * 1;
    }

    public double getTargetX() {
        return targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public int getId() {
        return id;
    }

    public Player getMaster() {
        return master;
    }

    public void leavePlanet(SpaceShip s) {
        if (orbit.contains(s))
            orbit.remove(s);
    }

    public boolean landPlanet(SpaceShip s) {
        if (s.isAlive() && (s.getMaster() == getMaster() || isEmpty() || orbit.size() == 0)
                && dist(s) < Properties.properties.SHIP_RADIOUS * radius && orbit.size() < orbitSize) {
            if (!contains(s))
                orbit.add(s);
            changeMaster(s.getMaster());
            return true;
        }
        return false;
    }

    public boolean contains(SpaceShip s) {
        return orbit.contains(s);
    }

    public double dist(SpaceShip p) {
        return Math.sqrt(Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2));
    }

    public void changeMaster(Player new_master) {
        if (!new_master.equals(master)) {
            getUniverse().notify(Event.getPlanetCaptureEvent(this, new_master));
        }
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

    public void buildGoldFactory() {
        if (goldPerSec == 0 && master != null && master.addGold(-Properties.properties.GOLD_PER_BUIDING)) {
            goldPerSec = Properties.properties.GOLD_PER_SEC;
            getUniverse().notify(Event.getGoldBuildEvent(this));
        }
    }

    public void buildIronFactory() {
        if (ironPerSec == 0 && master != null && master.addGold(-Properties.properties.GOLD_PER_BUIDING)) {
            ironPerSec = Properties.properties.IRON_PER_SEC;
            getUniverse().notify(Event.getIronBuildEvent(this));
        }
    }

    public void buildAngar() {
        if (hasAngar == false && master != null && master.addGold(-Properties.properties.GOLD_PER_ANGAR)
                && master.addIron(-Properties.properties.IRON_PER_ANGAR)) {
            hasAngar = true;
            getUniverse().notify(Event.getAngarBuildEvent(this));
        }
    }

    public boolean makeShip() {
        if (hasAngar && master != null && master.addGold(-Properties.properties.GOLD_PER_SHIP)
                && master.addIron(-Properties.properties.IRON_PER_SHIP)) {
            SpaceShip s = new SpaceShip(getX(), getY(), getMaster(), IdGenerator.getNewId(), getMaster().getUniverse());
            getMaster().addShip(s);

            getUniverse().notify(Event.getShipCreateEvent(s, getX(), getY()));
            s.move(targetX, targetY);
            return true;
        }
        return false;
    }

    public void startBuild(boolean start) {
        buildShips = start;
    }

    public boolean isBuildShips() {
        return buildShips;
    }

    public void setTarget(double x, double y) {
        targetX = x;
        targetY = y;
    }

    public void makeShips() {
        if (buildShips &&
                universe.getTickNumber() - lastShip > Properties.properties.BUIDING_DELAY) {
            lastShip = universe.getTickNumber();
            makeShip();
        }
    }

    public double getGold() {
        return goldPerSec * radius;
    }

    public double getIron() {
        return ironPerSec * radius;
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

    public Universe getUniverse() {
        return universe;
    }
}
