package correspondence;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 打印自己所有的网络地址(包括VPN)
 *
 * @since 2025-05-08 16:14:06
 */
public class NetworkIP {
    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr.isLinkLocalAddress()) {
                        continue;
                    }
                    System.out.println("➜  Network: " + addr.getHostAddress());
                }
            }
        } catch (SocketException e) {
            System.err.println("获取网络接口时出现错误: " + e.getMessage());
        }
    }
}    