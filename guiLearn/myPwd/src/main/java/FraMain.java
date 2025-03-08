import com.formdev.flatlaf.FlatIntelliJLaf;
import mix.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class FraMain extends JFrame {
    FraMain() {
        setTitle("加密工具");
        // 设置图标
        try (InputStream favor = getClass().getResourceAsStream("favor.jpg")) {
            if (favor != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(favor));
                setIconImage(icon.getImage());
            }
        } catch (IOException e) {
            System.out.println("未读到图片");
        }
        // 创建一个新的 JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        // 创建面板
        JPanel panAES = new PanAES();
        JPanel panFileAES = new PanAesFile();
        JPanel panRC4 = new PanRC4();
        JPanel panCaesar = new PanCaesar();
        JPanel panGenPwd = new PanGenPwd();

        // 将面板添加到选项卡面板中
        tabbedPane.addTab("AES", panAES);
        tabbedPane.addTab("AES文件", panFileAES);
        tabbedPane.addTab("PC4", panRC4);
        tabbedPane.addTab("凯撒", panCaesar);
        tabbedPane.addTab("生成密码", panGenPwd);

        // 将选项卡面板添加到帧中
        add(tabbedPane);

        // 设置窗口大小
        setSize(450, 500);
        setLocationRelativeTo(null); //此语句将窗口定位在屏幕的中央
        // 启动窗口
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        new FraMain();
    }
}