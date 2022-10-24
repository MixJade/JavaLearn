import dao.BookDao;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import service.BookService;

public class SpringTest {
    /**
     * IoC的尝试
     */
    @Test
    public void testIoC() {
        ClassPathResource resource = new ClassPathResource("applicationContext.xml");
        BeanFactory bf=new XmlBeanFactory(resource);
//        BeanFactory acx = new ClassPathXmlApplicationContext();
        // 获取资源
//        BookService bookService = (BookService) acx.getBean("service");
//        bookService.deposit();
//        //关闭容器以执行销毁方法
//        //acx.close();
//        acx.registerShutdownHook();
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
        ApplicationContext acx=new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao= acx.getBean(BookDao.class);
        bookDao.store();
    }
}
