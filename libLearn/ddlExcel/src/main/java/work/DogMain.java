package work;

import work.enums.DbType;
import work.model.entity.TableName;
import work.service.DogService;

import java.util.ArrayList;
import java.util.List;

/**
 * 狗工作的主要类，只在这里做区别配置
 *
 * @author MixJade
 * @since 2025-07-19
 */
public class DogMain {
    private static final DogService dogService = new DogService();

    public static void main(String[] args) {
        List<TableName> needOutTab = new ArrayList<>();

        // 需要输出DDL的表名称，以及注释(选填，没有查到表注释时才使用这里的)
        needOutTab.add(new TableName("students", "学生表"));
        needOutTab.add(new TableName("societys", "社团表"));
        needOutTab.add(new TableName("dog", "")); // 数据库有表注释，可以不填

        // 此处数据库类型可以选择MySQL、Oracle，但记得调整相应配置文件
        DbType dbType = DbType.MySQL;

        // 输出表结构的excel
        dogService.genXlsxTableDDL(dbType, needOutTab, "PLAY数据库.xlsx");

        // 输出表结构的xml
        dogService.genXmlTableDDL(dbType, needOutTab, "PLAY数据库.xml");
    }
}
