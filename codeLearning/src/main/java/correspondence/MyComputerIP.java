package correspondence;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取本机的IP地址与电脑名称
 */
public class MyComputerIP {
    public static void main(String[] args) {
        System.out.println("Hello World");
        try {
            InetAddress hostIp = InetAddress.getLocalHost();
            System.out.println("本机名称：" + hostIp.getHostName() + "\n地址为：" + hostIp.getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
