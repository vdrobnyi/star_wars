package graphics;

import ai.AIInterface;
import ai.RandomAI;
import ai.SingletonAI;
import ai.TeamAI;
import logic.*;

import javax.swing.*;
import java.awt.*;


public class ViewFrame extends JFrame {
    public static Universe universe;
    public Player currentPlayer;
    public SpaceShip currentShip;
    public int currentPlayerNum;
    public int currentShipNum;
    public UniverseObject currentObject;
    public int startY;
    public AIInterface ai, rai;
    public int startX;
    public int width;
    public int height;
    private int tickNumber;
    private ViewPanel panel;
    private BarPanel bar;

    public ViewFrame(Universe u) {
        currentPlayerNum = 0;
        currentShipNum = 0;
        tickNumber = 0;
        universe = u;
        startX = 0;
        startY = 0;
        //ai = new SingletonAI(universe, u.getPlayers().get(0));
        //rai = new RandomAI(universe, u.getPlayers().get(1));

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight() - 150;


        System.out.println(width + " " + height);
        panel = new ViewPanel(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bar = new BarPanel(this);
        bar.enableButtons(false);
        panel.setPreferredSize(new Dimension(width, height));
        bar.setPreferredSize(new Dimension(Properties.properties.BAR_SIZE_X + 1, Properties.properties.BAR_SIZE_Y + 1));
        add(panel, BorderLayout.NORTH);
        add(bar, BorderLayout.SOUTH);


        setFocusable(true);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);


        currentPlayer = universe.getPlayers().get(currentPlayerNum);
        currentObject = currentPlayer.getShips().get(currentShipNum);
    }

    public void end() {
        System.out.println("Game end");
        System.exit(0);
    }

    public BarPanel getBarPanel() {
        return bar;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public SpaceShip getCurrentShip() {
        return currentShip;
    }
}
