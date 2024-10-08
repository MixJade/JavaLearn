package someUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 自定义快捷方式GUI
 *
 * @since 2024-7-4 10:26:57
 */
public class GuiShortcut implements ActionListener {
    GuiShortcut() {
        // 按钮名称
        String[] btnNames = new String[]{
                "Python脚本", "Python笔记", "前端笔记", "Java笔记", "我的密码"
                , "图片文件", "备份存档", "无用快捷"
        };
        // 按钮颜色
        Color[] btnColors = new Color[]{
                Color.GREEN, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.ORANGE
                , Color.LIGHT_GRAY, Color.WHITE, Color.GRAY};
        // 开始创建界面
        JFrame frame = new JFrame("S");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 140 + btnNames.length * 20);
        frame.setLocationRelativeTo(null); //此语句将窗口定位在屏幕的中央
        frame.setLayout(new GridLayout(btnNames.length + 1, 1));
        frame.setResizable(false); // 禁用最大化窗口
        JLabel label = new JLabel("自定义快捷方式", SwingConstants.CENTER);
        frame.add(label);
        // 为每个按钮添加事件
        for (int i = 0; i < btnNames.length; i++) {
            JButton button1 = new JButton(btnNames[i]);
            button1.setBackground(btnColors[i]);
            button1.addActionListener(this);
            frame.add(button1);
        }
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Python脚本" -> openDir("../../PythonLearn/Normal/utils/pyCmd");
            case "Python笔记" -> openDir("../../PythonLearn/docs");
            case "前端笔记" -> openDir("../../TsLearn/docs");
            case "Java笔记" -> openDir("../../JavaLearn/docs/2023");
            case "我的密码" -> execCmd();
            case "图片文件" -> openDir("../../MyPicture/public");
            case "备份存档" -> openDir("../../mixArchive");
            case "无用快捷" -> openDir("unusedFile");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuiShortcut::new);
    }

    private void openDir(String dirPath) {
        try {
            // 打开上两级文件夹
            File myFile = new File("" + dirPath);
            Desktop.getDesktop().open(myFile);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "目标文件夹不存在", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void execCmd() {
        try {
            File myPwdJar = new File("myPwd-1.0-SNAPSHOT.jar");
            if (myPwdJar.exists()) {
                // 创建一个Runtime实例并执行
                Runtime.getRuntime().exec("java -jar myPwd-1.0-SNAPSHOT.jar");
            } else {
                JOptionPane.showMessageDialog(null, "对应Jar包不存在", "疑问", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "命令执行失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }
}