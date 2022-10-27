package myServlet;

import myUtils.CheckCodeUtil;
import pojo.UserMessage;
import sqlDemo.UserDemo;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/login/*")
public class LoginServlet extends MyBaseServlet {

    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserMessage userMessage = parseReq(req, UserMessage.class);
        //获取参数
        String nameJade = userMessage.getNameJade();
        String passwordJade = userMessage.getPasswordJade();
        boolean remember = userMessage.isRemember();
        if ("".equals(nameJade) || "".equals(passwordJade)) {
            resp.getWriter().write("NoText");
        } else {
            int status = UserDemo.userSelect(nameJade, passwordJade);
            if (remember) addMyCookie(resp, nameJade, passwordJade);
            //登录成功,建立一个session
            HttpSession session = req.getSession();
            session.setAttribute("user", nameJade);
            writeStatus(status, resp);
        }

    }

    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserMessage userMessage = parseReq(req, UserMessage.class);
        //获取参数
        String nameJade = userMessage.getNameJade();
        String passwordJade = userMessage.getPasswordJade();
        boolean remember = userMessage.isRemember();
        if ("".equals(nameJade) || "".equals(passwordJade)) {
            resp.getWriter().write("NoText");
        } else {
            if (UserDemo.userSelectByName(nameJade)) {
                if (remember) addMyCookie(resp, nameJade, passwordJade);
                int status = UserDemo.addUser(nameJade, passwordJade);
                writeStatus(status, resp);
            } else {
                resp.getWriter().write("Already");
            }
        }
    }

    public void checkSendCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String line = reader.readLine();
        if (line == null || "".equals(line)) {
            resp.getWriter().write("NoCheck");
            return;
        }
        HttpSession session = req.getSession();
        String checkCode = (String) session.getAttribute("checkCode");
        System.out.println("验证码"+checkCode+" 输入:"+line);
        if (checkCode.equalsIgnoreCase(line)) {
            resp.getWriter().write("YesCheck");
        } else {
            resp.getWriter().write("ErrCheck");
        }
    }

    public void checkCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            // 通过工具类生成验证码与验证码图片
            ServletOutputStream os = resp.getOutputStream();
            String checkCode = CheckCodeUtil.outputVerifyImage(100, 50, os, 4);
            // 将验证码字符写入session
            HttpSession session = req.getSession(true);
            session.setAttribute("checkCode", checkCode);
    }

    /**
     * 为登录与注册之中添加cookie的操作进行简化
     */
    private void addMyCookie(HttpServletResponse resp, String username, String password) {
        //如果勾选了"记住密码"设置两个cookie
        Cookie cookieUsername = new Cookie("username_Jade", username);
        Cookie cookiePassword = new Cookie("password_Jade", password);
        //设置存活时间，半个小时
        cookieUsername.setMaxAge(60 * 30);
        cookiePassword.setMaxAge(60 * 30);
        //给浏览器添加cookie
        resp.addCookie(cookieUsername);
        resp.addCookie(cookiePassword);
    }

    public void getMyCookie(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserMessage userMessage = new UserMessage();
        userMessage.setRemember(true);
        userMessage.setNameJade(getACookie(req, "username_Jade"));
        userMessage.setPasswordJade(getACookie(req, "password_Jade"));
        writeJSON(userMessage, resp);
    }

    private String getACookie(HttpServletRequest req, String nameNeed) {
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            String value = cookie.getValue();
            if (nameNeed.equals(name)) {
                return value;
            }
        }
        return "";
    }
}
