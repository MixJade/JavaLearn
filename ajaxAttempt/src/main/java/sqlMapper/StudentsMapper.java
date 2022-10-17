package sqlMapper;

import pojo.StudentsMessage;
import org.apache.ibatis.annotations.Select;
import pojo.StudentsTable;

import java.util.List;


public interface StudentsMapper {

    @Select("select * from students s1,societys s2 where s1.societyId=s2.societyId;")
    List<StudentsMessage> selectAll();

    int addStudent(StudentsTable studentsTable);
}
