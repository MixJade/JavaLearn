package myServlet;

import com.alibaba.fastjson.JSON;
import myUtils.BaseServlet;
import pojo.*;
import sqlDemo.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/manager/*")
public class ManagerServlet extends BaseServlet {
    /**
     * 解析特定Json数据
     *
     * @param req 指定格式的数据
     * @return 指定的对象
     */
    private <T> T parseReq(HttpServletRequest req, Class<T> objectClass) throws IOException {
        BufferedReader reader = req.getReader();
        String line = reader.readLine();
        return JSON.parseObject(line, objectClass);
    }

    /**
     * 根据status来进行不同的响应
     *
     * @param status SQL是否成功
     */
    private void writeStatus(int status, HttpServletResponse resp) throws IOException {
        // 响应数据
        if (status == 1) {
            resp.getWriter().write("YES");
        } else {
            resp.getWriter().write("NO");
        }
    }

    /**
     * 通过不同的对象来写入JSON
     */
    private void writeJSON(Object object, HttpServletResponse resp) throws IOException {
        // 写入JSON
        String jsonString = JSON.toJSONString(object);
        // 相应数据
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }


    /**
     * 通过反射的方式执行;
     * 查询所有学生;
     */
    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<StudentsMessage> studentsMessages = SelectStuDemo.allStudent();
        writeJSON(studentsMessages,resp);
    }
    /**
     * 添加一个学生
     */
    public void addStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StudentsTable studentsTable = parseReq(req, StudentsTable.class);
        int status = AddStuDemo.addStudentTable(studentsTable);
        writeStatus(status, resp);
    }

    /**
     * 修改一个学生的数据
     */
    public void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StudentsTable studentsTable = parseReq(req, StudentsTable.class);
        int status = UpdateStuDemo.updateStuTable(studentsTable);
        writeStatus(status, resp);
    }

    /**
     * 删除一组学生
     *
     * @param req  学生id的数组
     * @param resp 删除是否成功
     */
    public void deleteByIds(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int[] ids = parseReq(req, int[].class);
        String status = DeleteStuDemo.deleteGroup(ids);
        resp.getWriter().write(status);
    }

    /**
     * 分页查询,以及缺失与模糊查询
     *
     * @param req  索引开始页数和一页最大条目数，还有两个查询条件
     * @param resp 学生条目的总数和学生条目的实体对象
     */
    public void selectPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取索引开始位置,以及最大条目数
        PageBirth pageBirth = parseReq(req, PageBirth.class);
        // 解析参数
        int pageItem = pageBirth.getPageItem();
        int begin = (pageBirth.getCurrentPage1() - 1) * pageItem;
        String studentName = "%" + pageBirth.getStudentName() + "%";
        Integer societyId = pageBirth.getSocietyId();
        // 开始查询
        List<StudentsMessage> studentsMessages = SelectStuDemo.selectPage(studentName, societyId, begin, pageItem);
        int studentCount = SelectStuDemo.selectCount(studentName, societyId);
        // 写入封装对象
        PageBean<StudentsMessage> pageBean = new PageBean<>();
        pageBean.setRows(studentsMessages);
        pageBean.setTotalItem(studentCount);
        writeJSON(pageBean, resp);
    }

    /**
     * 根据一个学生的id进行查询并返回结果
     *
     * @param resp 一个学生的信息
     */
    public void selectAStu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = parseReq(req, int.class);
        StudentsTable studentsTable = SelectStuDemo.selectId(id);
        writeJSON(studentsTable,resp);
    }

    /**
     * 根据一个学生的id进行查询并返回结果
     *
     * @param req  一个学生的id
     * @param resp 一个学生的信息
     */
    public void deleteAStu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = parseReq(req, int.class);
        String status = DeleteStuDemo.deleteOrigin(id);
        resp.getWriter().write(status);
    }
}

