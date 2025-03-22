package correspondence;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

interface DownParam {
    String cookie = "yyy=xxx";
    String url = "http://localhost:9527/public/wallpaper/wp003.jpg";
    String fileName = "wp003.jpg";
}

/**
 * 下载线程的计数器对象
 */
class DownData {
    // 对象索引
    private final Integer name;
    // 下载的字节数
    private long downKB = 0;

    public DownData(Integer name) {
        this.name = name;
    }

    public long getDownKB() {
        return downKB;
    }

    public void addKB(long downKB) {
        this.downKB += downKB >> 10; // 2^10 = 1024
    }

    @Override
    public String toString() {
        return "Down{" +
                "name=" + name +
                ", downKB=" + downKB +
                '}';
    }
}


// 下载文件的线程类
class DownloadThread implements Runnable {
    private final String url;
    private final long start;
    private final long end;
    private final DownData downData;
    private final String filePath;

    public DownloadThread(String url, long start, long end, DownData downData, String filePath) {
        this.url = url;
        this.start = start;
        this.end = end;
        this.downData = downData;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            URL fileUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
            connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("cookie", DownParam.cookie);
            connection.connect();

            try (InputStream in = connection.getInputStream();
                 RandomAccessFile raf = new RandomAccessFile(filePath, "rw")) {
                raf.seek(start);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    downData.addKB(bytesRead); // 进行计数
                    raf.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 多线程下载大文件
 *
 * @since 2025-03-22 23:59:30
 */
public class MultiThreadDownloader {
    public static void downloadFile(String url, String filePath, int threadCount) throws IOException {
        URL fileUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("cookie", DownParam.cookie);
        long fileSize = connection.getContentLengthLong();
        long fileSizeKb = fileSize >> 10;
        System.out.println("文件大小:" + fileSizeKb + "kb,线程数:" + threadCount);
        connection.disconnect();

        // 按线程数拆分每部分大小
        long partSize = fileSize / threadCount;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        // 加上数据记录
        List<DownData> downDataList = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            downDataList.add(new DownData(i));
        }
        // 开辟线程
        for (int i = 0; i < threadCount; i++) {
            long start = i * partSize;
            long end = (i == threadCount - 1) ? fileSize - 1 : start + partSize - 1;
            executor.submit(new DownloadThread(url, start, end, downDataList.get(i), filePath));
        }
        executor.shutdown();

        // 计数器打印进度,创建一个 Timer 对象
        Timer timer = new Timer();
        // 安排一个任务每秒执行一次
        timer.scheduleAtFixedRate(new TimerTask() {
            long nowFileSize = 0;

            @Override
            public void run() {
                long newFileSize = calculateTotal(downDataList);
                System.out.println("已下载(" + newFileSize + "/" + fileSizeKb + "),速度:" + (newFileSize - nowFileSize) + "kb/s");
                nowFileSize = newFileSize;
            }
        }, 0, 1000);

        try {
            boolean terminated = executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            if (terminated) {
                System.out.println("所有任务已完成，线程池已关闭");
                timer.cancel();
            } else {
                System.out.println("超时，仍有任务未完成");
                // 可以选择强制关闭线程池
                executor.shutdownNow();
                timer.cancel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 计算 List 中所有 DownData 对象的 getDownKB() 总和
    private static long calculateTotal(List<DownData> downDataList) {
        long total = 0;
        for (DownData data : downDataList) {
            total += data.getDownKB();
        }
        return total;
    }

    public static void main(String[] args) {
        String fileUrl = DownParam.url;
        String filePath = DownParam.fileName;
        int threadCount = 5;

        try {
            downloadFile(fileUrl, filePath, threadCount);
            System.out.println("文件下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}