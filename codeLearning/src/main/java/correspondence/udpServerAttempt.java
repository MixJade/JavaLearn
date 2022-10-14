package correspondence;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class udpServerAttempt {
    public static void main(String[] args) {
        System.out.println("---服务端开始运行---");
        udpServerRepeated();
    }

    private static void udpServerRepeated() {
        try {
            DatagramSocket socket = new DatagramSocket(9527);
            byte[] receiveMessage = new byte[1024 * 60];
            DatagramPacket packet = new DatagramPacket(receiveMessage, receiveMessage.length);
            while (true) {
                socket.receive(packet);
                int receiveLength = packet.getLength();
                String receiveClient = new String(receiveMessage, 0, receiveLength);
                System.out.println("收到信息：" + receiveClient);
                if ("exit".equals(receiveClient)) {
                    socket.close();
                    String sendIp = packet.getSocketAddress().toString();
                    System.out.println("发送者的IP为：" + sendIp);
                    int sendPort = packet.getPort();
                    System.out.println("发送者端口为：" + sendPort);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("服务端结束");
        }
    }

    private static void udpServerOnce() {
        // 这个方法仅供参考，实际执行会跟上面那个方法冲突
        try {
            DatagramSocket socket = new DatagramSocket(9527);
            byte[] receiveMessage = new byte[1024 * 60];
            DatagramPacket packet = new DatagramPacket(receiveMessage, receiveMessage.length);
            socket.receive(packet);
            int receiveLength = packet.getLength();
            String receiveClient = new String(receiveMessage, 0, receiveLength);
            System.out.println("收到信息为：" + receiveClient);
            String sendIp = packet.getSocketAddress().toString();
            System.out.println("发送者的IP为：" + sendIp);
            int sendPort = packet.getPort();
            System.out.println("发送者端口为：" + sendPort);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
