package web;

import pojo.UserMessage;
import sqlDemo.UserDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginRegister/Login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserMessage user = UserDemo.userSelect(username, password);
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        if (user != null) {
            //登录成功,建立一个session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            //获取项目地址,重定向到操作页面
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/Students/students");
        } else {
            //登录失败,带上报错信息,请求转发回去
            req.setAttribute("login_fail", "用户名或密码错误");
            req.getRequestDispatcher("userLogin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        this.doGet(req, resp);
    }
}
