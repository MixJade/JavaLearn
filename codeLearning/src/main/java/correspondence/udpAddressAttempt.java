package correspondence;

import java.net.InetAddress;

public class udpAddressAttempt {
    public static void main(String[] args) throws Exception {
        System.out.println("---获取本机地址对象---");
        InetAddress ip01 = InetAddress.getLocalHost();
        System.out.println("主机名称：" + ip01.getHostName());
        System.out.println("主机的IP地址: " + ip01.getHostAddress());

        System.out.println("---获取域名IP对象---");
        InetAddress ip02 = InetAddress.getByName("www.baidu.com");
        System.out.println("网站名称：" + ip02.getHostName());
        System.out.println("网站IP地址：" + ip02.getHostAddress());
        System.out.println("检查是否能够在5s内联通：" + ip02.isReachable(5000));
    }
}
