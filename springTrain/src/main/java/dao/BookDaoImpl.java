package dao;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BookDaoImpl implements BookDao, InitializingBean, DisposableBean {
    public BookDaoImpl() {
        System.out.println("This is a constructor");
    }

    @Override
    public void store() {
        System.out.println("The book is stored on the shelf");
    }

    public void init(){
        System.out.println("Impl init==");
    }

//    public void destroy(){
//        System.out.println("Impl destroy==");
//    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean==接口");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean==接口");
    }
}