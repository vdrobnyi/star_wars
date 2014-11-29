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
    private int tickNumber;
    private ViewPanel panel;
    private BarPanel bar;
    public int startX;
    public int startY;
    public AIInterface ai;

    public ViewFrame(Universe u) {
        currentPlayerNum = 0;
        currentShipNum = 0;
        tickNumber = 0;
        universe = u;
        startX = 0;
        startY = 0;
        ai = new SingletonAI(universe, u.getPlayers().get(0));
        panel = new ViewPanel(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bar = new BarPanel(this);
        bar.enableButtons(false);
        panel.setPreferredSize(new Dimension(Properties.properties.PANEL_SIZE_X + 1, Properties.properties.PANEL_SIZE_Y + 1));
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
