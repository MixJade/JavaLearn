package myServlet;

import com.alibaba.fastjson.JSON;
import myUtils.BaseServlet;
import pojo.PageBean;
import pojo.PageBirth;
import pojo.StudentsMessage;
import pojo.StudentsTable;
import sqlDemo.AddStudentDemo;
import sqlDemo.DeleteIdGroupDemo;
import sqlDemo.SelectPageDemo;
import sqlDemo.StudentsDemo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/manager/*")
public class ManagerServlet extends BaseServlet {
    /**
     * 通过反射的方式执行;
     * 查询所有学生;
     */
    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<StudentsMessage> studentsMessages = StudentsDemo.allStudent();
        // 写入JSON
        String jsonString = JSON.toJSONString(studentsMessages);
        // 相应数据
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    /**
     * 添加一个学生
     *
     * @param req  StudentsTable的对象
     * @param resp 添加是否成功
     */
    public void addStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    /**
     * 删除一组学生
     *
     * @param req  学生id的数组
     * @param resp 删除是否成功
     */
    public void deleteByIds(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取参数
        BufferedReader reader = req.getReader();
        String line = reader.readLine();
        //通过json解析字符串
        int[] ids = JSON.parseObject(line, int[].class);
        String status = DeleteIdGroupDemo.deleteGroup(ids);
        resp.getWriter().write(status);
    }

    /**
     * 分页查询,以及缺失与模糊查询
     *
     * @param req  索引开始页数和一页最大条目数，还有两个查询条件
     * @param resp 学生条目的总数和学生条目的实体对象
     */
    public void selectPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取参数
        BufferedReader reader = req.getReader();
        String line = reader.readLine();
        // 获取索引开始位置,以及最大条目数
        PageBirth pageBirth = JSON.parseObject(line, PageBirth.class);
        // 解析参数
        int pageItem = pageBirth.getPageItem();
        int begin = (pageBirth.getCurrentPage1() - 1) * pageItem;
        String studentName = "%" + pageBirth.getStudentName() + "%";
        Integer societyId = pageBirth.getSocietyId();
        // 开始查询
        List<StudentsMessage> studentsMessages = SelectPageDemo.selectPage(studentName, societyId, begin, pageItem);
        int studentCount = SelectPageDemo.selectCount(studentName, societyId);
        // 写入封装对象
        PageBean<StudentsMessage> pageBean = new PageBean<>();
        pageBean.setRows(studentsMessages);
        pageBean.setTotalItem(studentCount);
        // 写入JSON
        String jsonString = JSON.toJSONString(pageBean);
        // 相应数据
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
}

