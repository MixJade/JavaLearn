package sqlMapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.StudentsMessage;
import pojo.StudentsTable;

import java.util.List;


public interface StudentsMapper {

    @Select("select * from students s1,societys s2 where s1.societyId=s2.societyId;")
    List<StudentsMessage> selectAll();

    int addStudent(StudentsTable studentsTable);

    void deleteIdGroup(@Param("idGroup") int[] idGroup);

    Page<StudentsMessage> selectByPage(@Param("studentName") String studentName,
                                       @Param("societyId") Integer societyId);

    @Select("select * from students where id= #{id};")
    StudentsTable selectById(@Param("id") int id);

    int updateOrigin(StudentsTable studentsTable);

    @Delete("delete from students where id = #{id};")
    void deleteOrigin(int id);
}
