package sqlMapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import pojo.StudentsMessage;
import org.apache.ibatis.annotations.Select;
import pojo.StudentsTable;
import pojo.UserMessage;

import java.util.List;


public interface StudentsMapper {

    @Select("select * from students s1,societys s2 where s1.societyId=s2.societyId;")
    List<StudentsMessage> selectAll();

    int addStudent(StudentsTable studentsTable);

    void deleteIdGroup(@Param("idGroup") int[] idGroup);

    List<StudentsMessage> selectByPage(@Param("studentName") String studentName, @Param("societyId") Integer societyId, @Param("begin") int begin, @Param("pageItem") int pageItem);

    int selectCount(@Param("studentName") String studentName, @Param("societyId") Integer societyId);

    @Select("select * from students where id= #{id};")
    StudentsTable selectById(@Param("id") int id);

    int updateOrigin(StudentsTable studentsTable);

    @Delete("delete from students where id = #{id};")
    void deleteOrigin(int id);

    @Select("select count(*) from loginmixjade where nameJade= #{nameJade} and passwordJade= #{passwordJade};")
    int userSelect(@Param("nameJade") String nameJade, @Param("passwordJade") String passwordJade);

    @Select("select count(*) from loginmixjade where nameJade= #{nameJade};")
    int userSelectByName(@Param("nameJade") String nameJade);

    @Insert("INSERT loginmixjade(nameJade,passwordJade) VALUES(#{nameJade},#{passwordJade})")
    int addUser(UserMessage userMessage);
}
