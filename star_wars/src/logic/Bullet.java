package logic;

public class Bullet {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private int startTick;
    private Player player;

    public Bullet(double x1, double y1, double x2, double y2, int tick, Player p) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        startTick = tick;
        player = p;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public int getStartTick() {
        return startTick;
    }

    public Player getPlayer() {
        return player;
    }

}
