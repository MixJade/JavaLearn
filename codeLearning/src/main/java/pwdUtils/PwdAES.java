package pwdUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES加密
 *
 * @since 2024-1-2 19:47
 */
public class PwdAES extends JFrame implements ActionListener {
    // 文本输入与密钥输入
    private final JTextField inputTextField, keyTextField, ivTextField;
    // 结果输出
    JTextArea showArea;

    public PwdAES() {
        setLayout(new BorderLayout());
        setTitle("AES加密");

        // 上方面板
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("输入"));
        inputTextField = new JTextField(20);
        inputTextField.setText("Hello World");
        topPanel.add(inputTextField);
        // 两个按钮
        JButton inBtn = new JButton("加密"),
                outBtn = new JButton("解密");
        topPanel.add(inBtn);
        topPanel.add(outBtn);
        // 添加监听器
        inBtn.setActionCommand("encrypt");
        inBtn.addActionListener(this);
        outBtn.setActionCommand("decrypt");
        outBtn.addActionListener(this);

        // 居中面板, JTextArea默认是可编辑的，我们设置为不可编辑
        showArea = new JTextArea();
        showArea.setEditable(false);

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
        this.add(showArea, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // 设置窗口属性
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }

    /**
     * 使用AES对明文进行加密
     *
     * @param key   密钥
     * @param iv    初始化向量
     * @param value 明文
     * @return 加密后的密文
     * @since 2024/1/2 15:00
     */
    public static String encrypt(SecretKey key, IvParameterSpec iv, String value) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return "加密失败";
        }
    }

    /**
     * 对AES的密文进行解密
     *
     * @param key       密钥
     * @param iv        初始化向量
     * @param encrypted 密文
     * @return 明文
     * @since 2024/1/2 15:00
     */
    public static String decrypt(SecretKey key, IvParameterSpec iv, String encrypted) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
            return "解密失败";
        }
    }

    public static void main(String[] args) {
        new PwdAES();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 处理密钥
        SecretKey key = new SecretKeySpec(padKeyString(keyTextField.getText()), "AES");
        // System.out.println(new String(padKeyString(keyTextField.getText()),StandardCharsets.UTF_8));
        // 处理向量
        IvParameterSpec iv = new IvParameterSpec(padIvString(ivTextField.getText()));
        // System.out.println(new String(padIvString(ivTextField.getText()),StandardCharsets.UTF_8));
        // 处理输入文本
        String inputText = inputTextField.getText();
        // 进行加密、解密
        if ("encrypt".equals(e.getActionCommand())) {
            // 加密
            showArea.setText(encrypt(key, iv, inputText));
        } else if ("decrypt".equals(e.getActionCommand())) {
            // 解密
            showArea.setText(decrypt(key, iv, inputText));
        }
    }

    /**
     * 对输入的密钥进行处理，输出长度为16、24、32的数组
     *
     * @param input 输入的密钥文本
     * @return 长度为16、24、32的byte[]
     * @since 2024/1/2 15:00
     */
    public byte[] padKeyString(String input) {
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        if (inputBytes.length < 24) {
            // 输出的数组长度小于24，则输出16位的密钥
            return padIvString(input, "WhatCannotBeSeen");
        } else if (inputBytes.length > 24 && inputBytes.length < 32) {
            // 输出数组的长度大于24但小于32，则输出24位数组
            byte[] result = new byte[24];
            // 将inputBytes前24位给复制到result中
            System.arraycopy(inputBytes, 0, result, 0, 24);
            return result;
        } else if (inputBytes.length > 32) {
            // 输出数组的长度大于32，则输出32位数组
            byte[] result = new byte[32];
            System.arraycopy(inputBytes, 0, result, 0, 32);
            return result;
        } else {
            // 刚好就是24、32的长度
            return inputBytes;
        }
    }

    /**
     * 重置方法：对输入的向量进行处理
     *
     * @param input 输入的向量文本
     * @return 16位的byte数组
     */
    public byte[] padIvString(String input) {
        return padIvString(input, "TimeWaitForNoMan");
    }

    /**
     * 对输入的向量进行处理，保证输出16位的byte数组
     *
     * @param input  输入的向量文本
     * @param defTxt 默认填充文本
     * @return 16位的byte数组
     * @since 2024/1/2 15:00
     */
    public byte[] padIvString(String input, String defTxt) {
        byte[] padBytes = defTxt.getBytes(StandardCharsets.UTF_8);
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        if (inputBytes.length < 16) {
            // 输出的数组长度小于16
            byte[] result = new byte[16];
            // 将inputBytes全部复制到result中
            System.arraycopy(inputBytes, 0, result, 0, inputBytes.length);
            // 将填空字符串拼接到输出字符串中
            System.arraycopy(padBytes, inputBytes.length, result, inputBytes.length, 16 - (inputBytes.length));
            return result;
        } else if (inputBytes.length > 16) {
            // 输出数组的长度大于16
            byte[] result = new byte[16];
            // 将inputBytes前16位给复制到result中
            System.arraycopy(inputBytes, 0, result, 0, 16);
            return result;
        } else {
            return inputBytes;
        }
    }
}