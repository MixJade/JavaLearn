package web;

import sqlDemo.UserDemo;
import pojo.UserMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginRegister/Login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserMessage user=UserDemo.userSelect(username, password);
        System.out.println(user);
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        if (user!=null){
            resp.getWriter().write("<h1>" + username + "你好!<br>登录成功</h1>");
        } else {
            resp.getWriter().write("<h1>登录失败</h1>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
