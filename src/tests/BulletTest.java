package tests;


import logic.Bullet;
import logic.Player;


public class BulletTest {

    public Boolean test() {
        Boolean res = true;
        System.out.println("-- Bullet test --");
        Player p = new Player(123, null);
        Bullet b = new Bullet(0, 0.5, 1, 0.3, 100, p);
        res &= b.getX1() == 0.0;
        res &= b.getY1() == 0.5;
        res &= b.getX2() == 1.0;
        res &= b.getY2() == 0.3;
        res &= b.getPlayer().equals(p);
        res &= b.getStartTick() == 100;
        if (res) { System.out.println("Test passed!"); }
        else { System.out.println("Test failed!"); }
        System.out.println("-- End bullet test --");
        return res;
    }
}
