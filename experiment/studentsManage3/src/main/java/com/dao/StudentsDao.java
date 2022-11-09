package com.dao;

import com.domain.StudentsMessage;
import com.domain.StudentsTable;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface StudentsDao {

    @Select("select * from students s1,societys s2 where s1.societyId=s2.societyId;")
    List<StudentsMessage> selectAll();

    @Insert("INSERT INTO students (studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money) VALUES (#{studentName},#{sex},#{englishGrade},#{mathGrade},#{societyId},#{height},#{birthday},#{money})")
    int addStudent(StudentsTable studentsTable);

    void deleteIdGroup(@Param("idGroup") int[] idGroup);

    List<StudentsMessage> selectByPage(@Param("studentName") String studentName, @Param("societyId") Integer societyId, @Param("begin") int begin, @Param("pageItem") int pageItem);

    int selectCount(@Param("studentName") String studentName, @Param("societyId") Integer societyId);

    @Select("select * from students where id= #{id};")
    StudentsTable selectById(@Param("id") int id);

    int updateOrigin(StudentsTable studentsTable);

    @Delete("delete from students where id = #{id};")
    void deleteOrigin(int id);
}
