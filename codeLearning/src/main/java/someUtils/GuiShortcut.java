package someUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 自定义快捷方式GUI
 *
 * @since 2024-7-4 10:26:57
 */
public class GuiShortcut {
    public GuiShortcut() {
        // 获取配置的值
        MyDir[] myDirs = MyDir.values();
        // 开始创建界面
        JFrame frame = new JFrame("S");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 140 + myDirs.length * 20);
        frame.setLocationRelativeTo(null); //此语句将窗口定位在屏幕的中央
        frame.setLayout(new GridLayout(myDirs.length + 1, 1));
        frame.setResizable(false); // 禁用最大化窗口
        JLabel label = new JLabel("自定义快捷方式", SwingConstants.CENTER);
        frame.add(label);
        // 为每个按钮添加事件
        for (MyDir myDir : myDirs) {
            JButton button1 = new JButton(myDir.btnNm());
            button1.setBackground(myDir.color());
            button1.addActionListener(e -> openDir(myDir.dirPath()));
            frame.add(button1);
        }

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuiShortcut::new);
    }

    private void openDir(String dirPath) {
        if (Desktop.isDesktopSupported()) {
            try {
                // 打开上两级文件夹
                File myFile = new File("" + dirPath);
                Desktop.getDesktop().open(myFile);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "目标文件夹不存在", "警告", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}

enum MyDir {
    PY_CMD("Python脚本", "../../PythonLearn/Normal/utils/pyCmd", Color.GREEN),
    PT_DOC("Python笔记", "../../PythonLearn/docs", Color.GREEN),
    TS_DOC("前端笔记", "../../TsLearn/docs", Color.CYAN),
    JAVA_DOC("Java笔记", "../../JavaLearn/docs/2023", Color.MAGENTA),
    PIC_PUB("图片文件", "../../MyPicture/public", Color.LIGHT_GRAY),
    UNUSED("无用文件", "unusedFile", Color.GRAY),
    MIX_ARCH("备份存档", "../../mixArchive", Color.GRAY),
    ;
    private final String btnNm, dirPath;
    private final Color color;

    MyDir(String btnNm, String dirPath, Color color) {
        this.btnNm = btnNm;
        this.dirPath = dirPath;
        this.color = color;
    }

    public String btnNm() {
        return btnNm;
    }

    public String dirPath() {
        return dirPath;
    }

    public Color color() {
        return color;
    }
}