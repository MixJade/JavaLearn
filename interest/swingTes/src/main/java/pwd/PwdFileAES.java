package pwd;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;

import static pwd.PwdAES.padIvString;
import static pwd.PwdAES.padKeyString;

/**
 * AES文件加密
 *
 * @since 2024-10-02 22:14:47
 */
public class PwdFileAES extends JFrame implements ActionListener {
    // 文本输入与密钥输入
    private final JTextField keyTextField, ivTextField;
    // 结果输出
    JTextArea showArea;
    private File selectedFile = null;

    public PwdFileAES() {
        // 使用东西南北布局
        setLayout(new BorderLayout());
        setTitle("AES加密");
        // 设置图标
        ImageIcon icon = new ImageIcon("src/main/resources/favor.jpg");
        setIconImage(icon.getImage());
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
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // 设置窗口属性
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null); //此语句将窗口定位在屏幕的中央
        setVisible(true);
    }

    /**
     * 使用AES对明文进行加密
     *
     * @param key        密钥
     * @param iv         初始化向量
     * @param originFile 明文
     * @return 加密后的密文
     * @since 2024/1/2 15:00
     */
    public static boolean encryptFile(SecretKey key, IvParameterSpec iv, File originFile) {
        // 创建一个写入指定文件数据的文件输出流
        try (FileOutputStream outputStream = new FileOutputStream(originFile.getPath() + ".enc")) {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            String fileContext = Files.readString(originFile.toPath());
            System.out.println(fileContext);
            byte[] encrypted = cipher.doFinal(fileContext.getBytes());
            // 把二进制数据写入文件
            outputStream.write(encrypted);

            System.out.println("Binary data has been written to file.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 对AES的密文进行解密
     *
     * @param key     密钥
     * @param iv      初始化向量
     * @param encFile 密文
     * @return 明文
     * @since 2024/1/2 15:00
     */
    public static String decryptFile(SecretKey key, IvParameterSpec iv, File encFile) {
        try (FileInputStream inputStream = new FileInputStream(encFile)) {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = cipher.doFinal(inputStream.readAllBytes());
            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
            return "解密失败";
        }
    }

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        new PwdFileAES();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("CHOOSE_FILE".equals(e.getActionCommand())) {
            // 打开选择文件对话框
            JFileChooser fileChooser = new JFileChooser("../../");
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
        SecretKey key = new SecretKeySpec(padKeyString(keyTextField.getText()), "AES");
        // 处理向量
        IvParameterSpec iv = new IvParameterSpec(padIvString(ivTextField.getText()));
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