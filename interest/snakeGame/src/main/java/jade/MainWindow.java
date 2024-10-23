package jade;

import jade.handle.HandleData;
import jade.recognition.HuaRongRoad;
import jade.recognition.SnakeWindow;
import jade.view.RegisterAndLoginView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

/**
 * 贪吃蛇游戏启动类
 */
public class MainWindow extends JFrame implements ActionListener {
    JButton gameOne;
    JButton gameTwo;
    JButton registerSanYu;
    RegisterAndLoginView view;

    MainWindow() {
        // 设置图标
        try (InputStream favor = getClass().getResourceAsStream("th006.jpg")) {
            if (favor != null) setIconImage(new ImageIcon(ImageIO.read(favor)).getImage());
        } catch (IOException ignored) {
        }
        setBounds(100, 100, 800, 300);
        JPanel gameFace = new JPanel();
        gameFace.setSize(800, 80);
        view = new RegisterAndLoginView();
        gameOne = new JButton("玩华容道");
        gameTwo = new JButton("玩贪吃蛇");
        registerSanYu = new JButton("向数据库加一只hh");
        gameOne.addActionListener(this);
        gameTwo.addActionListener(this);
        registerSanYu.addActionListener(this);
        gameFace.add(gameOne, BorderLayout.CENTER);
        gameFace.add(gameTwo, BorderLayout.CENTER);
        add(view, BorderLayout.CENTER);
        add(gameFace, BorderLayout.SOUTH);
        add(registerSanYu, BorderLayout.NORTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setTitle("登录玩游戏(2022-6-24)");
    }

    public void actionPerformed(ActionEvent e) {
        if (!view.isLoginSuccess()) {
            JOptionPane.showMessageDialog(this, "请登录", "登录提示", JOptionPane.WARNING_MESSAGE);
        }
        if (view.isLoginSuccess()) {
            if (e.getSource() == gameTwo) {
                SwingUtilities.invokeLater(SnakeWindow::new);
            } else {
                SwingUtilities.invokeLater(HuaRongRoad::new);
            }
        }
        if (e.getSource() == registerSanYu) {
            hh();
        }
    }

    public void hh() {
        HandleData handleRegister = new HandleData();
        handleRegister.writeRegisterModel("hh", "hh");
    }
}