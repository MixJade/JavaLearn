package mix.show;

import mix.entiy.Panel2Vo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 解析M3u8并下载ts文件
 */
public class Panel2 extends JPanel implements ActionListener {
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
        m3u8SavePath = new JTextField("example/sss/index.m3u8", 20);
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
            saveTsBtn.setEnabled(false); // 禁止多次点击
            String baseUrlText = baseUrl.getText();
            if (!baseUrlText.endsWith("/")) baseUrlText += "/";
            System.out.println("下载地址:" + baseUrlText + "sss.ts");

            String m3u8SaveText = m3u8SavePath.getText();
            System.out.println("m3u8路径:" + m3u8SaveText);

            File file = new File(m3u8SaveText);
            String parentDir = file.getParent();
            System.out.println("ts保存路径" + parentDir);

            progressBar.setValue(2);
            progressBar.setString("2/100");
        } else if ("SYNC".equals(e.getActionCommand())) {
            Panel2Vo dataToPanel2 = panel1.getDataToPanel2();
            baseUrl.setText(dataToPanel2.baseUrl());
            m3u8SavePath.setText(dataToPanel2.m3u8SavePath());
        }
    }
}
