package jade.view;

import jade.controller.HandleData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JPanel implements ActionListener {
    JTextField inputID;
    JPasswordField inputPassword;
    JButton buttonLogin;
    boolean loginSuccess;

    LoginView() {
        inputID = new JTextField(12);
        inputPassword = new JPasswordField(12);
        buttonLogin = new JButton("登录");
        add(new JLabel("用户名:"));
        add(inputID);
        add(new JLabel("密码:"));
        add(inputPassword);
        add(buttonLogin);
        buttonLogin.addActionListener(this);
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void actionPerformed(ActionEvent e) {
        String name = inputID.getText();
        char[] pw = inputPassword.getPassword();
        String pwd = new String(pw);
        var handleLogin = new HandleData();
        loginSuccess = handleLogin.queryVerify(name, pwd);
    }
}