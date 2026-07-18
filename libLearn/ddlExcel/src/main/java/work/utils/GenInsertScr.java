package work.utils;

import work.ddlGen.DdlGen;
import work.ddlGen.DdlGenFactory;
import work.enums.DbType;
import work.model.dto.TableInsertData;
import work.model.entity.TableName;
import work.model.entity.TableRowData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * INSERT语句生成工具（调度器 + 统一生成逻辑）
 * <p>
 * 本类负责遍历表数据并统一生成 INSERT 语句。
 * 日期值的格式化仍委托给 {@link DdlGen#formatDateValue}。
 *
 * @since 2026-05-20
 */
public final class GenInsertScr {

    /**
     * 将数据导出为INSERT语句
     *
     * @param tableDataList 各表的数据结构列表
     * @param sqlName       输出的sql文件名
     * @param sourceDb      源数据库类型
     * @param targetDb      目标数据库类型
     */
    public static void genInsertSql(List<TableInsertData> tableDataList, String sqlName, DbType sourceDb, DbType targetDb) {
        System.out.printf("%n开始生成INSERT语句，从【%s】到【%s】%n", sourceDb, targetDb);
        DdlGen targetGen = DdlGenFactory.get(targetDb);
        StringBuilder result = new StringBuilder();

        for (TableInsertData tabData : tableDataList) {
            TableName tableNameObj = tabData.tableName();
            String rawTableName = tableNameObj.tableName().trim();
            String comment = tableNameObj.comments();
            result.append(buildInsertSql(rawTableName, comment, tabData.rows(), sourceDb, targetGen));
        }

        StrToFile.toFile(result.toString(), sqlName);
    }

    /**
     * 生成单张表的INSERT语句（统一逻辑）
     * <p>
     * 根据 {@code targetGen.insertStyle()} 选择批量或单条INSERT格式，
     * 表名/字段名的大小写也由风格决定。日期值格式化委托给 {@link DdlGen#formatDateValue}。
     *
     * @param tableName    表名（原始大小写）
     * @param tableComment 表注释
     * @param dataRows     数据行
     * @param sourceDb     源数据库类型
     * @param targetGen    目标数据库方言生成器
     * @return INSERT语句字符串（含表注释行）
     */
    static String buildInsertSql(String tableName, String tableComment,
                                 List<List<TableRowData>> dataRows, DbType sourceDb, DdlGen targetGen) {
        StringBuilder sb = new StringBuilder();

        // 表头注释（批量/单条通用）
        sb.append("\n-- ").append(tableName);
        if (tableComment != null && !tableComment.isBlank()) {
            sb.append("  ").append(tableComment.trim());
        }
        sb.append("\n");

        if (dataRows.isEmpty()) {
            sb.append("-- 表 ").append(tableName).append(" 无数据\n");
            return sb.toString();
        }

        // 标识符大小写：BATCH → 小写，SINGLE → 大写
        String displayTable = targetGen.insertBatch() ? tableName.toLowerCase() : tableName.toUpperCase();
        List<TableRowData> firstRow = dataRows.get(0);
        String cols = firstRow.stream()
                .map(rd -> targetGen.insertBatch() ? rd.fieldName().toLowerCase() : rd.fieldName().toUpperCase())
                .collect(Collectors.joining(", "));

        if (targetGen.insertBatch()) {
            // 批量INSERT：INSERT INTO table (cols) VALUES (...), (...), ...;
            String valuesPart = dataRows.stream()
                    .map(row -> SqlValUtil.buildRowValues(row, sourceDb, targetGen))
                    .collect(Collectors.joining(",\n       "));
            sb.append(String.format("INSERT INTO %s (%s) VALUES\n       %s;\n", displayTable, cols, valuesPart));
        } else {
            // 单条INSERT：每行一条 INSERT INTO TABLE (COLS) VALUES (...);
            for (List<TableRowData> row : dataRows) {
                String values = SqlValUtil.buildRowValues(row, sourceDb, targetGen);
                sb.append(String.format("INSERT INTO %s (%s) VALUES %s;\n", displayTable, cols, values));
            }
        }
        return sb.toString();
    }
}
