package correspondence;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 获取本机的IP地址与电脑名称
 */
public class MyComputerIP {
    public static void main(String[] args) {
        try {
            InetAddress hostIp = InetAddress.getLocalHost();
            System.out.println("本机名称: " + hostIp.getHostName());
            System.out.println("IP地址:  " + hostIp.getHostAddress());

            // 附加：获取Mac地址
            NetworkInterface network = NetworkInterface.getByInetAddress(hostIp);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println("MAC地址: " + sb);
        } catch (UnknownHostException e) {
            System.out.println("IP地址获取失败");
        } catch (SocketException e) {
            System.out.println("Mac地址获取失败");
        }
    }
}
