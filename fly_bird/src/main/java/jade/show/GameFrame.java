package jade.show;

import jade.consts.GameConst;

import javax.swing.*;

/**
 * 窗体
 */
public class GameFrame extends JFrame {

    // 初始化窗体
    public void initFrame() {
        // 设置窗口标题
        setTitle(GameConst.TITLE);
        // 设置窗口大小
        setSize(GameConst.WIDTH, GameConst.HEIGHT);
        // 添加Panel
        GamePanel panel = new GamePanel();
        add(panel);
        // 设置窗口坐标
        setLocationRelativeTo(null);
        // 设置窗口图标
        ImageIcon icon = new ImageIcon(GameConst.IMG_PATH + "red-up.png");
        setIconImage(icon.getImage());
        // 设置窗口可见
        setVisible(true);
        // 设置窗口大小不可调整
        setResizable(false);
        // 监听窗口关闭，程序结束
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 让该Frame中的panel聚焦，可以响应键盘事件
        panel.requestFocus();
        // 开始移动地面
        panel.action();
    }
}
