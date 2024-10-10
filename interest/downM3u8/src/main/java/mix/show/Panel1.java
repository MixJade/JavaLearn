package mix.show;

import mix.entiy.Panel2Vo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

/**
 * 下载M3u8以及设置一些基础参数
 */
public class Panel1 extends JPanel implements ActionListener {
    private final JTextField m3u8Url, savePath, m3u8Name;
    private final JButton saveBtn;

    public Panel1() {
        // 设置布局
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gbc.fill = GridBagConstraints.NONE; // 如果组件所在的区域比组件大,不调整组件大小。

        // 加入组件
        // 组件1(grid-x,grid-y)组件的左上角坐标
        // grid-width，grid-height：组件占用的网格行数和列数
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(new JLabel("m3u8地址"), gbc);
        // 对输入框进行设置
        m3u8Url = new JTextField("http://127.0.0.1/sss/index.m3u8", 20);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 10;
        add(m3u8Url, gbc);

        // 保存地址lab
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(new JLabel("保存文件夹"), gbc);
        // 保存地址输入框
        savePath = new JTextField("example/sss", 20);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        add(savePath, gbc);

        // m3u8文件名
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(new JLabel("m3u8文件名"), gbc);
        // m3u8名称输入框
        m3u8Name = new JTextField("index.m3u8", 20);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 10;
        add(m3u8Name, gbc);

        // 下载按钮
        saveBtn = new JButton("下载m3u8");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        saveBtn.setActionCommand("SAVE_BTN");
        saveBtn.addActionListener(this);
        add(saveBtn, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("SAVE_BTN".equals(e.getActionCommand())) {
            saveBtn.setEnabled(false); // 禁止多次点击
            System.out.println("下载地址:" + m3u8Url.getText());
            System.out.println("转存文件名:" + Paths.get(savePath.getText(), m3u8Name.getText()));
        }
    }

    /**
     * 为panel2获取数据预留接口
     *
     * @return panel2的数据
     */
    Panel2Vo getDataToPanel2() {
        String baseUrl = m3u8Url.getText();
        int lastIndex = baseUrl.lastIndexOf('/');
        if (lastIndex != -1) {
            baseUrl = baseUrl.substring(0, lastIndex + 1);
        }
        String m3u8SavePath = Paths.get(savePath.getText(), m3u8Name.getText()).toString();
        return new Panel2Vo(baseUrl, m3u8SavePath);
    }
}
