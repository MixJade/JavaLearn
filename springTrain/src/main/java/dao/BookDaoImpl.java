package dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("bookDaoImpl")
public class BookDaoImpl implements BookDao {
    @Value("${myBookName}")
    private String bookName;
    @Value("${myMoney}")
    private int bookPrice;

    @Override
    public void store() {
        System.out.println("The book is stored on the shelf");
        System.out.println(bookName + ":" + bookPrice);
    }
}