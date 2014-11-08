public class SpaceShip extends UniverseObject {

    enum State {
        MOVE,
        STAY
    };
    private Player master;
    private double targetX, targetY;
    private double x, y;
    boolean isAlive = true;
    private int id;
    private double speed = Properties.properties.SHIP_SPEED;
    private double fuel = Properties.properties.SHIP_FUEL;
    private double fuelSpeed = Properties.properties.SHIP_FUEL_SPEED;
    private State state;
    private int lastAttack = 0;

    private double live = Properties.properties.SHIP_LIVE;

    public SpaceShip(double x, double y, Player master, int i) {
        this.x = x;
        this.y = y;
        this.master = master;
        state = State.STAY;
        id = i;
    }

    public Player getMaster() {
        return master;
    }

    public void kill() {
        isAlive = false;
        for (Planet p:ViewFrame.universe.getPlanets()) {
            p.leavePlanet(this);
        }
        getMaster().removeShip(this);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void move(double x, double y) {
        state = State.MOVE;
        targetX = x;
        targetY = y;
    }

    public void stop() {
        state = State.STAY;
    }

    public void tick(int tickNumber) {
        boolean attack = tickNumber < lastAttack + Properties.properties.SHIP_ATTACK_DELAY;

        for (int i = 0; i < ViewFrame.universe.getPlayers().length; i++) {
            Player p = ViewFrame.universe.getPlayers()[i];
            if (!p.equals(getMaster())) {
                for (int j = 0; j < p.getShips().size(); j++) {
                    SpaceShip s = p.getShips().get(j);
                    if (s.isAlive() && isAlive() && dist(s) < 0.1 && !attack) {
                        ViewFrame.universe.attack(this, s);
                        attack = true;
                        lastAttack = tickNumber;
                    }
                }
            }
        }

        if (state == State.MOVE && fuel > 0 && !attack) {
            if (Math.sqrt(Math.pow(this.x - targetX, 2) + Math.pow(this.y - targetY, 2)) > 0.02) {
                this.x += (targetX - this.x) / (Math.sqrt(Math.pow(this.x - targetX, 2) + Math.pow(this.y - targetY, 2))) * speed;
                this.y += (targetY - this.y) / (Math.sqrt(Math.pow(this.x - targetX, 2) + Math.pow(this.y - targetY, 2))) * speed;
                fuel -= fuelSpeed;
                if (this.x > 1) this.x = 1;
                if (this.x < -1) this.x = -1;
                if (this.y > 1) this.y = 1;
                if (this.y < -1) this.y = -1;
                if (Math.sqrt(Math.pow(this.x - targetX, 2) + Math.pow(this.y - targetY, 2)) < 0.01) {
                    this.x = targetX;
                    this.y = targetY;
                }
            } else {
                stop();
            }
        }

        for (Planet p: ViewFrame.universe.getPlanets()) {

            if (p.dist(this) < Properties.properties.SHIP_RADIOUS * p.getRadius()) {
                p.landPlanet(this);
            } else {
                p.leavePlanet(this);
            }
            if (p.contains(this)) {
                fuel = Properties.properties.SHIP_FUEL;
            }
        }
    }

    public void attacked(double damage) {
        live -= damage;
        if (live <= 0)
            kill();
    }

    public double dist(SpaceShip p) {
        return Math.sqrt(Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2));
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getId() {
        return id;
    }
}
