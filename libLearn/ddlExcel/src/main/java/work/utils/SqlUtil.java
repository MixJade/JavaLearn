package work.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import work.enums.DbType;

import java.io.IOException;
import java.io.InputStream;


/**
 * 动态返回不同数据库的配置文件
 *
 * @since 2025-07-19 21:30:40
 */
public class SqlUtil {
    public static SqlSessionFactory getFactory(DbType dbType) {
        try {
            String dbConfigName = "mb_MySQL.xml";
            if (dbType == DbType.Oracle)
                dbConfigName = "mb_Oracle.xml";
            InputStream inputStream = Resources.getResourceAsStream(dbConfigName);
            return new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            System.out.println("配置文件加载失败:\n" + e);
            System.exit(1);
            return null;
        }
    }
}
