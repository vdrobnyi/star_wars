import tests.*;

public class Tester {
    public static void main(String[] argv) {
        int passed = 0;
        BulletTest bt = new BulletTest();
        PlanetTest pt = new PlanetTest();
        PlayerTest plt = new PlayerTest();
        SpaceShipTest st = new SpaceShipTest();
        UniverseTest ut = new UniverseTest();

        if (bt.test()) passed++;
        if (pt.test()) passed++;
        if (plt.test()) passed++;
        if (st.test()) passed++;
        if (ut.test()) passed++;

        System.out.println("TOTAL: " + passed + " - passed, " + (5 - passed) + " - failed   ");

    }
}
