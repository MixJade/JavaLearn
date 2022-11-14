package com.demo.service;


import com.demo.domain.Students;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StudentsService {
    boolean addStu(Students students);

    boolean deleteId(int id);

    boolean update(Students students);

    List<Students> getAll();

    Students getById(int id);

}
