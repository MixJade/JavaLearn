import dao.BookDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.BookService;

public class SpringTest {
    /**
     * IoC的尝试
     */
    @Test
    public void testIoC() {
        //加载xml文件，获取容器对象
        ClassPathXmlApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取资源
        BookService bookService = (BookService) acx.getBean("service");
        bookService.deposit();
        //关闭容器以执行销毁方法
        //acx.close();
        acx.registerShutdownHook();
    }

    /**
     * 单例对象的地址都一样
     */
    @Test
    public void testBeanScope() {
        ApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao1 = (BookDao) acx.getBean("secondDao");
        BookDao bookDao2 = (BookDao) acx.getBean("secondDao");
        System.out.println(bookDao1);
        System.out.println(bookDao2);
    }
    /**
     * 测试集合注入
     */
    @Test
    public void testGather(){
        ClassPathXmlApplicationContext acx=new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao= (BookDao) acx.getBean("bookDao");
        bookDao.store();
    }
}