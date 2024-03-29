package pwdUtils;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 强密码生成器
 *
 * @since 2023年7月7日 21:11
 */
public class PwdCreatByDate extends JFrame {
    public PwdCreatByDate() throws ParseException {
        // 流式布局
        setLayout(new FlowLayout());
        // 设置输入框的格式化器
        MaskFormatter maskFormatter = new MaskFormatter("####-##-##");
        maskFormatter.setPlaceholderCharacter('_');
        // 创建组件
        JLabel dateLabel = new JLabel("输入日期:");
        // 初始化一个格式输入框
        JFormattedTextField dateTextField = new JFormattedTextField(maskFormatter);
        JButton button = new JButton("生成");
        JTextArea result = new JTextArea(5, 24);
        result.setEditable(false);
        // 将组件加入面板
        Container c = getContentPane();
        c.add(dateLabel);
        c.add(dateTextField);
        c.add(button);
        c.add(result);
        // 通过输入框生成密码
        button.addActionListener(arg0 -> {
            String date = dateTextField.getText();
            long aLong = tranDateToLong(date);
            String pwd = generatePwd(aLong);
            result.setText(pwd);
        });
        setSize(new Dimension(200, 300));
        setTitle("通过日期生成密码");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setVisible(true);
        setResizable(false);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        // 让Frame在屏幕中心出现
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    }

    public static void main(String[] args) {
        try {
            new PwdCreatByDate();
        } catch (ParseException e) {
            System.out.println("初始化失败");
        }
    }

    private long tranDateToLong(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);
            // 开始转化
            long timestamp = date.getTime();
            System.out.println("时间戳：" + timestamp);
            return timestamp;
        } catch (ParseException e) {
            System.out.println("时间转化失败");
            return System.currentTimeMillis();
        }
    }

    private String generatePwd(long timestamp) {// 必要的字符集合
        String lowerCaseLetter = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseLetter = lowerCaseLetter.toUpperCase();
        String numericChar = "1234567890";
        String specialChar = "!@#$%^&*()";

        String finalString = lowerCaseLetter + upperCaseLetter + numericChar + specialChar;

        //创建Random对象
        Random random = new Random(timestamp);

        //设置密码长度
        int passwordLength = 8;

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            //获取随机字符
            int position = random.nextInt(finalString.length());
            //将这些字符添加到密码字符串中
            password.append(finalString.charAt(position));
        }
        // 输出生成的随机密码
        System.out.println("生成的密码为：" + password);
        return String.valueOf(password);
    }
}