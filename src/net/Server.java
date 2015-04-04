package net;

import events.Event;
import javafx.util.Pair;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;

import static events.Event.*;
import static events.Event.GameEventType.*;


public class Server {

    public static void main(String[] args) throws Exception {
        BlockingQueue<Pair<Pair<InetAddress, Integer>, String> > queue = new ArrayBlockingQueue<>(1024);
        CopyOnWriteArraySet<Integer> ports = new CopyOnWriteArraySet<>();
        CopyOnWriteArraySet<Pair<InetAddress, Integer>> addresses = new CopyOnWriteArraySet<>();

        MessageSender producer = new MessageSender(queue, ports, addresses);
        MessageGetter consumer = new MessageGetter(queue, ports, addresses);


        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

class MessageSender implements Runnable {
    BlockingQueue<Pair<Pair<InetAddress, Integer>, String> > queue = null;
    CopyOnWriteArraySet<Integer> ports = null;
    CopyOnWriteArraySet<Pair> addresses = new CopyOnWriteArraySet<>();

    Map<String, Game> games = new HashMap<>();

    MessageSender(BlockingQueue q, CopyOnWriteArraySet p, CopyOnWriteArraySet a) {
        queue = q;
        ports = p;
        addresses = a;
    }

    private static DatagramPacket encodePacket(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        return new DatagramPacket(bytes, bytes.length);
    }

    public void run() {
        while (true) {
            if (queue.size() == 0) {
                continue;
            }
            String msg = "";
            InetAddress adr = null;
            Pair<Pair<InetAddress, Integer>, String> p;
            int senderPort = 0;
            try {
                p = queue.take();
                msg = p.getValue();
                adr = p.getKey().getKey();
                senderPort = p.getKey().getValue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Event evt = fromString(msg), answer = null;
                String name = null;
                switch (evt.getType()) {
                    case LIST:
                        answer = new Event(MESSAGE);
                        String s = "";
                        for (String g: games.keySet()) {
                            s += g + ",";
                        }
                        answer.setProperty("games", s);
                        break;
                    case INFO:
                        answer = new Event(MESSAGE);
                        name = evt.getProperty("game_name");
                        answer.setProperty("num", games.get(name).getNumberOfPlayers().toString());
                        answer.setProperty("started", games.get(name).getIsStarted().toString());
                        break;
                    case JOIN_GAME:
                        answer = new Event(MESSAGE);
                        name = evt.getProperty("game_name");
                        games.get(name).addPlayer(new Pair(adr, senderPort));
                        answer.setProperty("pos", String.valueOf(games.get(name).getPlayers().size()));
                        break;
                    case CREATE_GAME:
                        answer = new Event(MESSAGE);
                        name = evt.getProperty("game_name");
                        games.put(name, new Game());
                        games.get(name).addPlayer(new Pair(adr, senderPort));
                        break;
                    case START_GAME:
                        name = evt.getProperty("game_name");
                        for (Pair<InetAddress, Integer> player: games.get(name).getPlayers()) {
                            DatagramSocket socket = new DatagramSocket();
                            DatagramPacket packet = encodePacket(evt.toString());
                            packet.setSocketAddress(new InetSocketAddress(player.getKey(), player.getValue()));
                            socket.send(packet);
                        }
                        continue;
                    case END_GAME:
                        for (Map.Entry<String, Game> g : games.entrySet()) {
                            if (g.getValue().contains(new Pair<>(adr, senderPort))) {
                                name = g.getKey();
                                break;
                            }
                        }
                        if (name == null) continue;
                        for (Pair<InetAddress, Integer> player: games.get(name).getPlayers()) {
                            DatagramSocket socket = new DatagramSocket();
                            DatagramPacket packet = encodePacket(evt.toString());
                            packet.setSocketAddress(new InetSocketAddress(player.getKey(), player.getValue()));
                            socket.send(packet);
                        }
                        games.remove(name);
                        continue;
                    default:
                        Game current = null;
                        for (Map.Entry<String, Game> g : games.entrySet()) {
                            if (g.getValue().contains(new Pair<>(adr, senderPort))) {
                                current = g.getValue();
                                break;
                            }
                        }
                        if (current == null) continue;
                        for (Pair<InetAddress, Integer> player: current.getPlayers()) {
                            if (player.equals(new Pair(adr, senderPort)))
                                continue;
                            DatagramSocket socket = new DatagramSocket();
                            DatagramPacket packet = encodePacket(evt.toString());
                            packet.setSocketAddress(new InetSocketAddress(player.getKey(), player.getValue()));
                            socket.send(packet);
                        }
                }
                DatagramSocket socket = new DatagramSocket();
                DatagramPacket packet = encodePacket(answer.toString());
                packet.setSocketAddress(new InetSocketAddress(adr, senderPort));
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class MessageGetter implements Runnable {
    BlockingQueue<Pair<Pair<InetAddress, Integer>, String> > queue = null;
    CopyOnWriteArraySet<Integer> ports = null;
    CopyOnWriteArraySet<Pair> addresses = new CopyOnWriteArraySet<>();
    MessageGetter(BlockingQueue q, CopyOnWriteArraySet p, CopyOnWriteArraySet a) {
        queue = q;
        ports = p;
        addresses = a;
    }
    private static String decodePacket(DatagramPacket packet) {
        return new String(
                packet.getData(),
                packet.getOffset(),
                packet.getOffset() + packet.getLength(),
                StandardCharsets.UTF_8);
    }
    public void run() {
        while (true) {
            try (DatagramSocket socket = new DatagramSocket(11111)) {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String msg = decodePacket(packet);
                System.out.println(msg + " " + packet.getAddress());
                Integer port = Integer.valueOf(msg.substring(0, msg.indexOf(":")));
                queue.add(new Pair(new Pair(packet.getAddress(), port), msg.substring(msg.indexOf(":") + 1)));
                addresses.add(new Pair(packet.getAddress(), port));
                ports.add(Integer.valueOf(msg.substring(0, msg.indexOf(":"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}