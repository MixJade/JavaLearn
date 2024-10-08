package mix;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * RC4加密工具
 *
 * @since 2023年7月7日 21:11
 */
public class PanRC4 extends JPanel implements ActionListener {
    // 加密与解密按钮
    JButton closeBtn, openBtn;
    // 文本输入与密钥输入
    JTextField plainText;
    JPasswordField keyText;
    // 结果输出
    JTextArea cipherText;

    public PanRC4() {
        setLayout(new FlowLayout());
        setBounds(100, 100, 500, 200);
        // 设置组件
        closeBtn = new JButton("加密");
        openBtn = new JButton("解密");
        plainText = new JTextField(15);
        plainText.setText("蜈蚣的证词");
        cipherText = new JTextArea();
        keyText = new JPasswordField(10);
        keyText.setText("TheKey");
        // 添加监听器
        closeBtn.addActionListener(this);
        openBtn.addActionListener(this);
        // 设置容器
        JPanel myInput = new JPanel();
        myInput.add(new JLabel("输入:"));
        myInput.add(plainText);
        myInput.add(closeBtn);
        myInput.add(openBtn);
        JPanel myKey = new JPanel();
        myKey.add(new JLabel("密钥"));
        myKey.add(keyText);
        // 设置布局
        setLayout(new BorderLayout(2, 2));
        add(myInput, BorderLayout.NORTH);
        add(cipherText, BorderLayout.CENTER);
        add(myKey, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String plain = plainText.getText();
        String key = String.valueOf(keyText.getPassword()).trim();
        if (e.getSource() == closeBtn) {
            cipherText.setText(encryptRC4(plain, key));
        } else {
            cipherText.setText(decryptRC4(plain, key));
        }
    }

    /**
     * 加密
     */
    private String encryptRC4(String input, String key) {
        try {
            Cipher cipher = Cipher.getInstance("RC4");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "RC4");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return "加密失败";
        }
    }

    /**
     * 解密
     */
    private String decryptRC4(String input, String key) {
        try {
            Cipher cipher = Cipher.getInstance("RC4");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "RC4");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decode = Base64.getDecoder().decode(input);
            byte[] bytes = cipher.doFinal(decode);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "解密失败";
        }
    }
}