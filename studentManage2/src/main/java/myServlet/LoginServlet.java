package myServlet;

import myUtils.CheckCodeUtil;
import pojo.LoginVo;
import service.LoginService;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/login/*")
public class LoginServlet extends BaseServlet {

    @SuppressWarnings("unused")
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoginVo loginVo = parseReq(req, LoginVo.class);
        //获取参数
        String nameJade = loginVo.nameJade();
        String passwordJade = loginVo.passwordJade();
        boolean remember = loginVo.remember();
        if ("".equals(nameJade) || "".equals(passwordJade)) {
            resp.getWriter().write("NoText");
        } else {
            int status = LoginService.userSelect(nameJade, passwordJade);
            if (remember) addMyCookie(resp, nameJade, passwordJade);
            //登录成功,建立一个session
            HttpSession session = req.getSession();
            session.setAttribute("user", nameJade);
            writeStatus(status, resp);
        }

    }

    @SuppressWarnings("unused")
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoginVo loginVo = parseReq(req, LoginVo.class);
        //获取参数
        String nameJade = loginVo.nameJade();
        String passwordJade = loginVo.passwordJade();
        boolean remember = loginVo.remember();
        if ("".equals(nameJade) || "".equals(passwordJade)) {
            resp.getWriter().write("NoText");
        } else {
            if (LoginService.userSelectByName(nameJade)) {
                if (remember) addMyCookie(resp, nameJade, passwordJade);
                int status = LoginService.addUser(nameJade, passwordJade);
                writeStatus(status, resp);
            } else {
                resp.getWriter().write("Already");
            }
        }
    }

    @SuppressWarnings("unused")
    public void checkSendCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String line = reader.readLine();
        if (line == null || "".equals(line)) {
            resp.getWriter().write("NoCheck");
            return;
        }
        HttpSession session = req.getSession();
        String checkCode = (String) session.getAttribute("checkCode");
        System.out.println("验证码" + checkCode + " 输入:" + line);
        if (checkCode.equalsIgnoreCase(line)) {
            resp.getWriter().write("YesCheck");
        } else {
            resp.getWriter().write("ErrCheck");
        }
    }

    @SuppressWarnings("unused")
    public void checkCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 响应放在请求前面，会出现无法建立会话
        HttpSession session = req.getSession(true);
        // 通过工具类生成验证码与验证码图片
        ServletOutputStream os = resp.getOutputStream();
        String checkCode = CheckCodeUtil.outputVerifyImage(100, 50, os, 4);
        // 将验证码字符写入session
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

    @SuppressWarnings("unused")
    public void getMyCookie(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoginVo loginVo = new LoginVo(getACookie(req, "username_Jade"), getACookie(req, "password_Jade"), true);
        writeJSON(loginVo, resp);
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
