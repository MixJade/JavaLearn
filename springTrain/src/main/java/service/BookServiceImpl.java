package service;

import dao.BookDao;

public class BookServiceImpl implements BookService {
    private BookDao bookDao;
    @Override
    public void deposit() {
        System.out.println("You are saving a book");
        bookDao.store();
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
