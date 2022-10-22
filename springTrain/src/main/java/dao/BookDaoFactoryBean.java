package dao;

import org.springframework.beans.factory.FactoryBean;

public class BookDaoFactoryBean implements FactoryBean<BookDao> {

    @Override
    public BookDao getObject() throws Exception {
        System.out.println("得到对象");
        return new BookDaoImpl();
    }

    @Override
    public Class<?> getObjectType() {
        System.out.println("返回类型");
        return BookDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
