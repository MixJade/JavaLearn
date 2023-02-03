package snakeGame.view;


import snakeGame.handle.HandleInsertData;
import snakeGame.model.Register;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends JPanel implements ActionListener {
    Register register;
    JTextField inputID;
    JPasswordField inputPassword;
    JButton buttonRegister;

    RegisterView() {
        register = new Register();
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
        register.setNameJade(inputID.getText());
        char[] pw = inputPassword.getPassword();
        register.setPasswordJade(new String(pw));
        HandleInsertData handleRegister = new HandleInsertData();
        handleRegister.writeRegisterModel(register);
    }
}