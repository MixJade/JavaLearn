package mix;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static mix.PwdAES.decrypt;
import static mix.PwdAES.encrypt;

/**
 * AES加密
 *
 * @since 2024-1-2 19:47
 */
public class PanAES extends JPanel implements ActionListener {
    // 文本输入与密钥输入
    private final JTextField inputTextField, keyTextField, ivTextField;
    // 结果输出
    JTextArea showArea;

    public PanAES() {
        // 使用东西南北布局
        setLayout(new BorderLayout());
        // 上方面板
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("输入"));
        inputTextField = new JTextField(24);
        inputTextField.setText("Hello World");
        topPanel.add(inputTextField);
        // 右边区域
        JPanel rightPanel = new JPanel();
        // 设置布局为 BoxLayout，元素竖直排列
        BoxLayout rightLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
        rightPanel.setLayout(rightLayout);
        // 两个按钮
        JButton inBtn = new JButton("AES加密"),
                outBtn = new JButton("AES解密");
        rightPanel.add(inBtn);
        rightPanel.add(outBtn);
        // 添加监听器
        inBtn.setActionCommand("AES_ENC");
        inBtn.addActionListener(this);
        outBtn.setActionCommand("AES_DEC");
        outBtn.addActionListener(this);

        // 左边区域
        JPanel leftPanel = new JPanel();
        // 一样是竖直布局(布局不能复用,只能再New)
        BoxLayout leftLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
        leftPanel.setLayout(leftLayout);
        // 添加按钮
        JButton resetBtn = new JButton("重置");
        resetBtn.setActionCommand("RESET_FILE");
        resetBtn.addActionListener(this);
        leftPanel.add(resetBtn);

        // 居中面板, JTextArea默认是可编辑的，我们设置为不可编辑
        showArea = new JTextArea();
        showArea.setEditable(false);
        // 创建边框
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3);
        showArea.setBorder(border);

        // 下方面板
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1)); // 设置为网格布局(2行1列)
        // 第一行
        JPanel row1 = new JPanel();
        row1.add(new JLabel("密钥"));
        keyTextField = new JTextField(20);
        keyTextField.setText("WhatCannotBeSeen");
        row1.add(keyTextField);
        bottomPanel.add(row1);
        // 第二行
        JPanel row2 = new JPanel();
        row2.add(new JLabel("初始向量"));
        ivTextField = new JTextField(20);
        ivTextField.setText("TimeWaitForNoMan");
        row2.add(ivTextField);
        bottomPanel.add(row2);

        // 添加面板到JFrame
        this.add(topPanel, BorderLayout.NORTH);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(showArea, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 处理密钥
        String key = keyTextField.getText();
        // 处理向量
        String iv = ivTextField.getText();
        // 处理输入文本
        String inputText = inputTextField.getText();
        // 进行加密、解密
        if ("AES_ENC".equals(e.getActionCommand())) {
            // 加密
            showArea.setText(encrypt(key, iv, inputText));
        } else if ("AES_DEC".equals(e.getActionCommand())) {
            // 解密
            showArea.setText(decrypt(key, iv, inputText));
        } else {
            // 重置
            showArea.setText("");
            keyTextField.setText("");
            ivTextField.setText("");
        }
    }
}