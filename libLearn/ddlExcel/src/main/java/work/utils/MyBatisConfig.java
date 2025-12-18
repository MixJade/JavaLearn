package work.utils;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import work.DeConfig;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * 根据数据库类型不同，使用不同xml
 *
 * @since 2025-12-2 14:46
 */
public final class MyBatisConfig {
    // 构建池化数据源
    private static DataSource buildDataSource() {
        PooledDataSource dataSource = new PooledDataSource();
        switch (DeConfig.dbType) {
            case MySql -> dataSource.setDriver("com.mysql.cj.jdbc.Driver");
            case Oracle -> dataSource.setDriver("oracle.jdbc.driver.OracleDriver");
        }
        dataSource.setUrl(DeConfig.url);
        dataSource.setUsername(DeConfig.username);
        dataSource.setPassword(DeConfig.password);
        return dataSource;
    }

    private static void loadMapperXml(Configuration configuration) {
        // 按数据库类型拼接 XML 路径
        String xmlPath = switch (DeConfig.dbType) {
            case MySql -> "mysql/DeMapper.xml"; // MySql 专属 XML
            case Oracle -> "oracle/DeMapper.xml"; // Oracle 专属 XML
        };
        try (InputStream inputStream = Resources.getResourceAsStream(xmlPath)) {
            XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(
                    inputStream,
                    configuration,
                    xmlPath,
                    configuration.getSqlFragments()
            );
            mapperBuilder.parse(); // 核心：解析 XML 并绑定到 Configuration
        } catch (IOException e) {
            System.err.println("未找到Mapper XML文件：" + xmlPath);
        }
    }

    public static SqlSessionFactory getFactory() {
        // 1. 配置数据源
        DataSource dataSource = buildDataSource();
        // 2. 配置事务管理器
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        // 3. 配置环境
        Environment environment = new Environment.Builder("development")
                .transactionFactory(transactionFactory)
                .dataSource(dataSource)
                .build();
        // 4. 构建MyBatis核心配置
        Configuration configuration = new Configuration(environment);
        // 配置日志输出
        configuration.setLogImpl(StdOutImpl.class);
        // 5. 注册Mapper
        loadMapperXml(configuration);
        // 6. 构建 SqlSessionFactory
        return new SqlSessionFactoryBuilder().build(configuration);
    }
}