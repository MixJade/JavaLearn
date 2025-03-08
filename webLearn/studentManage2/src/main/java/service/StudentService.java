package service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import myUtils.SqlUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import pojo.QueryStuVo;
import pojo.StudentsMessage;
import pojo.StudentsTable;
import sqlMapper.StudentsMapper;

import java.util.List;


public class StudentService {
    /**
     * 分页查询，每七个一页
     *
     * @param queryStuVo 查询参数
     * @return 大量学生信息
     */
    public static PageInfo<StudentsMessage> selectPage(QueryStuVo queryStuVo) {
        // 开始查询
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);

        // 使用PageHelper.startPage对即将到来的下次查询进行分页
        PageHelper.startPage(queryStuVo.pageNum(), queryStuVo.pageSize());
        // 这里的查询不需要加分页逻辑，
        // 且返回值是使用PageHelper的Page对象(继承自ArrayList，如果转化Json，只有查出来的list)
        Page<StudentsMessage> studentsMessages = mapper.selectByPage(queryStuVo.studentName(), queryStuVo.societyId());
        // 将返回的Page对象封装成PageInfo对象，这样序列化就有页码、最大数据条数了
        PageInfo<StudentsMessage> pageInfo = new PageInfo<>(studentsMessages);

        session.close();
        return pageInfo;
    }

    /**
     * 根据id查询学生
     *
     * @param id 学生id
     * @return 学生信息
     */
    public static StudentsTable selectId(int id) {
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        StudentsTable studentsTable = mapper.selectById(id);
        session.close();
        return studentsTable;
    }

    /**
     * 直接查询所有学生
     *
     * @return 所有学生带着社团名字的信息
     */
    public static List<StudentsMessage> allStudent() {
        // 获取SqlSession对象，用于执行sql
        SqlSession session = SqlUtil.getFactory().openSession();
        // 执行sql
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<StudentsMessage> studentsMessages = mapper.selectAll();
        // 释放资源
        session.close();
        return studentsMessages;
    }

    /**
     * 添加学生
     *
     * @return 是否添加成功
     */
    public static int addStudentTable(StudentsTable studentsTable) {
        SqlSession session = SqlUtil.getFactory().openSession();
        int stu = 0;
        try {
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            stu = mapper.addStudent(studentsTable);
            // 事务提交，你懂的，后面还有一个回滚才健全。
            session.commit();
            session.close();
        } catch (PersistenceException e) {
            session.rollback();
        }
        return stu;
    }

    /**
     * 修改学生信息
     */
    public static int updateStuTable(StudentsTable studentsTable) {
        SqlSession session = SqlUtil.getFactory().openSession();
        int stu = 0;
        try {
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            stu = mapper.updateOrigin(studentsTable);
            // 事务提交，你懂的，后面还有一个回滚才健全。
            session.commit();
            session.close();
        } catch (PersistenceException e) {
            session.rollback();
        }
        return stu;
    }

    /**
     * 批量删除;
     * 接口通过@Param重写默认数组名array；
     * 映射语句foreach遍历数组
     */
    public static String deleteGroup(int[] idGroup) {
        try {
            SqlSession session = SqlUtil.getFactory().openSession();
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            mapper.deleteIdGroup(idGroup);
            session.commit();
            session.close();
            return "YES";
        } catch (PersistenceException e) {
            System.out.println("删除失败。\n" + e);
        }
        return "NO";
    }

    /**
     * 通过id删除学生
     */
    public static String deleteOrigin(int id) {
        try {
            SqlSession session = SqlUtil.getFactory().openSession();
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            mapper.deleteOrigin(id);
            session.commit();
            session.close();
            return "YES";
        } catch (PersistenceException e) {
            System.out.println("删除失败。\n" + e);
        }
        return "NO";
    }
}
