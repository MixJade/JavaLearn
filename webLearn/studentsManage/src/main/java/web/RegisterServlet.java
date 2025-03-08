package web;

import mixUtils.CookieAddUtil;
import sqlDemo.UserDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginRegister/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //读取请求体参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String checkCode = req.getParameter("checkCode");
        String remember = req.getParameter("remember");
        // 验证验证码,忽略大小写
        HttpSession session = req.getSession();
        String checkCode1 = (String) session.getAttribute("checkCode");
        if (!checkCode1.equalsIgnoreCase(checkCode)) {
            req.setAttribute("register_fail", "验证码错误");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        // 验证用户是否存在，不存在则进行注册
        if (UserDemo.userSelectByName(username)) {
            UserDemo.addUser(username, password);
            //如果勾选了"记住密码"设置两个cookie
            if ("on".equals(remember)) {
                CookieAddUtil.addMyCookie(resp, username, password);
            }
            resp.sendRedirect("userLogin.jsp");
        } else {
            req.setAttribute("register_fail", "注册失败,用户名已存在");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        this.doPost(req, resp);
    }
}
