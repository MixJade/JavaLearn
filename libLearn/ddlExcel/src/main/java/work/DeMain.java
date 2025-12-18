package work;

import work.service.DeService;
import work.service.DeServiceImpl;

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
            DE_SERVICE.genXlsxTableDDL();
        }
    }

    static class GenXml {
        public static void main(String[] args) {
            // 输出表结构的xml
            DE_SERVICE.genXmlTableDDL();
        }
    }

    static class GenSql {
        public static void main(String[] args) {
            // 输出表结构的SQL
            DE_SERVICE.genSqlTableDDL();
        }
    }

    static class GenCode {
        public static void main(String[] args) {
            // 代码生成器
            DE_SERVICE.genCodeTableDDL();
        }
    }
}
