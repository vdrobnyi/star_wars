import graphics.ViewFrame;
import logic.Universe;

public class Main {

    public static void main(String[] args) {
        System.out.println("Start game");
        Universe u = new Universe();
        ViewFrame v = new ViewFrame(u);
    }
}
