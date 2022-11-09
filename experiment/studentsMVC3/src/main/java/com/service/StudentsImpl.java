package com.service;

import com.dao.StudentsDao;
import com.domain.StudentsTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StudentsImpl implements StudentsService {
    @Autowired
    private StudentsDao studentsDao;

    @Override
    public List<StudentsTable> getAll() {
        return studentsDao.selectAll();
    }
}