package work;

import work.enums.DbType;
import work.service.DogService;
import work.service.DogServiceImpl;

/**
 * 狗工作的主要类，只在这里做区别配置
 *
 * @author MixJade
 * @since 2025-07-19
 */
public class DogMain {
    private static final DogService dogService = new DogServiceImpl();

    static class GenXlsx {
        public static void main(String[] args) {
            // 输出表结构的excel
            String xlsxName = DogConfig.outFileName + ".xlsx";
            dogService.genXlsxTableDDL(xlsxName);
        }
    }

    static class GenXml {
        public static void main(String[] args) {
            // 输出表结构的xml
            String xmlName = DogConfig.outFileName + ".xml";
            dogService.genXmlTableDDL(xmlName);
        }
    }

    static class GenSql {
        public static void main(String[] args) {
            // 输出表结构的SQL(目前只有oracle转mysql)
            String sqlName = DogConfig.outFileName + ".sql";
            // 目标数据库的格式
            DbType targetDb = DbType.MySQL;
            dogService.genSqlTableDDL(sqlName, targetDb);
        }
    }
}
