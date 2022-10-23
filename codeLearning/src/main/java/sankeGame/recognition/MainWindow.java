package sankeGame.recognition;

import sankeGame.handle.HandleInsertData;
import sankeGame.model.Register;
import sankeGame.view.RegisterAndLoginView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {
    JButton gameOne;
    JButton gameTwo;
    JButton registerSanYu;
    RegisterAndLoginView view;

    MainWindow() {
        setBounds(100, 100, 800, 300);
        JPanel gameFace = new JPanel();
        gameFace.setSize(800, 80);
        view = new RegisterAndLoginView();
        gameOne = new JButton("玩华容道");
        gameTwo = new JButton("玩贪吃蛇");
        registerSanYu = new JButton("向数据库加一只散玉");
        gameOne.addActionListener(this);
        gameTwo.addActionListener(this);
        registerSanYu.addActionListener(this);
        gameFace.add(gameOne, BorderLayout.CENTER);
        gameFace.add(gameTwo, BorderLayout.CENTER);
        add(view, BorderLayout.CENTER);
        add(gameFace, BorderLayout.SOUTH);
        add(registerSanYu, BorderLayout.NORTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (!view.isLoginSuccess()) {
            JOptionPane.showMessageDialog(null, "请登录", "登录提示", JOptionPane.WARNING_MESSAGE);
        }
        if (view.isLoginSuccess()) {
            if (e.getSource() == gameTwo) {
                new interface_snake();
            } else {
                new Hua_Rong_Road();
            }
        }
        if (e.getSource() == registerSanYu) {
            sanYu();
        }
    }

    public void sanYu() {
        Register sanYu = new Register();
        sanYu.setNameJade("MixJade001");
        sanYu.setPasswordJade("hhh");
        HandleInsertData handleRegister = new HandleInsertData();
        handleRegister.writeRegisterModel(sanYu);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setTitle("登录玩游戏(2022-6-24)");
    }
}