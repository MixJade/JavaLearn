package mix;

import mix.view.Panel1;
import mix.view.Panel2;
import mix.view.Panel3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * m3u8多线程下载器
 *
 * @since 2024-10-11 14:33:42
 */
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
        } catch (IOException ignored) {
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
        SwingUtilities.invokeLater(Main::new);
    }
}