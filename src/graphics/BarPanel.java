package graphics;

import events.*;
import logic.Planet;
import logic.Properties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BarPanel extends JPanel {

    private JButton buttonGold;
    private JButton buttonIron;
    private JButton buttonAngar;
    private JButton buttonShip;
    private Icon buttonShipIcon;
    private Icon buttonShipIconTrue;

    private ViewFrame frame;

    BarPanel(ViewFrame f) {
        ImageLoader loader = new ImageLoader(Properties.properties.images);
        frame = f;
        buttonGold = new JButton(/*"Построить золотую шахту"*/);
        buttonGold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonGoldPush();
            }
        });
        buttonGold.setIcon(new ImageIcon(loader.getImage("gold")));
        buttonGold.setBorder(BorderFactory.createEmptyBorder());
        add(buttonGold);
        buttonGold.setVisible(true);

        buttonIron = new JButton(/*"Построить железную шахту"*/);
        buttonIron.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonIronPush();
            }
        });
        buttonIron.setIcon(new ImageIcon(loader.getImage("iron")));
        buttonIron.setBorder(BorderFactory.createEmptyBorder());
        add(buttonIron);
        buttonIron.setVisible(true);

        buttonAngar = new JButton(/*"Построить ангар"*/);
        buttonAngar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAngarPush();
            }
        });
        buttonAngar.setIcon(new ImageIcon(loader.getImage("angar")));
        buttonAngar.setBorder(BorderFactory.createEmptyBorder());
        add(buttonAngar);
        buttonAngar.setVisible(true);

        buttonShip = new JButton(/*"Построить корабль"*/);
        buttonShip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonShipPush();
            }
        });
        buttonShipIcon = new ImageIcon(loader.getImage("shipBuild"));
        buttonShipIconTrue = new ImageIcon(loader.getImage("shipBuildTrue"));
        buttonShip.setIcon(buttonShipIcon);
        buttonShip.setBorder(BorderFactory.createEmptyBorder());
        add(buttonShip);
        buttonShip.setVisible(true);

        setVisible(true);
        setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
    }

    private void buttonGoldPush() {
        if (frame.currentObject instanceof Planet) {
            //((Planet)frame.currentObject).buildGoldFactory();
            frame.universe.eventCapture(events.Event.getGoldBuildEvent((Planet) frame.currentObject));
        }
    }

    private void buttonIronPush() {
        if (frame.currentObject instanceof Planet) {
            //((Planet) frame.currentObject).buildIronFactory();
            frame.universe.eventCapture(events.Event.getIronBuildEvent((Planet) frame.currentObject));
        }
    }

    private void buttonAngarPush() {
        if (frame.currentObject instanceof Planet) {
            //((Planet) frame.currentObject).buildAngar();
            frame.universe.eventCapture(events.Event.getAngarBuildEvent((Planet) frame.currentObject));
        }
    }

    private void buttonShipPush() {
        boolean build = false;
        if (frame.currentObject instanceof Planet) {
            build = !((Planet) frame.currentObject).isBuildShips();
            ((Planet) frame.currentObject).startBuild(build);
            if (build)
                buttonShip.setIcon(buttonShipIconTrue);
            else
                buttonShip.setIcon(buttonShipIcon);
            //((Planet)frame.currentObject).makeShip();
        }
    }

    public void enableButtons(boolean enable) {
        buttonGold.setEnabled(enable);
        buttonIron.setEnabled(enable);
        buttonAngar.setEnabled(enable);
        buttonShip.setEnabled(enable);
    }
}
