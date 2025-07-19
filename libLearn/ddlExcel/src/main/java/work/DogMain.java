package work;

import work.enums.DbType;
import work.service.DogService;

/**
 * 狗工作的主要类，只在这里做区别配置
 *
 * @author MixJade
 * @since 2025-07-19
 */
public class DogMain {
    private final DogService dogService = new DogService();

    /**
     * 生成oracle中的表结构DDL
     *
     * @since 2025-07-19 12:38:36
     */
    private void genOracleDDL() {
        String[] needOutTab = {
                "BASE_CELL_INFO",
                "BASE_COMPT_CELL_REL",
                "BASE_COMPT_INFO",
                "BASE_INVEST_TRGT",
                "BASE_PRODUCT_STRATEGY",
                "BASE_PROD_COMPT_REL",
                "BASE_SCALE_COM_PRO",
                "BASE_TP_INFO",
                "PRODUCT_COOP_REL",
                "PROD_DEF_VERSION",
                "PROD_PLAN",
                "PROD_PRODUCT_INFO",
                "CMB_FAILED_NUMBER",
                "CMB_SELECT_NUMBER",
        };
        dogService.genTableDDL(DbType.Oracle, needOutTab, "产品表结构.xlsx");
    }

    /**
     * 生成mysql中的表结构DDL(测试用)
     *
     * @since 2025-07-19 21:12:23
     */
    private void genMySqlDDL() {
        String[] needOutTab = {
                "students",
                "societys",
                "dog",
        };
        dogService.genTableDDL(DbType.MySQL, needOutTab, "PLAY数据库.xlsx");
    }

    public static void main(String[] args) {
        DogMain dogMain = new DogMain();
        // dogMain.genOracleDDL();
        dogMain.genMySqlDDL();
    }
}
