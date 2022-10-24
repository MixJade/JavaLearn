package dao;

import org.springframework.stereotype.Repository;

@Repository("bookDaoImpl")
public class BookDaoImpl implements BookDao {
    private String bookName;
    private int bookPrice;

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    @Override
    public void store() {
        System.out.println("The book is stored on the shelf");
        System.out.println(bookName + ":" + bookPrice);
    }
}