import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ListenerAttempt implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("对象被创建,整个web应用发布");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("对象被销毁，整个web应用被卸载");
    }
}
