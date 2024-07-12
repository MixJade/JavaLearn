package pwdUtils;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 凯撒加密
 */
public class PwdCaesar extends JFrame implements ActionListener {
    JButton addBtn, reduceBtn;
    JTextField plainText, keyText;
    JTextArea cipherText;
    PwdCaesar() {
        init();
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new PwdCaesar();
    }

    private void init() {
        setLayout(new FlowLayout());
        setBounds(100, 100, 500, 200);
        setTitle("普通的加密");
        // 设置组件
        addBtn = new JButton("加密");
        reduceBtn = new JButton("解密");
        plainText = new JTextField(15);
        cipherText = new JTextArea();
        keyText = new JTextField(10);
        keyText.setText("1");
        // 添加监听器
        addBtn.addActionListener(this);
        reduceBtn.addActionListener(this);
        // 设置容器
        JPanel myInput = new JPanel();
        myInput.add(new JLabel("输入:"));
        myInput.add(plainText);
        myInput.add(addBtn);
        myInput.add(reduceBtn);
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
        int key;
        try {
            key = Integer.parseInt(keyText.getText().trim());
        } catch (NumberFormatException nfe) {
            key = 1;
        }
        if (e.getSource() == addBtn) {
            cipherText.setText(addOne(plain, key));
        } else {
            cipherText.setText(reduce(plain, key));
        }
    }

    public String addOne(String plain, int key) {
        char[] chars = plain.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ('a' <= chars[i] && chars[i] <= 'z') {
                chars[i] += key;
                while (chars[i] > 'z') {
                    chars[i] = (char) (chars[i] - 'z' - 1 + 'a');
                }
            } else if ('A' <= chars[i] && chars[i] <= 'Z') {
                chars[i] += key;
                while (chars[i] > 'Z') {
                    chars[i] = (char) (chars[i] - 'Z' - 1 + 'A');
                }
            } else if ('0' <= chars[i] && chars[i] <= '9') {
                chars[i] += key;
                while (chars[i] > '9') {
                    chars[i] = (char) (chars[i] - '9' - 1 + '0');
                }
            }
        }
        return String.valueOf(chars);
    }

    public String reduce(String plain, int key) {
        char[] chars = plain.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ('a' <= chars[i] && chars[i] <= 'z') {
                chars[i] -= key;
                while (chars[i] < 'a') {
                    chars[i] = (char) (chars[i] + 'z' + 1 - 'a');
                }
            } else if ('A' <= chars[i] && chars[i] <= 'Z') {
                chars[i] -= key;
                while (chars[i] < 'A') {
                    chars[i] = (char) (chars[i] + 'Z' + 1 - 'A');
                }
            } else if ('0' <= chars[i] && chars[i] <= '9') {
                chars[i] -= key;
                while (chars[i] < '0') {
                    chars[i] = (char) (chars[i] + '9' + 1 - '0');
                }
            }
        }
        return String.valueOf(chars);
    }
}
