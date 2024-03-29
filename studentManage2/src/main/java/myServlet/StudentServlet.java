package myServlet;

import com.github.pagehelper.PageInfo;
import pojo.QueryStuVo;
import pojo.StudentsMessage;
import pojo.StudentsTable;
import service.StudentService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/*")
public class StudentServlet extends BaseServlet {

    /**
     * 通过反射的方式执行;
     * 查询所有学生;
     */
    @SuppressWarnings("unused")
    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<StudentsMessage> studentsMessages = StudentService.allStudent();
        writeJSON(studentsMessages, resp);
    }

    /**
     * 添加一个学生
     */
    @SuppressWarnings("unused")
    public void addStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StudentsTable studentsTable = parseReq(req, StudentsTable.class);
        int status = StudentService.addStudentTable(studentsTable);
        writeStatus(status, resp);
    }

    /**
     * 修改一个学生的数据
     */
    @SuppressWarnings("unused")
    public void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StudentsTable studentsTable = parseReq(req, StudentsTable.class);
        int status = StudentService.updateStuTable(studentsTable);
        writeStatus(status, resp);
    }

    /**
     * 删除一组学生
     *
     * @param req  学生id的数组
     * @param resp 删除是否成功
     */
    @SuppressWarnings("unused")
    public void deleteByIds(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int[] ids = parseReq(req, int[].class);
        String status = StudentService.deleteGroup(ids);
        resp.getWriter().write(status);
    }

    /**
     * 分页查询,以及缺失与模糊查询
     *
     * @param req  索引开始页数和一页最大条目数，还有两个查询条件
     * @param resp 学生条目的总数和学生条目的实体对象
     */
    @SuppressWarnings("unused")
    public void selectPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取索引开始位置,以及最大条目数
        QueryStuVo queryStuVo = parseReq(req, QueryStuVo.class);
        // 开始查询
        PageInfo<StudentsMessage> studentsMessages = StudentService.selectPage(queryStuVo);
        // 写入封装对象
        writeJSON(studentsMessages, resp);
    }

    /**
     * 根据一个学生的id进行查询并返回结果
     *
     * @param resp 一个学生的信息
     */
    @SuppressWarnings("unused")
    public void selectAStu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = parseReq(req, int.class);
        StudentsTable studentsTable = StudentService.selectId(id);
        writeJSON(studentsTable, resp);
    }

    /**
     * 根据一个学生的id进行查询并返回结果
     *
     * @param req  一个学生的id
     * @param resp 一个学生的信息
     */
    @SuppressWarnings("unused")
    public void deleteAStu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = parseReq(req, int.class);
        String status = StudentService.deleteOrigin(id);
        resp.getWriter().write(status);
    }
}

