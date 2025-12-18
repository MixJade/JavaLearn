package work;

import work.enums.DbType;
import work.model.entity.TableName;

import java.util.List;

/**
 * 表结构生成器的全局配置
 *
 * @since 2025-12-02 11:20:27
 */
public interface DeConfig {
    // 此处数据库类型可以选择MySql、Oracle
    DbType dbType = DbType.MySql;

    // 数据库链接、账号、密码
    String url = "jdbc:mysql://localhost:3306/play?useSSL=true",
            username = "root",
            password = "MC@:(==ni2024";

    // 需要输出的文件名，无后缀
    String outFileName = "PLAY数据库";

    // 需要输出的表名
    List<TableName> needOutTab = List.of(
            new TableName("students", "学生表"),
            new TableName("societys", "社团表"),
            new TableName("dog", "") // 若数据库有表注释，可以不写
    );
}
