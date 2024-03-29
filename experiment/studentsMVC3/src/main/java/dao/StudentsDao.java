package dao;

import domain.Students;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentsDao {
    @Insert("INSERT INTO students(studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money) VALUES (#{studentName},#{sex},#{englishGrade},#{mathGrade},#{societyId},#{height},#{birthday},#{money})")
    int addStudent(Students students);

    @Delete("delete from students where id = #{id};")
    int deleteOrigin(int id);

    @Update("update students set studentName=#{studentName},sex=#{sex},englishGrade=#{englishGrade},mathGrade=#{mathGrade},societyId=#{societyId},height=#{height},birthday=#{birthday},money=#{money} where id = #{id};")
    int update(Students students);

    @Select("select * from students;")
    List<Students> selectAll();

    @Select("select * from students where id= #{id};")
    Students selectById(@Param("id") int id);

}
