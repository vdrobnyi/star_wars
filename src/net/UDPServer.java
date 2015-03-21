
import javafx.util.Pair;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;


public class UDPServer {

    public static void main(String[] args) throws Exception {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1024);
        CopyOnWriteArraySet<Integer> ports = new CopyOnWriteArraySet<>();
        CopyOnWriteArraySet<Pair<InetAddress, Integer>> addresses = new CopyOnWriteArraySet<>();

        MessageSenderS producer = new MessageSenderS(queue, ports, addresses);
        MessageGetterS consumer = new MessageGetterS(queue, ports, addresses);


        new Thread(producer).start();
        new Thread(consumer).start();
    }


    private static String decodePacket(DatagramPacket packet) {
        return new String(
                packet.getData(),
                packet.getOffset(),
                packet.getOffset() + packet.getLength(),
                StandardCharsets.UTF_8);
    }

    private static DatagramPacket encodePacket(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        return new DatagramPacket(bytes, bytes.length);
    }
}

class MessageSenderS implements Runnable {
    BlockingQueue<String> queue = null;
    CopyOnWriteArraySet<Integer> ports = null;
    CopyOnWriteArraySet<Pair> addresses = new CopyOnWriteArraySet<>();
    MessageSenderS(BlockingQueue q, CopyOnWriteArraySet p, CopyOnWriteArraySet a) {
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
            try {
                msg = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                DatagramSocket socket = new DatagramSocket();
                DatagramPacket packet = encodePacket(msg.substring(msg.indexOf(":") + 1));
                int senderPort = Integer.parseInt(msg.substring(0, msg.indexOf(":")));
                for (Pair<InetAddress, Integer> a: addresses) {
                    //if (p.equals(Integer.valueOf(msg.substring(0, msg.indexOf(":")))))
                    //    continue;
                    if (a.getValue().equals(senderPort))
                        continue;
                    packet.setSocketAddress(new InetSocketAddress(a.getKey(), a.getValue()));
                    socket.send(packet);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class MessageGetterS implements Runnable {
    BlockingQueue<String> queue = null;
    CopyOnWriteArraySet<Integer> ports = null;
    CopyOnWriteArraySet<Pair> addresses = new CopyOnWriteArraySet<>();
    MessageGetterS(BlockingQueue q, CopyOnWriteArraySet p, CopyOnWriteArraySet a) {
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
                queue.add(msg);
                Integer port = Integer.valueOf(msg.substring(0, msg.indexOf(":")));
                addresses.add(new Pair(packet.getAddress(), port));
                ports.add(Integer.valueOf(msg.substring(0, msg.indexOf(":"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println("Data prepared");
        }
    }
}