import myConfig.JdbcConfig;
import myConfig.MybatisConfig;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan({"service","dao","testAOP"})
@EnableAspectJAutoProxy
@PropertySource("classpath:jdbc.properties")
@Import({JdbcConfig.class, MybatisConfig.class})
public class SpringConfig {
}