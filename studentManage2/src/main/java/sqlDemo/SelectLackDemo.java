package sqlDemo;

import myUtils.SqlUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.StudentsMessage;
import pojo.StudentsTable;
import sqlMapper.StudentsMapper;

import java.util.List;

public class SelectLackDemo {
    /**
     * 条件缺失查询;
     * 注意实体类的参数用Integer
     * 注意是模糊查询
     */
    public static List<StudentsMessage> queryLack(String studentName, Integer societyId) {
        if (studentName != null)
            studentName = "%" + studentName + "%";
        StudentsTable studentTable = new StudentsTable();
        studentTable.setStudentName(studentName);
        studentTable.setEnglishGrade(societyId);
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<StudentsMessage> studentsMessages = mapper.selectLack(studentTable);
        session.close();
        return studentsMessages;
    }
}
