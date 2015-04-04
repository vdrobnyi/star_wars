package wizard;

import events.Event;
import net.ClientSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Wizard {
    private ClientSender client;
    private boolean isStarted = false;

    public Wizard(ClientSender client) {
        this.client = client;
    }

    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (!isStarted) {
            String msg = null;
            System.out.println("list - to show all games\n" +
                    "info game_name - to show info about game game_name\n" +
                    "create game_name\n" +
                    "join game_name\n" +
                    "start game_name\n");
            try {
                msg = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (msg.split(" ")[0]) {
                case "list":
                    msg = "Type:LIST";
                    break;
                case "info":
                    msg = "Type:INFO game_name:" + msg.split(" ")[1];
                    break;
                case "create":
                    msg = "Type:CREATE_GAME game_name:" + msg.split(" ")[1];
                    break;
                case "join":
                    msg = "Type:JOIN_GAME game_name:" + msg.split(" ")[1];
                    break;
                case "start":
                    msg = "Type:START_GAME game_name:" + msg.split(" ")[1];
                    isStarted = true;
                    break;
            }
            client.eventCapture(Event.fromString(msg));
        }
    }
}
