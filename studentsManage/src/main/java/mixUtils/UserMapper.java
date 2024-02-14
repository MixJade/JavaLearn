package mixUtils;

import org.apache.ibatis.annotations.Delete;
import pojo.StudentsMessage;
import pojo.StudentsTable;
import pojo.UserMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserMapper {

    @Select("select nameJade from loginmixjade where nameJade= #{nameJade} and passwordJade= #{passwordJade};")
    UserMessage userSelect(@Param("nameJade") String nameJade, @Param("passwordJade") String passwordJade);

    @Select("select nameJade from loginmixjade where nameJade= #{nameJade};")
    UserMessage userSelectByName(@Param("nameJade") String nameJade);

    @Insert("INSERT loginmixjade(nameJade,passwordJade) VALUES(#{nameJade},#{passwordJade})")
    int addUser(UserMessage userMessage);

    @Select("select * from students s1,societys s2 where s1.societyId=s2.societyId;")
    List<StudentsMessage> selectAll();

    int addStudent(StudentsTable studentsTable);

    @Select("select * from students where id= #{id};")
    StudentsTable selectById(@Param("id") int id);

    int updateOrigin(StudentsTable studentsTable);

    @Delete("delete from students where id = #{id};")
    void deleteOrigin(int id);
}
