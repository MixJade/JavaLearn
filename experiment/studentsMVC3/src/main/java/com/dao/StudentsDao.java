package com.dao;

import com.domain.StudentsTable;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentsDao {
    @Select("select * from students;")
    List<StudentsTable> selectAll();

}
