package mix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64解码
 *
 * @since 2025-03-23 14:25:29
 */
public class PanBase64 extends JPanel implements ActionListener {
    // 加密与解密按钮
    JButton closeBtn, openBtn;
    // 文本输入与密钥输入
    JTextField plainText;
    // 结果输出
    JTextArea cipherText;

    public PanBase64() {
        setLayout(new FlowLayout());
        setBounds(100, 100, 500, 200);
        // 设置组件
        closeBtn = new JButton("编码");
        openBtn = new JButton("解码");
        plainText = new JTextField(15);
        plainText.setText("蜈蚣的证词");
        cipherText = new JTextArea();
        // 添加监听器
        closeBtn.addActionListener(this);
        openBtn.addActionListener(this);
        // 设置容器
        JPanel myInput = new JPanel();
        myInput.add(new JLabel("输入:"));
        myInput.add(plainText);
        myInput.add(closeBtn);
        myInput.add(openBtn);

        // 设置布局
        setLayout(new BorderLayout(2, 2));
        add(myInput, BorderLayout.NORTH);
        add(cipherText, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String plain = plainText.getText();
        if (e.getSource() == closeBtn) {
            cipherText.setText(encodeBase64(plain));
        } else {
            cipherText.setText(decodeBase64(plain));
        }
    }

    /**
     * Base64编码
     */
    private String encodeBase64(String input) {
        try {
            byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return "编码失败";
        }
    }

    /**
     * Base64解码
     */
    private String decodeBase64(String input) {
        try {
            byte[] decode = Base64.getDecoder().decode(input);
            return new String(decode, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "解码失败";
        }
    }
}