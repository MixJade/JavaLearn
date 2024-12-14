package correspondence;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 查看当前局域网的在线设备数
 *
 * @since 2024-12-14 20:28:31
 */
public class NetworkScanner {
    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        String localHostAddress = InetAddress.getLocalHost().getHostAddress();
        // 获取当前设备的网段
        String subnet = getSubNet(localHostAddress);
        // 从0到255进行遍历
        for (int i = 1; i < 255; i++) {
            final int j = i;
            executorService.execute(() -> {
                String host = subnet + j;
                try {
                    InetAddress inetAddress = InetAddress.getByName(host);
                    boolean res = inetAddress.isReachable(1500);
                    if (res) {
                        System.out.println(host + "设备在线");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();

        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("扫描结束");
                break;
            }
            //noinspection BusyWait
            Thread.sleep(500);
        }
    }


    /**
     * 获取当前设备的网段
     *
     * @param ipAddress 192.168.1.1
     * @return 192.168.1.
     */
    private static String getSubNet(String ipAddress) {
        int lastDot = ipAddress.lastIndexOf(".");
        return ipAddress.substring(0, lastDot + 1);
    }
}
