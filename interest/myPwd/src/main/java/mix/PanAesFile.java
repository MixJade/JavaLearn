package mix;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static mix.PwdAES.decryptFile;
import static mix.PwdAES.encryptFile;

/**
 * AES文件加密
 *
 * @since 2024-10-02 22:14:47
 */
public class PanAesFile extends JPanel implements ActionListener {
    // 文本输入与密钥输入
    private final JPasswordField keyTextField, ivTextField;
    // 结果输出
    JTextArea showArea;
    private File selectedFile = null;

    public PanAesFile() {
        // 使用东西南北布局
        setLayout(new BorderLayout());
        // 上方面板
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("AES文件加密"));
        // 右边区域
        JPanel rightPanel = new JPanel();
        // 设置布局为 BoxLayout，元素竖直排列
        BoxLayout rightLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
        rightPanel.setLayout(rightLayout);
        // 两个按钮
        JButton openDialogBtn = new JButton("选择文件"),
                inBtn = new JButton("AES加密"),
                outBtn = new JButton("AES解密");
        rightPanel.add(openDialogBtn);
        rightPanel.add(inBtn);
        rightPanel.add(outBtn);
        // 添加监听器
        openDialogBtn.setActionCommand("CHOOSE_FILE");
        openDialogBtn.addActionListener(this);
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
        //添加滚动条
        JScrollPane scrollPane = new JScrollPane(showArea);
        // 创建边框
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3);
        scrollPane.setBorder(border);

        // 下方面板
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1)); // 设置为网格布局(2行1列)
        // 第一行
        JPanel row1 = new JPanel();
        row1.add(new JLabel("密钥"));
        keyTextField = new JPasswordField(16);
        keyTextField.setText("WhatCannotBeSeen");
        row1.add(keyTextField);
        bottomPanel.add(row1);
        // 第二行
        JPanel row2 = new JPanel();
        row2.add(new JLabel("向量"));
        ivTextField = new JPasswordField(16);
        ivTextField.setText("TimeWaitForNoMan");
        row2.add(ivTextField);
        bottomPanel.add(row2);

        // 添加面板到JFrame
        this.add(topPanel, BorderLayout.NORTH);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("CHOOSE_FILE".equals(e.getActionCommand())) {
            // 打开选择文件对话框
            JFileChooser fileChooser = new JFileChooser("../");
            fileChooser.setDialogTitle("请选择待加解密的文件");
            // 打开一个文件对话框
            int returnValue = fileChooser.showOpenDialog(null);
            // 当用户选中一个文件并点击"Open"按钮时，returnValue的值为 `JFileChooser.APPROVE_OPTION`
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // 用户选中的文件
                selectedFile = fileChooser.getSelectedFile();
                showArea.setText("选中的文件名：" + selectedFile.getName() + "\n" + selectedFile.getPath());
            }
            return;
        }
        // 处理密钥
        String key = String.valueOf(keyTextField.getPassword());
        // 处理向量
        String iv = String.valueOf(ivTextField.getPassword());
        // 进行加密、解密
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(null, "请先选择文件", "散玉说", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ("AES_ENC".equals(e.getActionCommand())) {
            // 加密
            boolean encryptSuc = encryptFile(key, iv, selectedFile);
            if (encryptSuc)
                showArea.setText("文件加密成功:" + selectedFile.getPath() + ".enc");
            else
                showArea.setText("文件加密失败");
        } else if ("AES_DEC".equals(e.getActionCommand())) {
            // 解密
            showArea.setText(decryptFile(key, iv, selectedFile));
        } else {
            // 重置
            showArea.setText("");
            selectedFile = null;
            keyTextField.setText("");
            ivTextField.setText("");
        }
    }
}