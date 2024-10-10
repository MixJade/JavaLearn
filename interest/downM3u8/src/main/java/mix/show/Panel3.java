package mix.show;

import mix.entiy.Panel3Vo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 将ts文件打包
 */
public class Panel3 extends JPanel implements ActionListener {
    private final JTextField saveDir, m3u8Name, movieName;
    private final JButton tranBtn;

    private final Panel1 panel1;

    public Panel3(Panel1 panel1) {
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
        add(new JLabel("转换文件夹"), gbc);
        // 对输入框进行设置
        saveDir = new JTextField("example/sss", 20);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 10;
        add(saveDir, gbc);

        // 保存地址lab
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(new JLabel("m3u8文件名"), gbc);
        // m3u8地址输入框
        m3u8Name = new JTextField("index.m3u8", 20);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        add(m3u8Name, gbc);

        // 电影名称
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(new JLabel("电影名称"), gbc);
        // 电影名称输入框
        movieName = new JTextField("下载电影", 20);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 10;
        add(movieName, gbc);

        // 同步按钮
        JButton syncBtn = new JButton("同步配置");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        syncBtn.setActionCommand("SYNC");
        syncBtn.addActionListener(this);
        add(syncBtn, gbc);

        // 下载按钮
        tranBtn = new JButton("开始转换");
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        tranBtn.setActionCommand("TRAN");
        tranBtn.addActionListener(this);
        add(tranBtn, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("TRAN".equals(e.getActionCommand())) {
            tranBtn.setEnabled(false); // 禁止多次点击

            System.out.println("文件夹名称" + saveDir.getText());

            System.out.println("m3u8名称:" + m3u8Name.getText());

            System.out.println("电影名称" + movieName.getText());
        } else if ("SYNC".equals(e.getActionCommand())) {
            Panel3Vo dataToPanel3 = panel1.getDataToPanel3();
            saveDir.setText(dataToPanel3.saveDir());
            m3u8Name.setText(dataToPanel3.m3u8Name());
        }
    }
}
