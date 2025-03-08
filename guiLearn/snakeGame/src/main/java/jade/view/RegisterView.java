package jade.view;


import jade.controller.HandleData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends JPanel implements ActionListener {
    JTextField inputID;
    JPasswordField inputPassword;
    JButton buttonRegister;

    RegisterView() {
        inputID = new JTextField(12);
        inputPassword = new JPasswordField(12);
        buttonRegister = new JButton("注册");
        add(new JLabel("用户名:"));
        add(inputID);
        add(new JLabel("密码:"));
        add(inputPassword);
        add(buttonRegister);
        buttonRegister.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        char[] pw = inputPassword.getPassword();
        HandleData handleRegister = new HandleData();
        handleRegister.writeRegisterModel(inputID.getText(), new String(pw));
    }
}