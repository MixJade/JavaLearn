package myServlet;

import myUtils.JsonUtil;
import pojo.StudentsMessage;
import sqlDemo.StudentsDemo;

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
        // 下面那个是fastJson的转化方式，这个不能转化record类
        // String jsonString = JSON.toJSONString(studentsMessages);
        // 使用jackson转化，这个可以转化record
        String jsonString = JsonUtil.toStr(studentsMessages);
        // 相应数据
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doGet(req, resp);
    }
}
