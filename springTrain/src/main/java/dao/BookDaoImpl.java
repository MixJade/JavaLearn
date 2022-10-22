package dao;

public class BookDaoImpl implements BookDao {
    public BookDaoImpl() {
        System.out.println("This is a constructor");
    }

    @Override
    public void store() {
        System.out.println("The book is stored on the shelf");
    }
}