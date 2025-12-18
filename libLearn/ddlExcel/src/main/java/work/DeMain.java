package work;

import work.enums.DbType;
import work.service.DeService;
import work.service.DeServiceImpl;

import java.util.Scanner;

/**
 * 狗工作的主要类，只在这里做区别配置
 *
 * @author MixJade
 * @since 2025-07-19
 */
public class DeMain {
    private static final DeService DE_SERVICE = new DeServiceImpl();

    static class GenXlsx {
        public static void main(String[] args) {
            // 输出表结构的excel
            String xlsxName = DeConfig.outFileName + ".xlsx";
            DE_SERVICE.genXlsxTableDDL(xlsxName);
        }
    }

    static class GenXml {
        public static void main(String[] args) {
            // 输出表结构的xml
            String xmlName = DeConfig.outFileName + ".xml";
            DE_SERVICE.genXmlTableDDL(xmlName);
        }
    }

    static class GenSql {
        public static void main(String[] args) {
            // 输出表结构的SQL
            String sqlName = DeConfig.outFileName + ".sql";

            System.out.println("当前源数据库类型：" + DeConfig.dbType);
            // 创建键盘输入扫描器
            Scanner scanner = new Scanner(System.in);
            // 1. 输入目标数据库类型
            System.out.println("请输入目标数据库类型（1=MySql，0=Oracle）：");
            String dbInput = scanner.nextLine().trim();
            DbType targetDb = DbType.MySql;
            // 校验并转换数据库类型输入
            if ("0".equals(dbInput)) {
                targetDb = DbType.Oracle;
            }
            // 2. 输入是否生成删表语句
            System.out.println("请输入是否生成删表语句（1=是，0=否）：");
            String dropInput = scanner.nextLine().trim();
            boolean addDropSql = "1".equals(dropInput); // 仅输入1时为true，其余为false
            // 关闭扫描器
            scanner.close();

            DE_SERVICE.genSqlTableDDL(sqlName, targetDb, addDropSql);
        }
    }
}
