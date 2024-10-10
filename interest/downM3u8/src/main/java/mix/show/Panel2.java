package mix.show;

import mix.entiy.Panel2Vo;
import mix.entiy.TsName;
import mix.utils.DownFile;
import mix.utils.ReadTsFromM3u8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("SAVE_TS".equals(e.getActionCommand())) {
            // 异步执行防止卡死
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    saveTsList();
                    return null;
                }
            }.execute();
        } else if ("SYNC".equals(e.getActionCommand())) {
            Panel2Vo dataToPanel2 = panel1.getDataToPanel2();
            baseUrl.setText(dataToPanel2.baseUrl());
            m3u8SavePath.setText(dataToPanel2.m3u8SavePath());
        }
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
        MyOKO myOKO = new MyOKO(tsNames, parentDir);
        // 创建结束钩子,将列表写入文件
        Runtime.getRuntime().addShutdownHook(new Thread(myOKO::writeDealItemToFile));
        // 正式开始下载
        for (int i = 0; i < THREAD_COUNT; i++) {
            new DownTsThread(myOKO, latch, parentDir).start();
        }
        // 定时输出进度
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                progressBar.setValue(myOKO.getProgress());
                progressBar.setString(myOKO.getProgress() + "/" + tsNameSize);
            }
        }, 0, 5000); // 每5秒执行一次
        try {
            latch.await();  // 等待所有的线程结束
            timer.cancel();
        } catch (InterruptedException exception) {
            JOptionPane.showMessageDialog(null, "线程结束失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
        // 最后总结
        if (DownFile.isAlreadyErr)
            JOptionPane.showMessageDialog(null, "有部分ts下载失败,请查看错误日志", "错误", JOptionPane.ERROR_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "ts下载完毕", "反馈", JOptionPane.INFORMATION_MESSAGE);
    }
}

/**
 * 公共变量类
 */
class MyOKO {
    private final String FILE_NAME; // 保存的文件名
    private final CopyOnWriteArrayList<TsName> TS_LIST = new CopyOnWriteArrayList<>(),
            alreadyTlList = new CopyOnWriteArrayList<>();

    public MyOKO(List<TsName> tsList, String dirPath) {
        FILE_NAME = dirPath + "\\dealItemList.txt";
        readDingItemFromFile(tsList);
        TS_LIST.addAll(tsList);
    }

    /**
     * 获取下一项待处理，并从待选列表中移除
     *
     * @return 下一项待处理的
     */
    TsName getNextItem() {
        if (TS_LIST.isEmpty()) {
            return null;
        }
        return TS_LIST.remove(0);
    }

    int getProgress() {
        return alreadyTlList.size();
    }

    /**
     * 将已完成的项目加入已完成列表
     *
     * @param item 已完成项目
     */
    void setProgress(TsName item) {
        alreadyTlList.add(item);
    }

    /**
     * 从文件中读取已完成的项目，并与传来的列表进行筛选
     *
     * @param tsList 传来的列表
     */
    private void readDingItemFromFile(List<TsName> tsList) {
        try {
            Path filePath = Paths.get(FILE_NAME);
            if (Files.exists(filePath)) {
                // 文件存在
                List<String> lines = Files.readAllLines(filePath);
                List<TsName> haveTsName = new ArrayList<>();
                for (String line : lines) {
                    haveTsName.add(new TsName("", line));
                }
                // 循环并筛选掉已处理的
                tsList.removeAll(haveTsName);
                // 将已处理的加入已处理列表
                alreadyTlList.addAll(haveTsName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 在程序结束时,将所有已完成的项目写入文件中
     */
    public void writeDealItemToFile() {
        File file = new File(FILE_NAME);
        try {
            // 创建新文件
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            }
            // 创建FileWriter对象
            FileWriter writer = new FileWriter(file);
            // 向文件写入内容
            List<String> alreadyTlTextList = alreadyTlList.stream()
                    .map(TsName::fileName).toList();
            writer.write(String.join("\n", alreadyTlTextList));
            writer.close();
        } catch (IOException ignored) {
        }
    }
}

/**
 * 用于读取元素并计数的线程
 */
class DownTsThread extends Thread {
    private final MyOKO myOKO;
    private final CountDownLatch latch;
    private final String DIR_PATH;

    public DownTsThread(MyOKO myOKO, CountDownLatch latch, String downDir) {
        this.myOKO = myOKO;
        this.latch = latch;
        this.DIR_PATH = downDir;
    }

    @Override
    public void run() {
        TsName item;
        while ((item = myOKO.getNextItem()) != null) {
            boolean downFromWeb = DownFile.downFromWeb(item.url(), DIR_PATH + "\\" + item.fileName());
            if (downFromWeb) myOKO.setProgress(item);
            else DownFile.writeErrorText(item.fileName());
        }
        latch.countDown();
    }
}