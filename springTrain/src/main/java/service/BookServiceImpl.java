package service;

import dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service()
@Scope("singleton")
public class BookServiceImpl implements BookService {
    @Autowired
    @Qualifier("bookDaoImpl")
    private BookDao bookDao;
    public BookServiceImpl() {
        System.out.println("hhhhhhhhhhh");
    }
    @Override
    public void deposit() {
        bookDao.store();
        System.out.println("You are saving a book");
    }
}
