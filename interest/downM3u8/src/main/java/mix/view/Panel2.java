package mix.view;

import mix.controller.DownTsThread;
import mix.controller.DownTsData;
import mix.model.Panel2Vo;
import mix.model.TsName;
import mix.utils.DownFile;
import mix.utils.ReadTsFromM3u8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * 解析M3u8并下载ts文件
 */
public class Panel2 extends JPanel implements ActionListener {
    private static final int THREAD_COUNT = 5; // 线程数量
    private final JTextField baseUrl, m3u8SavePath;
    private final JButton saveTsBtn;
    private final JProgressBar progressBar;
    private final Panel1 panel1;

    private final JLabel errCount, refreshCount;

    public Panel2(Panel1 panel1) {
        this.panel1 = panel1;
        // 设置布局
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gbc.fill = GridBagConstraints.NONE; // 如果组件所在的区域比组件大,不调整组件大小。

        // 加入组件
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(new JLabel("下载基路径"), gbc);
        // 对输入框进行设置
        baseUrl = new JTextField("http://127.0.0.1/sss/", 20);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 10;
        add(baseUrl, gbc);

        // 保存地址lab
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(new JLabel("m3u8文件"), gbc);
        // m3u8地址输入框
        m3u8SavePath = new JTextField("example\\sss\\index.m3u8", 20);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        add(m3u8SavePath, gbc);

        // 下载进度lab
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(new JLabel("下载进度"), gbc);
        // 下载进度条
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 10;
        add(progressBar, gbc);

        // 同步按钮
        JButton syncBtn = new JButton("同步配置");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        syncBtn.setActionCommand("SYNC");
        syncBtn.addActionListener(this);
        add(syncBtn, gbc);

        // 下载按钮
        saveTsBtn = new JButton("下载ts文件");
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        saveTsBtn.setActionCommand("SAVE_TS");
        saveTsBtn.addActionListener(this);
        add(saveTsBtn, gbc);

        // 错误计数
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        // 下载进度条
        errCount = new JLabel("错误计数: 0");
        add(errCount, gbc);

        // 错误计数
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 6;
        // 下载进度条
        refreshCount = new JLabel("刷新计数: 0");
        add(refreshCount, gbc);

        // 同步参数
        syncConfigFormPanel1();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("SAVE_TS".equals(e.getActionCommand())) {
            DownFile.readReqConfig();
            // 异步执行防止卡死
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    saveTsList();
                    return null;
                }
            }.execute();
        } else if ("SYNC".equals(e.getActionCommand()))
            syncConfigFormPanel1();
    }

    /**
     * 读取m3u8文件，并下载其中的ts列表
     */
    private void saveTsList() {
        saveTsBtn.setEnabled(false); // 禁止多次点击
        // 下载基url
        String baseUrlText = baseUrl.getText();
        if (!baseUrlText.endsWith("/")) baseUrlText += "/";
        // m3u8文件路径
        String m3u8SaveText = m3u8SavePath.getText();
        // ts保存路径(文件夹)
        String parentDir = new File(m3u8SaveText).getParent();

        // 使用多线程下载
        final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        List<TsName> tsNames = ReadTsFromM3u8.readTsNameList(m3u8SaveText, baseUrlText);
        int tsNameSize = tsNames.size();
        if (tsNameSize == 0) {
            JOptionPane.showMessageDialog(null, "m3u8解析失败", "错误", JOptionPane.ERROR_MESSAGE);
            saveTsBtn.setEnabled(true);
            return;
        }
        progressBar.setMaximum(tsNameSize);
        DownTsData downTsData = new DownTsData(tsNames, parentDir);
        // 创建结束钩子,将列表写入文件
        Runtime.getRuntime().addShutdownHook(new Thread(downTsData::writeDealItemToFile));
        // 正式开始下载
        for (int i = 0; i < THREAD_COUNT; i++) {
            new DownTsThread(downTsData, latch, parentDir).start();
        }
        // 定时输出进度
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int refreshCou = 0;

            @Override
            public void run() {
                progressBar.setValue(downTsData.getProgress());
                progressBar.setString(downTsData.getProgress() + "/" + tsNameSize);
                errCount.setText("错误计数: " + downTsData.getErrCount());
                refreshCount.setText("刷新计数: " + refreshCou++);
            }
        }, 0, 5000); // 每5秒执行一次
        try {
            latch.await();  // 等待所有的线程结束
            timer.cancel();
        } catch (InterruptedException exception) {
            JOptionPane.showMessageDialog(null, "线程结束失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
        // 最后总结
        if (downTsData.getErrCount() > 0) {
            JOptionPane.showMessageDialog(null, "有部分ts下载失败,请重下", "错误", JOptionPane.ERROR_MESSAGE);
            saveTsBtn.setText("重下失败Ts");
            saveTsBtn.setEnabled(true);
        } else
            JOptionPane.showMessageDialog(null, "ts下载完毕", "反馈", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 从第一面板同步数据过来
     */
    private void syncConfigFormPanel1() {
        Panel2Vo dataToPanel2 = panel1.getDataToPanel2();
        baseUrl.setText(dataToPanel2.baseUrl());
        m3u8SavePath.setText(dataToPanel2.m3u8SavePath());
    }
}