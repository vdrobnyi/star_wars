package graphics;

import ai.AIInterface;
import events.Event;
import events.EventListenerInterface;
import logic.*;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ViewFrame extends JFrame implements events.EventListenerInterface {
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
    private final JTextArea bar2 = new JTextArea(3, 62);
    private final JScrollPane scroll = new JScrollPane(bar2);
    private ViewFrame frame;
    public ViewFrame(Universe u, Player current) {
        currentPlayerNum = 0;
        currentShipNum = 0;
        tickNumber = 0;
        universe = u;
        startX = 0;
        startY = 0;
        //ai = new SingletonAI(universe, u.getPlayers().get(0));
        //rai = new SingletonAI(universe, u.getPlayers().get(1));

        //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = 500;//gd.getDisplayMode().getWidth();
        height = 500;//gd.getDisplayMode().getHeight() - 150;

        universe.addListener(this);

        System.out.println(width + " " + height);
        panel = new ViewPanel(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bar = new BarPanel(this);
        bar.enableButtons(false);
        //panel.setPreferredSize(new Dimension(width, height - 100));
        //bar.setPreferredSize(new Dimension(width, 100));//(new Dimension(Properties.properties.BAR_SIZE_X + 1, Properties.properties.BAR_SIZE_Y + 1));
        //add(panel, BorderLayout.NORTH);
        //add(bar, BorderLayout.SOUTH);


        bar2.setLineWrap(true);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(new Dimension(width, 100));

        setBounds(0, 0, width, height);
        panel.setBounds(0, 100, width, height - 200);
        bar.setBounds(0, height - 199, width, 100);
        //bar2.setBounds(0, 0, width, 99);
        add(panel);
        add(bar, BorderLayout.SOUTH);
        add(scroll, BorderLayout.NORTH);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //setFocusable(true);
        //setUndecorated(true);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setVisible(true);
        frame = this;
        bar2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                System.out.println(e.getKeyCode() + "b");
                if (e.getKeyCode() == 10) {
                    Event evt = events.Event.fromString(bar2.getText());
                    evt.setFrom(frame);
                    universe.notify(evt);
                    universe.eventCapture(evt);
                }
            }
        });

        currentPlayer = current;//universe.getPlayers().get(currentPlayerNum);
        currentObject = currentPlayer.getShips().get(currentShipNum);
    }

    public void setCurrentPlayer(Player current) {
        currentPlayer = current;
        currentObject = currentPlayer.getShips().get(currentShipNum);
    }

    public void eventCapture(events.Event evt) {
        //System.out.println(evt);
        addText(evt.toString());
        switch (evt.getType()) {
            case MESSAGE:
                if (evt.getProperty("pos") != null) {
                    setCurrentPlayer(universe.getPlayerById(
                            Integer.parseInt(evt.getProperty("pos")) - 1));
                }
                break;
            case START_GAME:
                setVisible(true);
                break;
            case CREATE_GAME:
                setCurrentPlayer(universe.getPlayerById(0));
                break;
            case END_GAME:
                end();
                break;
        }
    }

    public void end() {
        Event event = new Event(Event.GameEventType.END_GAME);
        universe.notify();
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

    public void addText(String s) {
        bar2.append(s + "\n");
        bar2.setCaretPosition(bar2.getDocument().getLength());
    }
}
