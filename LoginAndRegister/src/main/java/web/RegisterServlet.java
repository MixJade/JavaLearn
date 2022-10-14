package web;

import sqlDemo.UserDemo;
import pojo.UserMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginRegister/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserMessage user = UserDemo.userSelectByName(username);
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        if (user != null) {
            resp.getWriter().write("<h2>你好!<br>注册失败，已经有这个名字了</h2>");
        } else {
            resp.getWriter().write("<h2>注册成功</h2><br><a href=\"Login.html\">开始登录</a>");
            UserDemo.addUser(username, password);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
