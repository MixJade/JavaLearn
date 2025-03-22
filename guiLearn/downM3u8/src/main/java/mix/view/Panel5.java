package mix.view;

import mix.controller.DownBigData;
import mix.controller.DownBigThread;
import mix.utils.DownFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 单纯的下载工具
 */
public class Panel5 extends JPanel implements ActionListener {
    private final JTextField downUrl, down;
    private final JButton saveBtn;
    private final JProgressBar progressBar;
    private final JLabel speedDown;

    public Panel5() {
        // 设置布局
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gbc.fill = GridBagConstraints.NONE; // 如果组件所在的区域比组件大,不调整组件大小。

        // 开局提示
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 12;
        gbc.gridheight = 1;
        add(new JLabel("与电影无关,单纯的下载工具"), gbc);

        // 下载url
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("下载url"), gbc);
        // 对输入框进行设置
        downUrl = new JTextField("http://localhost:9527/public/wallpaper/wp003.jpg", 20);
        gbc.gridx = 1;
        gbc.gridwidth = 11;
        add(downUrl, gbc);

        // 文件名
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(new JLabel("文件名"), gbc);
        // 文件名输入框
        down = new JTextField("xxx.jpg", 20);
        gbc.gridx = 1;
        gbc.gridwidth = 11;
        add(down, gbc);

        // 下载按钮
        JButton reqFileBtn = new JButton("设请求头");
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        reqFileBtn.setActionCommand("REQ_FILE_BTN");
        reqFileBtn.addActionListener(this);
        add(reqFileBtn, gbc);


        // 下载按钮
        saveBtn = new JButton("下载文件");
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        saveBtn.setActionCommand("SAVE_BTN");
        saveBtn.addActionListener(this);
        add(saveBtn, gbc);

        // 下载进度lab
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(new JLabel("下载进度"), gbc);
        // 下载进度条
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 10;
        add(progressBar, gbc);

        // 下载速度
        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.gridwidth = 6;
        // 下载速度
        speedDown = new JLabel("速度: 0kb/s");
        add(speedDown, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("SAVE_BTN".equals(e.getActionCommand())) {
            saveBtn.setEnabled(false); // 禁止多次点击
            DownFile.readReqConfig();
            // 异步执行防止卡死
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    downBigFile();
                    return null;
                }
            }.execute();
        } else if ("REQ_FILE_BTN".equals(e.getActionCommand())) {
            try {
                File reqFile = new File("reqHeadConfig.properties");
                if (reqFile.exists())
                    Desktop.getDesktop().open(reqFile);
                else {
                    if (reqFile.createNewFile()) Desktop.getDesktop().open(reqFile);
                    else JOptionPane.showMessageDialog(null, "请求头文件建立失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 下载大的文件
     */
    private void downBigFile() {
        String webUrl = downUrl.getText();
        String filePath = down.getText();
        try {
            // 下载文件并保存
            downloadFile(webUrl, filePath);
            JOptionPane.showMessageDialog(null, "文件已保存至" + filePath, "反馈", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ioException) {
            saveBtn.setEnabled(true); // 下载失败就恢复点击
            JOptionPane.showMessageDialog(null, "文件下载失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void downloadFile(String url, String filePath) throws IOException {
        URL fileUrl = new URL(url);
        int threadCount = 5; // 下载线程数
        HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        DownFile.setReqHead(conn);
        long fileSize = conn.getContentLengthLong();
        int fileSizeKb = (int) (fileSize >> 10);
        progressBar.setMaximum(fileSizeKb);
        conn.disconnect();

        // 按线程数拆分每部分大小
        long partSize = fileSize / threadCount;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        // 加上数据记录
        java.util.List<DownBigData> downDataList = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            downDataList.add(new DownBigData(i));
        }
        // 开辟线程
        for (int i = 0; i < threadCount; i++) {
            long start = i * partSize;
            long end = (i == threadCount - 1) ? fileSize - 1 : start + partSize - 1;
            executor.submit(new DownBigThread(url, start, end, downDataList.get(i), filePath));
        }
        executor.shutdown();

        // 计数器打印进度,创建一个 Timer 对象
        java.util.Timer timer = new java.util.Timer();
        // 安排一个任务每秒执行一次
        timer.scheduleAtFixedRate(new TimerTask() {
            long nowFileSize = 0;

            @Override
            public void run() {
                int newFileSize = calculateTotal(downDataList);
                progressBar.setValue(newFileSize);
                progressBar.setString(newFileSize + "/" + fileSizeKb);
                speedDown.setText("速度: " + (newFileSize - nowFileSize) + "kb/s");
                // 新旧进度交替放最后
                nowFileSize = newFileSize;
            }
        }, 0, 1000);

        try {
            boolean terminated = executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            if (terminated) {
                timer.cancel();
            } else {
                // 可以选择强制关闭线程池
                executor.shutdownNow();
                timer.cancel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 计算 List 中所有 DownData 对象的 getDownKB() 总和
    private static int calculateTotal(List<DownBigData> downDataList) {
        int total = 0;
        for (DownBigData data : downDataList) {
            total += data.getDownKB();
        }
        return total;
    }
}
