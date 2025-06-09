package correspondence;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多线程扫描指定ip的端口
 *
 * @since 2025-06-09 22:02:02
 */
public class MultiThreadedPortScanner {
    public static void main(String[] args) {
        String ip = "192.168.1.9";
        int timeout = 2000;
        int maxThreads = 100; // 最大线程数

        ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

        for (int port = 1; port <= 65535; port++) {
            final int currentPort = port;
            executor.submit(() -> {
                try (Socket socket = new Socket()) {
                    socket.connect(new java.net.InetSocketAddress(ip, currentPort), timeout);
                    System.out.printf("端口 %d 是开放的%n", currentPort);
                } catch (IOException e) {
                    // 端口关闭
                }
            });
        }

        executor.shutdown();
        try {
            boolean termination = executor.awaitTermination(1, TimeUnit.MINUTES);
            System.out.println("最后停止：" + termination);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}