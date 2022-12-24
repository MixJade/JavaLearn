package service;

import dao.StudentsDao;
import domain.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentsImpl implements StudentsService {
    private StudentsDao studentsDao;

    @Autowired
    public void setStudentsDao(StudentsDao studentsDao) {
        this.studentsDao = studentsDao;
    }

    @Override
    public boolean addStu(Students students) {
        return studentsDao.addStudent(students) > 0;
    }

    @Override
    public boolean deleteId(int id) {
        return studentsDao.deleteOrigin(id) > 0;
    }

    @Override
    public boolean update(Students students) {
        return studentsDao.update(students) > 0;
    }

    @Override
    public List<Students> getAll() {
        return studentsDao.selectAll();
    }

    @Override
    public Students getById(int id) {
        return studentsDao.selectById(id);
    }


}