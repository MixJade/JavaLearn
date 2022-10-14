package web;

import pojo.StudentsTable;
import sqlDemo.ByIdDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Students/updateStudent")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        StudentsTable student = ByIdDemo.selectId(id);
        System.out.println("修改前的值："+student);
        req.setAttribute("student", student);
        req.getRequestDispatcher("updateById.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");//设置读取的字符编码
        // 获取参数
        String id = req.getParameter("id");
        String studentName = req.getParameter("studentName");
        String sex = req.getParameter("sex");
        String englishGrade = req.getParameter("englishGrade");
        String mathGrade = req.getParameter("mathGrade");
        String societyId = req.getParameter("societyId");
        String height = req.getParameter("height");
        String birthday = req.getParameter("birthday");
        String money = req.getParameter("money");
        // 写入对象
        StudentsTable studentsTable = new StudentsTable();
        studentsTable.setId(Integer.parseInt(id));
        studentsTable.setMessage(studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money);
        int status = ByIdDemo.updateSQL(studentsTable);
        // 响应数据
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        if (status>0){
            writer.write(String.valueOf(studentsTable));
            writer.write("<br>写入成功，成功修改了"+status+"行：<a href=\"students\">查看所有学生信息</a>");
        }else {
            writer.write("写入失败");
        }

    }
}
