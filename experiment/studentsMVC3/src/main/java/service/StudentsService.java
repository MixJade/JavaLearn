package service;

import domain.Students;

import java.util.List;


public interface StudentsService {
    boolean addStu(Students students);

    boolean deleteId(int id);

    boolean update(Students students);

    List<Students> getAll();

    Students getById(int id);

}
