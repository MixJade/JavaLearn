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
                "Python脚本", "Python笔记", "前端笔记", "Java笔记", "我的工具"
                , "图片文件", "备份存档", "无用快捷"
        };
        // 对应的文件夹路径
        String[] btnDir = new String[]{
                "../../PythonLearn/Normal/utils/pyCmd",
                "../../PythonLearn/docs",
                "../../TsLearn/docs",
                "../../JavaLearn/docs/2023",
                "../../selfTool",
                "../../MyPicture/public",
                "../../mixArchive",
                "unusedFile",
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
            button1.setActionCommand(btnDir[i]);
            frame.add(button1);
        }
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // 打开上两级文件夹
            File myFile = new File(e.getActionCommand());
            Desktop.getDesktop().open(myFile);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "目标文件夹不存在", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuiShortcut::new);
    }
}