package correspondence;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class udpClientAttempt {
    public static void main(String[] args) {
        System.out.println("---客户端开始运行---");
        udpClientOnce();
        udpClientRepeated();
    }

    private static void udpClientRepeated() {
        try {
            DatagramSocket socket = new DatagramSocket();
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("请说：");
                String str = sc.nextLine();
                byte[] sendMessage = str.getBytes();
                DatagramPacket packet = new DatagramPacket(sendMessage, sendMessage.length, InetAddress.getLocalHost(), 9527);
                socket.send(packet);
                System.out.println("发送成功");
                if ("exit".equals(str)) {
                    socket.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("客户端结束");
        }
    }

    private static void udpClientOnce() {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] sendMessage = "鸟宿池边树，僧敲月下门".getBytes();
            DatagramPacket packet = new DatagramPacket(sendMessage, sendMessage.length, InetAddress.getLocalHost(), 9527);
            socket.send(packet);
            socket.close();
            System.out.println("数据发送完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
