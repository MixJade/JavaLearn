package service;

import pojo.StudentsTable;

import java.util.List;

public interface StudentService {
    List<StudentsTable> selectAll();
}
