package work;

import work.service.DeService;
import work.service.DeServiceImpl;

import java.util.Scanner;

/**
 * ddlExcel的主要类
 *
 * @author MixJade
 * @since 2025-07-19
 */
public class DeMain {
    private static final DeService DE_SERVICE = new DeServiceImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=============================");
        System.out.println("  请选择要执行的操作：");
        System.out.println("  1. 输出表结构的 Excel");
        System.out.println("  2. 输出表结构的 XML");
        System.out.println("  3. 输出表结构的 SQL（可转数据库）");
        System.out.println("  4. 代码生成器");
        System.out.println("  0. 退出");
        System.out.println("=============================");
        System.out.print("请输入选项: ");
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1" -> DE_SERVICE.genXlsxTableDDL();
            case "2" -> DE_SERVICE.genXmlTableDDL();
            case "3" -> DE_SERVICE.genSqlTableDDL();
            case "4" -> DE_SERVICE.genCodeTableDDL();
            default -> System.out.println("已退出，拜拜~");
        }
        System.out.println();
    }
}
