package myServlet;

import com.alibaba.fastjson.JSON;
import sqlDemo.StudentsDemo;
import pojo.StudentsMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/Students/students")
public class AllStudentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StudentsDemo studentsDemo = new StudentsDemo();
        List<StudentsMessage> studentsMessages = studentsDemo.allStudent();
        // 写入JSON
        String jsonString = JSON.toJSONString(studentsMessages);
        // 相应数据
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doGet(req, resp);
    }
}
