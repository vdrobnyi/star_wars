package logic;

import java.util.*;

public class Universe {
    private int tickNumber;
    private Player[] players;
    private List<Bullet> bullets;
    private Planet[] planets;
    private boolean isEnd = false;
    private Random rand = new Random();

    public Universe() {
        tickNumber = 0;
        planets = new Planet[3];
        bullets = new LinkedList<Bullet>();
        planets[0] = new Planet(0, 0, IdGenerator.getNewId(), 1);
        planets[1] = new Planet(0.325, 0.745, IdGenerator.getNewId(), 2);
        planets[2] = new Planet(-0.225, 0.025, IdGenerator.getNewId(), 3);

        players = new Player[2];
        players[0] = new Player(1, this);
        players[1] = new Player(2, this);
    }

    public Universe(Universe u) {
        planets = new Planet[u.getPlanets().length];
        bullets = new LinkedList<Bullet>();
        players = new Player[u.getPlayers().length];
        int i = 0;
        for (Player p: u.getPlayers()) {
            players[i++] = new Player(1, this, p);
        }
        i = 0;
        for (Planet p: u.getPlanets()) {
            planets[i++] = new Planet(p.getX(), p.getY(), 1, p.getRadius());
            int j = 0;
            while (j < u.planets.length && !u.planets[j].equals(p.getMaster())) j++;
            if (j < u.planets.length) {
                planets[i - 1].changeMaster(players[j]);
            }
        }
    }

    public void end() {
        isEnd = true;
    }

    public boolean isEndGame() {
        return isEnd;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void attack(SpaceShip attacker, SpaceShip target) {
        target.attacked(Properties.properties.SHIP_DAMAGE * (rand.nextInt(6)));
        makeBullet(attacker, target);
    }

    public int getTickNumber() {
        return tickNumber;
    }

    public Planet getNearestPlanet(double x, double y) {
        double dist = 10;
        Planet res = null;
        for (Planet s: planets) {
            double d = Universe.distanse(x, y, s.getX(), s.getY());
            if (d < dist) {
                dist = d;
                res = s;
            }
        }
        return res;
    }
    
    public void makeBullet(SpaceShip attacker, SpaceShip target) {
        bullets.add(new Bullet(attacker.getX(), attacker.getY(),
                target.getX() + (rand.nextDouble() / 100), target.getY() + (rand.nextDouble() / 100),
                getTickNumber(), attacker.getMaster()));
    }

    private void tickBullets() {
        for (Bullet b: new ArrayList<Bullet>(bullets)) {
            if (tickNumber - b.getStartTick() > Properties.properties.BULLET_LIVE_TIME)
                bullets.remove(b);
        }
    }

    public Planet getPlanet(double x, double y) {
        Planet res = getNearestPlanet(x, y);
        if (res != null && Universe.distanse(x, y, res.getX(), res.getY()) < (Properties.properties.SHIP_RADIOUS * res.getRadius())) {
            return res;
        }
        return null;
    }

    public void tick() {
        tickNumber++;
        for (Planet p: planets) {
            if (p.getMaster() != null) {
                p.getMaster().addGold(p.getGold());
                p.getMaster().addIron(p.getIron());
            }
        }
        for (Player p : players) {
            for (int i = 0; i < p.getShips().size(); i++) {
                p.getShips().get(i).tick(tickNumber);
            }
        }
        tickBullets();
    }
    /*
    public Universe getCopy() {
        Universe u = new Universe();
        for (int i = 0; i < players.length; i++) {
            for (SpaceShip s: u.players[i].getShips()) {
                s.kill();
            }
            for (SpaceShip s: players[i].getShips()) {
                SpaceShip ns = new SpaceShip(s.getX(), s.getY(), u.players[i], 1, u);
                u.players[i].addShip(ns);
            }
        }
        for (int i = 0; i < )
    }*/

    public List<Bullet> getBullets() {
        return bullets;
    }

    public Planet[] getPlanets() {
        return planets;
    }

    public static double distanse(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
