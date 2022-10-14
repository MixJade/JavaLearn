package helloWorld;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
        QuietNoNew.quietMethod();
        try {
            InetAddress hostIp = InetAddress.getLocalHost();
            System.out.println("本机名称：" + hostIp.getHostName() + "\n地址为：" + hostIp.getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}

class QuietNoNew{
    public static void quietMethod(){
        System.out.println("调用静态方法，不用新建对象。\n===========");
    }
}