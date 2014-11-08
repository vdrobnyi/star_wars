
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class ViewPanel extends JPanel implements ActionListener {
    private Universe universe;
    private Timer timer;
    private ViewFrame frame;
    private int tickNumber;
    private Image shipImage;
    private Image planetImage;
    private Image universeImage;
    private double mouseX = 100;
    private double mouseY = 100;

    public ViewPanel(ViewFrame f) {
        tickNumber = 0;
        universe = ViewFrame.universe;
        frame = f;
        timer = new Timer((int) (1000 / Properties.properties.TICKS_PER_SEC), this);
        timer.setInitialDelay(200);
        timer.start();

        shipImage = Toolkit.getDefaultToolkit().getImage("images\\ship.gif");
        planetImage = Toolkit.getDefaultToolkit().getImage("images\\planet.gif");
        universeImage = Toolkit.getDefaultToolkit().getImage("images\\universe.jpg");

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                double eX = e.getX();
                double eY = e.getY();
                double x = (eX + frame.startX) / (Properties.properties.UNIVERSE_SIZE_X / 2.0) - 1;
                double y = (eY + frame.startY) / (Properties.properties.UNIVERSE_SIZE_Y / 2.0) - 1;

                if (e.getButton() == MouseEvent.BUTTON1) {
                    SpaceShip s = frame.currentPlayer.getShip(x, y);
                    if (s != null) {
                        frame.currentObject = s;
                        frame.getBarPanel().enableButtons(false);
                    } else {
                        Planet p = universe.getPlanet(x, y);
                        if (p != null && frame.currentPlayer.equals(p.getMaster())) {
                            frame.currentObject = p;
                            frame.getBarPanel().enableButtons(true);
                        } else {
                            frame.currentObject = null;
                            frame.getBarPanel().enableButtons(false);
                        }
                    }
                }
                else if (e.getButton() == MouseEvent.BUTTON3 && frame.currentObject != null && frame.currentObject instanceof SpaceShip)
                    ((SpaceShip) frame.currentObject).move(x, y);
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
           }
        });

    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        moveScreen();
        g2.drawImage(universeImage,
                0, 0, Properties.properties.PANEL_SIZE_X, Properties.properties.PANEL_SIZE_Y, this);

        Color[] c = new Color[]{Color.RED, Color.GREEN, Color.DARK_GRAY};

        for (Planet p : universe.getPlanets()) {
            g2.drawImage(planetImage,
                    -frame.startX + (int)(p.getX() * Properties.properties.UNIVERSE_SIZE_X / 2 + Properties.properties.UNIVERSE_SIZE_X / 2 - 10 * p.getRadius()),
                    -frame.startY + (int)(p.getY() * Properties.properties.UNIVERSE_SIZE_Y / 2 + Properties.properties.UNIVERSE_SIZE_Y / 2 - 10 * p.getRadius()),
                    (int)(p.getRadius() * 20), (int)(p.getRadius() * 20), this);

            if (p.getMaster() != null) {
                if (p.getMaster() == frame.getCurrentPlayer()) {
                    g2.setColor(c[1]);
                } else {
                    g2.setColor(c[2]);
                }
                g2.drawOval(-frame.startX + (int)(p.getX() * Properties.properties.UNIVERSE_SIZE_X / 2 + Properties.properties.UNIVERSE_SIZE_X / 2 - 10 * p.getRadius()),
                            -frame.startY + (int)(p.getY() * Properties.properties.UNIVERSE_SIZE_Y / 2 + Properties.properties.UNIVERSE_SIZE_Y / 2 - 10 * p.getRadius()),
                            (int)(p.getRadius() * 20),
                            (int)(p.getRadius() * 20));
            }
        }
        for (Player p : universe.getPlayers()) {
            for (SpaceShip s : p.getShips()) {
                if (p.equals(frame.getCurrentPlayer())) {
                    g2.setColor(c[1]);
                } else {
                    g2.setColor(c[2]);
                }
                g2.drawOval(-frame.startX + (int)(s.getX() * Properties.properties.UNIVERSE_SIZE_X / 2 + Properties.properties.UNIVERSE_SIZE_X / 2 - 10 * s.getRadius() - 3),
                            -frame.startY + (int)(s.getY() * Properties.properties.UNIVERSE_SIZE_Y / 2 + Properties.properties.UNIVERSE_SIZE_Y / 2 - 10 * s.getRadius() - 3),
                            (int)(s.getRadius() * 20 + 6),
                            (int)(s.getRadius() * 20 + 6));

                g2.drawImage(shipImage,
                        -frame.startX + (int)(s.getX() * Properties.properties.UNIVERSE_SIZE_X / 2 + Properties.properties.UNIVERSE_SIZE_X / 2 - 10 * s.getRadius()),
                        -frame.startY + (int)(s.getY() * Properties.properties.UNIVERSE_SIZE_Y / 2 + Properties.properties.UNIVERSE_SIZE_Y / 2 - 10 * s.getRadius()),
                        (int)(s.getRadius() * 20), (int)(s.getRadius() * 20), this);
                g2.setColor(Color.WHITE);
                g2.drawString("Player: gold - " + (int)frame.getCurrentPlayer().getGold() + ", iron - " + (int)frame.getCurrentPlayer().getIron(), 10, 10);
                g2.drawString("tick number: " + tickNumber, 10, 30);
            }
        }
        //if (frame.currentObject instanceof SpaceShip && ((SpaceShip)frame.currentObject).isAlive())
        drawCurrent(g2);
        if (universe.getBullets().size() > 0) {
            for (Bullet b : universe.getBullets()) {
                if (b.getPlayer().equals(frame.currentPlayer))
                    g2.setColor(Color.ORANGE);
                else
                    g2.setColor(Color.DARK_GRAY);
                g2.drawLine(-frame.startX + (int)(b.getX1() * Properties.properties.UNIVERSE_SIZE_X / 2 + Properties.properties.UNIVERSE_SIZE_X / 2),
                            -frame.startY + (int)(b.getY1() * Properties.properties.UNIVERSE_SIZE_Y / 2 + Properties.properties.UNIVERSE_SIZE_Y / 2),
                            -frame.startX + (int)(b.getX2() * Properties.properties.UNIVERSE_SIZE_X / 2 + Properties.properties.UNIVERSE_SIZE_X / 2),
                            -frame.startY + (int)(b.getY2() * Properties.properties.UNIVERSE_SIZE_Y / 2 + Properties.properties.UNIVERSE_SIZE_Y / 2));
            }
        }
        g2.setColor(Color.WHITE);
        g2.fillRect(Properties.properties.PANEL_SIZE_X, 0, 1000, 1000);


        g2.finalize();
    }

    private void drawCurrent(Graphics2D g2) {
        if (frame.currentObject != null) {
            g2.setColor(Color.RED);
            g2.drawOval(-frame.startX + (int)(frame.currentObject.getX() * Properties.properties.UNIVERSE_SIZE_X / 2 + Properties.properties.UNIVERSE_SIZE_X / 2 - 10 * frame.currentObject.getRadius() - 5),
                        -frame.startY + (int)(frame.currentObject.getY() * Properties.properties.UNIVERSE_SIZE_Y / 2 + Properties.properties.UNIVERSE_SIZE_Y / 2 - 10 * frame.currentObject.getRadius() - 5),
                        (int)(frame.currentObject.getRadius() * 20 + 10),
                        (int)(frame.currentObject.getRadius() * 20 + 10));
        }
    }

    private void tick() {
        universe.tick();
        tickNumber++;
    }

    private void moveScreen() {
        if (Math.abs(mouseX - 0) < Properties.properties.MOVE_SCREEN_AREA) {
            frame.startX -= 10;
        }
        if (Math.abs(mouseX - Properties.properties.PANEL_SIZE_X) < Properties.properties.MOVE_SCREEN_AREA) {
            frame.startX += 10;
        }
        if (Math.abs(mouseY - 0) < Properties.properties.MOVE_SCREEN_AREA) {
            frame.startY -= 10;
        }
        if (Math.abs(mouseY - Properties.properties.PANEL_SIZE_Y) < Properties.properties.MOVE_SCREEN_AREA) {
            frame.startY += 10;
        }
        if (frame.startX < 0) frame.startX = 0;
        if (frame.startX > Properties.properties.UNIVERSE_SIZE_X - Properties.properties.PANEL_SIZE_X)
            frame.startX = Properties.properties.UNIVERSE_SIZE_X - Properties.properties.PANEL_SIZE_X;
        if (frame.startY < 0) frame.startY = 0;
        if (frame.startY > Properties.properties.UNIVERSE_SIZE_Y - Properties.properties.PANEL_SIZE_Y)
            frame.startY = Properties.properties.UNIVERSE_SIZE_Y - Properties.properties.PANEL_SIZE_Y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tick();
        repaint();
    }
}