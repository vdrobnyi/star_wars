import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class BarPanel extends JPanel {

    private JButton buttonGold;
    private JButton buttonIron;
    private JButton buttonAngar;
    private JButton buttonShip;

    private ViewFrame frame;

    BarPanel(ViewFrame f) {

        frame = f;
        buttonGold = new JButton(/*"Построить золотую шахту"*/);
        buttonGold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonGoldPush();
            }
        });
        buttonGold.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("images\\gold.jpg")));
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
        buttonIron.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("images\\iron.jpg")));
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
        buttonAngar.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("images\\angar.jpg")));
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
        buttonShip.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("images\\shipBuild.jpg")));
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
            ((Planet)frame.currentObject).buildGoldFactory();
        }
    }

    private void buttonIronPush() {
        if (frame.currentObject instanceof Planet) {
            ((Planet) frame.currentObject).buildIronFactory();
        }
    }

    private void buttonAngarPush() {
        if (frame.currentObject instanceof Planet) {
            ((Planet) frame.currentObject).buildAngar();
        }
    }

    private void buttonShipPush() {
        if (frame.currentObject instanceof Planet) {
            ((Planet)frame.currentObject).makeShip();
        }
    }

    public void enableButtons(boolean enable) {
        buttonGold.setEnabled(enable);
        buttonIron.setEnabled(enable);
        buttonAngar.setEnabled(enable);
        buttonShip.setEnabled(enable);
    }
}
