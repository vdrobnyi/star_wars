
import graphics.ViewFrame;
import logic.Universe;
import logic.UniverseLoader;

public class Main {

    public static void main(String[] args) {
        UniverseLoader ul = new UniverseLoader();
        System.out.println("Start game");
        Universe u = ul.loadUniverse("../out.txt");
        ViewFrame v = new ViewFrame(u);
    }
}
