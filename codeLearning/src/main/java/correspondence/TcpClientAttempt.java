package correspondence;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TcpClientAttempt {
    public static void main(String[] args) {
        System.out.println("---TCP客户端---\n输入exit退出:");
        try {
            Socket socket = new Socket("127.0.0.1", 9528);
            OutputStream os = socket.getOutputStream();
            PrintStream ps = new PrintStream(os);
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("说：");
                String prepareMessage = sc.nextLine();
                ps.println(prepareMessage);
                ps.flush();
                if ("exit".equals(prepareMessage)) break;
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("客户端执行完毕");
        }
    }
}
