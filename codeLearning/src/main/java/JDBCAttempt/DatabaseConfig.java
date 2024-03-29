package JDBCAttempt;

/**
 * 数据库链接配置
 */
public interface DatabaseConfig {
    String URL = "jdbc:mysql://localhost:3306/play?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true",
            USERNAME = "root",
            PASSWORD = "MC@:(==ni2024";
    // 如果只连本地数据库，并且端口号是3306（mysql默认端口号）且mysql版本大于5，可省略端口号
    String SIMPLE_URL = "jdbc:mysql:///play?useSSL=true&serverTimezone=UTC";
}
