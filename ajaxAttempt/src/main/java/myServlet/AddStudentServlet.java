package myServlet;

import com.alibaba.fastjson.JSON;
import pojo.StudentsTable;
import sqlDemo.AddStudentDemo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/Students/addStudent")
public class AddStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取参数
        BufferedReader reader = req.getReader();
        String line = reader.readLine();
        //通过json解析字符串
        StudentsTable studentsTable = JSON.parseObject(line, StudentsTable.class);
        int status = AddStudentDemo.addStudentTable(studentsTable);
        // 响应数据
        if (status == 1) {
            resp.getWriter().write("YES");
        } else {
            resp.getWriter().write("NO");
        }
    }
}
