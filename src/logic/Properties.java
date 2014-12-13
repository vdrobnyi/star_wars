package logic;

public class Properties {
    public static final Properties properties = new Properties();

    final public double SHIP_RADIOUS = 0.04;
    final public double SHIP_DAMAGE = 1;
    final public double GOLD_PER_SEC = 3 / 50.0;
    final public double IRON_PER_SEC = 3 / 50.0;
    final public double GOLD_PER_BUIDING = 50;
    final public double GOLD_PER_ANGAR = 100;
    final public double IRON_PER_ANGAR = 100;
    final public double GOLD_PER_SHIP = 10;
    final public double IRON_PER_SHIP = 10;
    final public double START_GOLD = 100;
    final public double START_IRON = 100;
    final public double SHIP_LIVE = 10;
    final public double SHIP_FUEL = 15;
    final public double SHIP_FUEL_SPEED = 1 / 500.0;
    final public double SHIP_SPEED = 0.1 / 50.0;
    final public double SHIP_ATTACK_DELAY = 50;
    final public double TICKS_PER_SEC = 50;
    final public double BULLET_LIVE_TIME = 20;
    final public int PANEL_SIZE_X = 800;
    final public int PANEL_SIZE_Y = 600;
    final public int BAR_SIZE_X = 500;
    final public int BAR_SIZE_Y = 100;
    final public int UNIVERSE_SIZE_X = 1000;
    final public int UNIVERSE_SIZE_Y = 1000;
    final public int MOVE_SCREEN_AREA = 50;
    final public double UNIVERSE_MAX_DIST = 10;
    final public double SHIP_ATTACK_RANGE = 0.1;
    final public int ATTACK_RANGE = 6;
    final public int BUIDING_DELAY = 100;
    final public String images = "images/load.txt";

    private Properties() {

    }
}
