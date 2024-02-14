package web;

import pojo.StudentsMessage;
import sqlDemo.StudentsDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/Students/students")
public class StudentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentsDemo studentsDemo=new StudentsDemo();
        List<StudentsMessage> studentsMessages = studentsDemo.allStudent();
        // 存储到请求域里
        req.setAttribute("studentsMessage",studentsMessages);
        // 请求转发
        req.getRequestDispatcher("selectAll.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
