package correspondence;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServerAttempt {
    public static void main(String[] args) {
        System.out.println("---TCP服务端---");
        try {
            ServerSocket ss = new ServerSocket(9528);
            Socket socket = ss.accept();
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(socket.getRemoteSocketAddress() + " 说了： " + str);
            }
            ss.close();
            System.out.println("服务端退出");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
