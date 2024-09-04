package demo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;

/**
 * JTab的样例
 *
 * @since 2024-09-04 17:28:08
 */
public class JTabDemo {
  public static void main(String[] args) {
    // 创建一个新的 JFrame
    JFrame frame = new JFrame("TabbedPane Demo");

    // 创建一个新的 JTabbedPane
    JTabbedPane tabbedPane = new JTabbedPane();

    // 创建两个面板
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();

    // 在每个面板上添加一些内容
    panel1.add(new JLabel("这是面板1"));
    panel2.add(new JLabel("这 is 面板2"));

    // 将这两个面板添加到选项卡面板中
    tabbedPane.addTab("Tab 1", panel1);
    tabbedPane.addTab("Tab 2", panel2);

    // 将选项卡面板添加到帧中
    frame.add(tabbedPane);

    // 设置窗口大小
    frame.setSize(400, 300);

    // 启动窗口
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}