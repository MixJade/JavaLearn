import myConfig.JdbcConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"dao", "service"})
@PropertySource("mySelf.properties")
@Import(JdbcConfig.class)
public class SpringConfig {
}