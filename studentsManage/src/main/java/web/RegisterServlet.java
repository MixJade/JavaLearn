package web;

import sqlDemo.UserDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginRegister/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");//设置读取的字符编码
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username);
        System.out.println(UserDemo.userSelectByName(username));
        if (UserDemo.userSelectByName(username)) {
            UserDemo.addUser(username, password);
            req.setAttribute("login_fail", "注册成功!");
            req.getRequestDispatcher("userLogin.jsp").forward(req, resp);
        } else {
            req.setAttribute("register_fail","注册失败,用户名已存在");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        this.doPost(req, resp);
    }
}
