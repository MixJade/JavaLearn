package com.demo.dao;

import com.demo.domain.Students;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
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
