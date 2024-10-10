package mix;

import mix.show.Panel1;
import mix.show.Panel2;
import mix.show.Panel3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class Main extends JFrame {
    Main() {
        // 创建一个新的 JFrame
        setTitle("m3u8下载器");
        // 设置图标
        try (InputStream favor = getClass().getResourceAsStream("th023.jpg")) {
            if (favor != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(favor));
                setIconImage(icon.getImage());
            }
        } catch (IOException e) {
            System.out.println("未读到图片");
        }
        // 创建一个新的 JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // 创建面板1
        Panel1 panel1 = new Panel1();
        tabbedPane.addTab("基本参数", panel1);

        // 创建面板2
        Panel2 panel2 = new Panel2(panel1);
        tabbedPane.addTab("下载ts", panel2);

        // 创建面板3
        Panel3 panel3 = new Panel3(panel1);
        tabbedPane.addTab("转换电影", panel3);

        // 将选项卡面板添加到帧中
        add(tabbedPane);

        // 设置窗口为最佳大小
        pack();
        // 启动窗口
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}