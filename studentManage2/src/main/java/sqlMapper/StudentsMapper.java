package sqlMapper;

import org.apache.ibatis.annotations.Param;
import pojo.StudentsMessage;
import org.apache.ibatis.annotations.Select;
import pojo.StudentsTable;

import java.util.List;


public interface StudentsMapper {

    @Select("select * from students s1,societys s2 where s1.societyId=s2.societyId;")
    List<StudentsMessage> selectAll();

    int addStudent(StudentsTable studentsTable);

    void deleteIdGroup(@Param("idGroup") int[] idGroup);

    List<StudentsMessage> selectByPage(@Param("studentName") String studentName, @Param("societyId") Integer societyId, @Param("begin") int begin, @Param("pageItem") int pageItem);

    int selectCount(@Param("studentName") String studentName, @Param("societyId") Integer societyId);

    List<StudentsMessage> selectLack(StudentsTable studentsTable);
}
