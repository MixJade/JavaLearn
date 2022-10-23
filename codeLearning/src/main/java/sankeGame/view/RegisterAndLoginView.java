package sankeGame.view;

import javax.swing.*;
import java.awt.*;

public class RegisterAndLoginView extends JPanel {
    JTabbedPane p;
    RegisterView registerface;
    LoginView loginface;

    public RegisterAndLoginView() {
        registerface = new RegisterView();
        loginface = new LoginView();
        setLayout(new BorderLayout());
        p = new JTabbedPane();
        p.add("我要登录", loginface);
        p.add("我要注册", registerface);
        p.validate();
        add(p, BorderLayout.CENTER);
    }

    public boolean isLoginSuccess() {
        return loginface.isLoginSuccess();
    }
}