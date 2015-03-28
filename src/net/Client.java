package net;

import events.*;
import logic.Universe;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;


//EL: лучше бы имя содержало слово Sender, например, ClientSender
public class Client implements EventListenerInterface {
    private Universe universe;
    private int serverPort;
    private String server;
    private int clientPort;

    public Client(int clientPort, Universe universe, String server, int serverPort) {
        this.universe = universe;
        this.server = server;
        this.serverPort = serverPort;
        this.clientPort = clientPort;
        universe.addListener(this);
        MessageGetterC consumer = new MessageGetterC(clientPort, universe);
        Thread t = new Thread(consumer);
        t.start();
    }

    public void eventCapture(Event event) {
        sendMessage(event.toString(), server, serverPort);
    }

    private void sendMessage(String msg, String server, int serverPort) {
        try {
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = encodePacket(
                    String.valueOf(clientPort) + ":" + msg);
            packet.setSocketAddress(new InetSocketAddress(server, serverPort));
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DatagramPacket encodePacket(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        return new DatagramPacket(bytes, bytes.length);
    }
}

//EL: лучше бы имя содержало слово Receiver, например, ClientReceiver
class MessageGetterC implements Runnable {
    Integer port;
    Universe universe;

    MessageGetterC(Integer p, Universe u) {
        port = p;
        universe = u;
    }

    private static String decodePacket(DatagramPacket packet) {
        return new String(
                packet.getData(),
                packet.getOffset(),
                packet.getOffset() + packet.getLength(),
                StandardCharsets.UTF_8);
    }

    private void notify(Event event) {
        universe.eventCapture(event);
    }

    public void run() {
        while (true) {

            try (DatagramSocket socket = new DatagramSocket(port)) {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String msg = decodePacket(packet);
                notify(Event.fromString(msg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
