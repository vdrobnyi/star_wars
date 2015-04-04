
import events.Event;
import graphics.ViewFrame;
import logic.Player;
import logic.Universe;
import logic.UniverseLoader;
import net.ClientSender;
import wizard.Wizard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        UniverseLoader ul = new UniverseLoader();
        System.out.println("Start game");
        Universe u = new Universe(true);//ul.loadUniverse("out.txt");

        //EL: Create StartGameWizard class
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String server = "localhost";
        int serverPort = 11111, clientPort = 22222;
        try {
            System.out.print("Input server address: ");
            server = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClientSender clientSender = new ClientSender(clientPort, u, server, serverPort);
        Player current = u.getPlayers().get(1);
        ViewFrame v = new ViewFrame(u, current);

        Wizard w = new Wizard(clientSender);
        w.run();

        /*while (!v.isVisible()) {
            String msg = null;
            try {
                msg = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientSender.eventCapture(Event.fromString(msg));
        }*/
    }
}
