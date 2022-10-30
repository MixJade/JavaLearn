import myConfig.JdbcConfig;
import myConfig.MybatisConfig;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan({"service","dao","testAOP","baiduCheck"})
@EnableAspectJAutoProxy
@PropertySource("classpath:jdbc.properties")
@Import({JdbcConfig.class, MybatisConfig.class})
public class SpringConfig {
}