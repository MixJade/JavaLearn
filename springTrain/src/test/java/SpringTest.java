import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.BookService;

public class SpringTest {
    /**
     * IoC的尝试
     */
    @Test
    public void testIoC() {
        AnnotationConfigApplicationContext acx = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookService bookService = acx.getBean(BookService.class);
        bookService.deposit();
    }
}
