package com.demo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.net.InetAddress;

// 使用 @WebListener 注解注册监听器
@WebListener
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("========WAR包已成功初始化========");
        try {
            InetAddress ip01 = InetAddress.getLocalHost();
            //noinspection HttpUrlsUsage
            System.out.println("局域IP: http://" + ip01.getHostAddress() + ":7841");
            Runtime.getRuntime().exec("cmd /c start http://localhost:7841");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 当 ServletContext 销毁时，可以添加相应的逻辑
    }
}