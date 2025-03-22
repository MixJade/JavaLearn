package mix.controller;

import mix.utils.DownFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

// 下载文件的线程类
public class DownBigThread implements Runnable {
    private final String url;
    private final long start;
    private final long end;
    private final DownBigData downData;
    private final String filePath;

    public DownBigThread(String url, long start, long end, DownBigData downData, String filePath) {
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
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            DownFile.setReqHead(conn);
            conn.connect();

            try (InputStream in = conn.getInputStream(); RandomAccessFile raf = new RandomAccessFile(filePath, "rw")) {
                raf.seek(start);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    downData.addKB(bytesRead); // 进行计数
                    raf.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException ignored) {
        }
    }
}