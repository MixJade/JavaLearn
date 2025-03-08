package mix;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Random;

/**
 * 强密码生成器
 *
 * @since 2023年7月7日 21:11
 */
public class PanGenPwd extends JPanel {
    public PanGenPwd() {
        // 流式布局
        setLayout(new FlowLayout());
        // 创建组件
        JLabel dateLabel = new JLabel("密码位数:");
        // 设置生成密码数
        JTextField pwdCountTextField = new JTextField("24");
        JButton button = new JButton("生成");
        JButton button2 = new JButton("复制");
        // 创建结果输出
        JTextArea result = new JTextArea(5, 24);
        result.setEditable(false);
        // 创建边框
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3);
        result.setBorder(border);
        // 将组件加入面板
        add(dateLabel);
        add(pwdCountTextField);
        add(button);
        add(button2);
        add(result);
        // 通过输入框生成密码
        button.addActionListener(arg0 -> {
            int pwdCount;
            try {
                pwdCount = Integer.parseInt(pwdCountTextField.getText());
            } catch (NumberFormatException e) {
                pwdCount = 24;
            }
            String pwd = generatePwd(pwdCount);
            result.setText(pwd);
        });
        // 复制密码
        button2.addActionListener(arg0 -> {
            StringSelection stringSelection = new StringSelection(result.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            JOptionPane.showMessageDialog(null, "复制成功", "反馈", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    @SuppressWarnings("SpellCheckingInspection")
    private String generatePwd(int pwdLength) {// 必要的字符集合
        String lowerCaseLetter = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseLetter = lowerCaseLetter.toUpperCase();
        String numericChar = "1234567890";
        String specialChar = "!@#$%^&*()";

        String finalString = lowerCaseLetter + upperCaseLetter + numericChar + specialChar;

        //创建Random对象
        Random random = new Random(System.currentTimeMillis());
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < pwdLength; i++) {
            //获取随机字符
            int position = random.nextInt(finalString.length());
            //将这些字符添加到密码字符串中
            password.append(finalString.charAt(position));
        }
        // 输出生成的随机密码
        return String.valueOf(password);
    }
}