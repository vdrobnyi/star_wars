package logic;

import events.Event;
import events.EventListenerInterface;
import graphics.ViewFrame;

import java.util.*;

public class Universe {
    private int tickNumber;
    private List<Player> players;
    private List<Bullet> bullets;
    private List<Planet> planets;
    private boolean isEnd = false;
    private Random rand = new Random();
    private Player winner = null;

    private List<EventListenerInterface> listeners = new ArrayList<EventListenerInterface>();

    public Universe() {
        tickNumber = 0;
        planets = new ArrayList<Planet>();//new Planet[3];
        bullets = new LinkedList<Bullet>();
        players = new ArrayList<Player>();//new Player[2];

        /*
        planets.add(new Planet(0, 0, IdGenerator.getNewId(), 1));
        planets.add(new Planet(0.325, 0.745, IdGenerator.getNewId(), 2));
        planets.add(new Planet(-0.225, 0.025, IdGenerator.getNewId(), 3));

        players.add(new Player(1, this));
        players.add(new Player(2, this));


        players.get(0).addShip(new SpaceShip(-0.8, -0.8, players.get(0), IdGenerator.getNewId(), this));
        players.get(0).addShip(new SpaceShip(0.8, -0.8, players.get(0), IdGenerator.getNewId(), this));
        players.get(1).addShip(new SpaceShip(0.5, 0.8, players.get(1), IdGenerator.getNewId(), this));
        players.get(1).addShip(new SpaceShip(0.0, 0.8, players.get(1), IdGenerator.getNewId(), this));
        players.get(1).addShip(new SpaceShip(0.5, 0.8, players.get(1), IdGenerator.getNewId(), this));
        players.get(1).addShip(new SpaceShip(0.0, 0.8, players.get(1), IdGenerator.getNewId(), this));
        players.get(1).addShip(new SpaceShip(0.5, 0.8, players.get(1), IdGenerator.getNewId(), this));
        players.get(1).addShip(new SpaceShip(0.0, 0.8, players.get(1), IdGenerator.getNewId(), this));
        */
    }


    public Universe(boolean a) {
        tickNumber = 0;
        planets = new ArrayList<Planet>();//new Planet[3];
        bullets = new LinkedList<Bullet>();
        players = new ArrayList<Player>();//new Player[2];


        planets.add(new Planet(this, 0, 0, IdGenerator.getNewId(), 1));
        planets.add(new Planet(this, 0.325, 0.745, IdGenerator.getNewId(), 2));
        planets.add(new Planet(this, -0.225, 0.025, IdGenerator.getNewId(), 3));

        planets.add(new Planet(this, -0.325, -0.745, IdGenerator.getNewId(), 2));
        planets.add(new Planet(this, 0.325, -0.125, IdGenerator.getNewId(), 3));

        planets.add(new Planet(this, 0.325, 0.745, IdGenerator.getNewId(), 2));
        planets.add(new Planet(this, -0.225, 0.025, IdGenerator.getNewId(), 3));

        planets.add(new Planet(this, -0.7, 0.9, IdGenerator.getNewId(), 2));
        planets.add(new Planet(this, 0.8, 0.8, IdGenerator.getNewId(), 3));


        players.add(new Player(1, this));
        players.add(new Player(2, this));


        players.get(0).addShip(new SpaceShip(-0.8, -0.8, players.get(0), IdGenerator.getNewId(), this));
        players.get(0).addShip(new SpaceShip(-0.76, -0.83, players.get(0), IdGenerator.getNewId(), this));
        players.get(1).addShip(new SpaceShip(-0.73+0.5, -0.78+0.5, players.get(1), IdGenerator.getNewId(), this));
        players.get(1).addShip(new SpaceShip(-0.82+0.5, -0.82+0.5, players.get(1), IdGenerator.getNewId(), this));
    }

    public void addListener(EventListenerInterface l) {
        listeners.add(l);
    }

    public synchronized void eventCapture(Event event) {
        int ship_id;
        int owner_id;
        int planet_id;
        Double x;
        Double y;
        Player p, p2;
        //EL: плохо в модели знать детали про View
        switch (event.getType()) {
            case MESSAGE:
                String out = "";
                for (Map.Entry<String, String> e : event.getProps().entrySet()) {
                    out += e.getKey() + ":" + e.getValue() + "  ";
                }
                System.out.println(out);
                break;
            case LIST:
                break;
            case INFO:
                break;
            case JOIN_GAME:
                break;
            case END_GAME:
                break;
            case START_GAME:
                break;
            case SHIP_MOVE:
                ship_id = event.getInteger("ship_id");//Integer.valueOf(event.getProperty("ship_id"));
                //EL: event.getDouble("x")
                x = event.getDouble("x");//Double.valueOf(event.getProperty("x"));
                y = event.getDouble("y");//Double.valueOf(event.getProperty("y"));
                SpaceShip sp = getShipById(ship_id);
                sp.move(x, y, false);
                break;
            case SHIP_CREATE:
                owner_id = event.getInteger("owner_id");//Integer.valueOf(event.getProperty("owner_id"));
                ship_id = event.getInteger("ship_id");//Integer.valueOf(event.getProperty("ship_id"));
                x = event.getDouble("x");//Double.valueOf(event.getProperty("x"));
                y = event.getDouble("y");//Double.valueOf(event.getProperty("y"));
                p = getPlayerById(owner_id);
                SpaceShip s = new SpaceShip(x, y, p, ship_id, this);
                p.shipCreated(s);
                break;
            case PLANET_CAPTURE:
                planet_id = event.getInteger("planet_id");//Integer.valueOf(event.getProperty("planet_id"));
                owner_id = event.getInteger("new_owner_id");//Integer.valueOf(event.getProperty("new_owner_id"));
                Planet pl = getPlanetById(planet_id);
                p = getPlayerById(owner_id);
                pl.changeMaster(p);
                break;
            case ATTACK:
                int attacker_id = event.getInteger("attacker_id");//Integer.valueOf(event.getProperty("attacker_id"));
                int attacked_id = event.getInteger("attacked_id");//Integer.valueOf(event.getProperty("attacked_id"));
                double damage = event.getDouble("damage");//Double.valueOf(event.getProperty("damage"));
                SpaceShip s1 = getShipById(attacker_id);
                SpaceShip s2 = getShipById(attacked_id);
                attack(s1, s2, damage);
                break;
            case SHIP_REMOVE:
                ship_id = event.getInteger("ship_id");//Integer.valueOf(event.getProperty("ship_id"));
                SpaceShip ship = getShipById(ship_id);
                ship.kill();
                break;
            case GOLD_BUILD:
                planet_id = event.getInteger("planet_id");//Integer.valueOf(event.getProperty("planet_id"));
                pl = getPlanetById(planet_id);
                pl.buildGoldFactory(false);
                break;
            case IRON_BUILD:
                planet_id = event.getInteger("planet_id");//Integer.valueOf(event.getProperty("planet_id"));
                pl = getPlanetById(planet_id);
                pl.buildIronFactory(false);
                break;
            case ANGAR_BUILD:
                planet_id = event.getInteger("planet_id");//Integer.valueOf(event.getProperty("planet_id"));
                pl = getPlanetById(planet_id);
                pl.buildAngar(false);
                break;
        }
        notify(event);
    }



    public void notify(Event event) {
        for (EventListenerInterface l : listeners) {
            if (l.equals(event.getFrom())) continue;
            l.eventCapture(event);
        }
    }

    public Player getPlayerById(int id) {
        for (Player p : getPlayers()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Planet getPlanetById(int id) {
        for (Planet p : getPlanets()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public SpaceShip getShipById(int id) {
        for (Player p : getPlayers()) {
            for (SpaceShip s : p.getShips()) {
                if (s.getId() == id) {
                    return s;
                }
            }
        }
        return null;
    }

    public void addPlanet(Planet p) {
        planets.add(p);
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public Universe(Universe u) {
        planets = new ArrayList<Planet>();//new Planet[u.getPlanets().length];
        bullets = new LinkedList<Bullet>();
        players = new ArrayList<Player>();//new Player[u.getPlayers().length];
        int i = 0;
        for (Player p: u.getPlayers()) {
            players.add(new Player(1, this, p));
        }
        i = 0;
        for (Planet p: u.getPlanets()) {
            planets.add(new Planet(this, p.getX(), p.getY(), 1, p.getRadius()));
            int j = 0;
            while (j < u.planets.size() && !u.planets.get(j).equals(p.getMaster())) j++;
            if (j < u.planets.size()) {
                planets.get(i - 1).changeMaster(players.get(j));
            }
        }
    }

    public void end() {
        isEnd = true;
        for (Player p: players) {
            if (p.getShips().size() > 0) {
                winner = p;
                return;
            }
        }
        for (Planet p: planets) {
            if (p.getMaster() != null) {
                winner = p.getMaster();
                return;
            }
        }
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isEnd() {
        Set<Player> set = new HashSet<Player>();
        for (Player p: players) {
            if (p.getShips().size() > 0) {
                set.add(p);
            }
        }
        for (Planet p: planets) {
            if (p.getMaster() != null) {
                set.add(p.getMaster());
            }
        }
        return set.size() <= 1;
    }

    public boolean isEndGame() {
        return isEnd;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void attack(SpaceShip attacker, SpaceShip target) {
        double damage = Properties.properties.SHIP_DAMAGE * (rand.nextInt(Properties.properties.ATTACK_RANGE));
        notify(Event.getAttackEvent(attacker, target, damage));
        target.attacked(damage);
        makeBullet(attacker, target);
    }

    private void attack(SpaceShip attacker, SpaceShip target, double damage) {
        target.attacked(damage);
        makeBullet(attacker, target);
    }

    public int getTickNumber() {
        return tickNumber;
    }

    public Planet getNearestPlanet(double x, double y) {
        double dist = Properties.properties.UNIVERSE_MAX_DIST;
        Planet res = null;
        for (Planet s: planets) {
            double d = Universe.distance(x, y, s.getX(), s.getY());
            if (d < dist) {
                dist = d;
                res = s;
            }
        }
        return res;
    }

    public UniverseObject getNearestObject(double x, double y) {
        UniverseObject res = getNearestPlanet(x, y);
        double dist = Universe.distance(x, y, res.getX(), res.getY());
        for (Player p: players) {
            for (SpaceShip s: p.getShips()) {
                double d = Universe.distance(x, y, s.getX(), s.getY());
                if (d < dist || Math.abs(d - dist) < 0.02) {
                    dist = d;
                    res = s;
                }
            }
        }
        return res;
    }

    public UniverseObject getObject(double x, double y) {
        UniverseObject res = getNearestObject(x, y);
        if (res != null && Universe.distance(x, y, res.getX(), res.getY()) < (Properties.properties.SHIP_RADIOUS * res.getRadius())) {
            return res;
        }
        return null;
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
        if (res != null && Universe.distance(x, y, res.getX(), res.getY()) < (Properties.properties.SHIP_RADIOUS * res.getRadius())) {
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
                p.makeShips();
            }
        }
        for (Player p : players) {
            for (int i = 0; i < p.getShips().size(); i++) {
                p.getShips().get(i).tick(tickNumber);
            }
        }
        tickBullets();
        if (tickNumber % 50 == 0) {
            if (isEnd()) {
                end();
            }
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
