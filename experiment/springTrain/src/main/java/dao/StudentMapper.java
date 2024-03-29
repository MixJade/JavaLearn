package dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import pojo.StudentsTable;

import java.util.List;

@Repository
public interface StudentMapper {
    @Select("select * from students")
    List<StudentsTable> selectAll();
}
