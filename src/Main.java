
import graphics.ViewFrame;
import logic.Player;
import logic.Universe;
import logic.UniverseLoader;
import net.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        UniverseLoader ul = new UniverseLoader();
        System.out.println("Start game");
        Universe u = new Universe(true);//ul.loadUniverse("out.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String server = "localhost";
        int serverPort = 0, clientPort = 0;
        try {
            System.out.print("Input server address: ");
            server = br.readLine();
            System.out.print("Input server port: ");
            serverPort = Integer.valueOf(br.readLine());
            System.out.print("Input your port: ");
            clientPort = Integer.valueOf(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Client client = new Client(clientPort, u, server, serverPort);
        Player current = u.getPlayers().get(0);
        ViewFrame v = new ViewFrame(u, current);
    }
}
