package mix.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 专门处理命令行输出流的线程
 */
public class RunProcessThread extends Thread {
    private final InputStream ioStream;

    public RunProcessThread(InputStream ioStream) {
        this.ioStream = ioStream;
    }

    @Override
    public void run() {
        // 仅仅只是象征性的读一下，防止命令行的缓冲区爆炸
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ioStream))) {
            //noinspection StatementWithEmptyBody
            while (reader.readLine() != null) {
            }
        } catch (IOException ignored) {
        }
    }
}
