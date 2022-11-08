package someUtils;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Displace extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new Displace();
    }

    JButton addBtn, reduceBtn;
    JTextField plainText, keyText;
    JTextArea cipherText;

    Displace() {
        init();
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            chars[i] += key;
        }
        return String.valueOf(chars);
    }

    public String reduce(String plain, int key) {
        char[] chars = plain.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] -= key;
        }
        return String.valueOf(chars);
    }
}
