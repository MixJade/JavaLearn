package work.utils;

import work.ddlGen.DdlGen;
import work.enums.DbType;
import work.model.entity.TableRowData;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SQL值格式化工具类（通用逻辑，与具体数据库无关）
 * <p>
 * 提供INSERT语句中字段值的通用格式化逻辑：
 * <ul>
 *     <li>NULL / 数字 / 字符串 的通用处理</li>
 *     <li>日期/时间类型判断与字符串转换</li>
 *     <li>日期值的具体SQL格式委托给 {@link DdlGen#formatDateValue}（数据库定制部分）</li>
 * </ul>
 *
 * @since 2026-07-18
 */
public final class SqlValUtil {

    /** 日期时间格式 yyyy-MM-dd HH:mm:ss */
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /** 日期格式 yyyy-MM-dd */
    private static final DateTimeFormatter D_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private SqlValUtil() {
    }

    /**
     * 通用值格式化：将Java对象格式化为SQL字面量
     * <p>
     * 日期/时间类型委托给目标数据库的 {@link DdlGen#formatDateValue} 处理；
     * 其余类型（NULL/数字/字符串）使用通用逻辑。
     *
     * @param val       字段值
     * @param dataType  字段的数据库类型（如 DATE、VARCHAR2、NUMBER 等）
     * @param sourceDb  源数据库类型
     * @param targetGen 目标数据库的INSERT生成器（用于日期格式化委托）
     * @return SQL字面量字符串
     */
    private static String formatValue(Object val, String dataType, DbType sourceDb, DdlGen targetGen) {
        if (val == null) {
            return "NULL";
        }

        String strVal = val.toString().trim();
        String typeUpper = dataType == null ? "" : dataType.trim().toUpperCase();

        // -------- 日期/时间类型：委托给目标数据库的定制方法 --------
        if (isDateTimeType(typeUpper)) {
            return targetGen.formatDateValue(val, strVal, typeUpper, sourceDb);
        }

        // -------- 数字类型：纯数字字符串不加引号 --------
        if (isNumericType(typeUpper)) {
            if (strVal.matches("^-?\\d+(\\.\\d+)?$")) {
                return strVal;
            }
        }

        // Java数字类型直接返回（无需引号）
        if (val instanceof Number) {
            return strVal;
        }

        // -------- 字符串类型：转义单引号 --------
        return "'" + strVal.replace("'", "''") + "'";
    }

    /**
     * 构建单行VALUES的括号内容，如 ('张三', 18, NULL, TO_DATE(...))
     */
    public static String buildRowValues(List<TableRowData> row, DbType sourceDb, DdlGen targetGen) {
        String values = row.stream()
                .map(rd -> formatValue(rd.value(), rd.fieldType(), sourceDb, targetGen))
                .collect(Collectors.joining(", "));
        return "(" + values + ")";
    }

    // ====== 类型判断辅助方法 ======

    /**
     * 判断是否为日期/时间类型
     */
    private static boolean isDateTimeType(String dataType) {
        if (dataType == null || dataType.isBlank()) return false;
        return dataType.startsWith("DATE") || dataType.startsWith("TIMESTAMP")
                || dataType.equals("DATETIME") || dataType.equals("TIME");
    }

    /**
     * 判断是否为数字类型
     */
    private static boolean isNumericType(String dataType) {
        if (dataType == null || dataType.isBlank()) return false;
        return dataType.startsWith("NUMBER") || dataType.startsWith("INT") || dataType.startsWith("BIGINT")
                || dataType.startsWith("DECIMAL") || dataType.startsWith("NUMERIC")
                || dataType.startsWith("FLOAT") || dataType.startsWith("DOUBLE")
                || dataType.startsWith("TINYINT") || dataType.startsWith("SMALLINT");
    }

    // ====== 日期转换辅助方法 ======

    /**
     * 转为日期时间字符串 "yyyy-MM-dd HH:mm:ss"
     * <p>
     * 用于Oracle DATE/TIMESTAMP（包含时分秒）或 DATETIME/TIMESTAMP 类型。
     * LocalDate 会被补零为当天 00:00:00。
     */
    public static String toDateTimeStr(Object val, String strVal) {
        if (val instanceof LocalDateTime ldt) {
            return ldt.format(DT_FMT);
        }
        if (val instanceof LocalDate ld) {
            return ld.atStartOfDay().format(DT_FMT);
        }
        if (val instanceof Timestamp ts) {
            return ts.toLocalDateTime().format(DT_FMT);
        }
        if (val instanceof Date d) {
            return new Timestamp(d.getTime()).toLocalDateTime().format(DT_FMT);
        }
        return normalizeDateStr(strVal);
    }

    /**
     * 转为日期或日期时间字符串
     * <p>
     * LocalDate → "yyyy-MM-dd"（纯日期，适用于MySQL/PG的DATE类型）<br>
     * 其他 → "yyyy-MM-dd HH:mm:ss"（含时间）
     */
    public static String toDateOrDateTimeStr(Object val, String strVal) {
        if (val instanceof LocalDate ld) {
            return ld.format(D_FMT);
        }
        if (val instanceof LocalDateTime ldt) {
            return ldt.format(DT_FMT);
        }
        if (val instanceof Timestamp ts) {
            return ts.toLocalDateTime().format(DT_FMT);
        }
        if (val instanceof Date d) {
            return new Timestamp(d.getTime()).toLocalDateTime().format(DT_FMT);
        }
        return normalizeDateStr(strVal);
    }

    /**
     * 将字符串日期标准化为指定格式（容错处理）
     * <p>
     * 支持 Oracle/MySQL/PostgreSQL JDBC 常见输出格式：
     * <ul>
     *     <li>"2025-01-01 00:00:00.0" → "2025-01-01 00:00:00"</li>
     *     <li>"2025-01-01 00:00:00" → 原样返回</li>
     *     <li>"2025-01-01" → "2025-01-01 00:00:00"</li>
     * </ul>
     */
    private static String normalizeDateStr(String strVal) {
        if (strVal == null || strVal.isBlank()) return strVal;
        // 去掉末尾 ".0" 或 ".xxx"（JDBC 附加的纳秒部分）
        String cleaned = strVal.replaceAll("\\.\\d+$", "").trim();
        try {
            if (cleaned.length() == 10) {
                LocalDate ld = LocalDate.parse(cleaned, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return ld.atStartOfDay().format(DT_FMT);
            }
            LocalDateTime ldt = LocalDateTime.parse(cleaned, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return ldt.format(DT_FMT);
        } catch (Exception e) {
            return cleaned;
        }
    }
}
