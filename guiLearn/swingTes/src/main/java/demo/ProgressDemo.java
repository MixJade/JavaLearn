package demo;

import javax.swing.*;

/**
 * 进度条demo
 *
 * @since 2024-10-09 16:21:54
 */
public class ProgressDemo {
    public static void main(String[] args) {
        // 创建一个窗口
        JFrame frame = new JFrame("Progress Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        final int MAX = 100;
        // 创建一个进度条
        JProgressBar progressBar = new JProgressBar(0, MAX);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        frame.add(progressBar);
        frame.setVisible(true);

        // 更新进度条
        for (int i = 0; i <= MAX; i++) {
            progressBar.setValue(i);
            progressBar.setString(i + "/" + MAX);
            try {
                Thread.sleep(100);  // 模拟一些工作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}