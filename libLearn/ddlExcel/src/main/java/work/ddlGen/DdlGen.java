package work.ddlGen;

import work.enums.DbType;
import work.enums.JType;
import work.model.dto.TabXmlDo;
import work.model.entity.OriTabCol;
import work.utils.SqlValUtil;

import java.util.List;

/**
 * 数据库方言生成器（统一接口）
 * <p>
 * 集中管理各数据库的全部定制逻辑，包含三大职责：
 * <ul>
 *     <li><b>DDL 生成</b>：{@link #generate} 生成对应方言的 CREATE TABLE 语句</li>
 *     <li><b>类型转换</b>：{@link #toMiddleType} / {@link #fromMiddleType} 跨库类型互转（经 MySQL 中间类型），
 *     {@link #toJavaType} 字段类型 → Java 类型（用于代码生成）</li>
 *     <li><b>INSERT 生成</b>：声明 INSERT 风格（批量/单条），
 *     {@link #formatDateValue} 格式化日期字面量（默认字符串格式）</li>
 * </ul>
 * <p>
 * 新增数据库类型时，只需实现此接口并注册到 {@link DdlGenFactory}，无需修改任何工具类。
 * <p>
 * 默认实现提供 MySQL/PostgreSQL 风格的字符串日期格式；
 * Oracle 需覆写为单条 INSERT + TO_DATE 函数。
 *
 * @since 2026-07-09
 */
public interface DdlGen {

    // ==================== DDL 生成 ====================

    /**
     * 生成建表语句并写入文件
     *
     * @param tabXmlDos  生成所需表字段参数
     * @param sqlName    生成的sql文件名称
     * @param sourceDb   源数据库类型
     * @param addDropSql 是否添加删表语句
     */
    void generate(List<TabXmlDo> tabXmlDos, String sqlName, DbType sourceDb, boolean addDropSql);

    // ==================== 类型转换 ====================

    /**
     * 将源数据库的原生类型转换为中间类型（MySQL类型）
     *
     * @param nativeType 源数据库的原生类型字符串，如 "VARCHAR2(200)"、"NUMBER(10,2)"
     * @return 中间类型字符串（MySQL类型），如 "varchar(200)"、"decimal(10,2)"
     */
    String toMiddleType(String nativeType);

    /**
     * 将中间类型（MySQL类型）转换为本数据库的原生类型
     *
     * @param middleType 中间类型字符串（MySQL类型），如 "varchar(200)"、"int"
     * @return 本数据库的原生类型字符串，如 "VARCHAR2(200)"、"NUMBER(10)"
     */
    String fromMiddleType(String middleType);

    /**
     * 将本数据库的字段类型转换为Java类型（用于代码生成）
     *
     * @param oriTabCol 原始字段信息（含类型名、精度、小数位等）
     * @return Java类型枚举
     */
    JType toJavaType(OriTabCol oriTabCol);

    // ==================== INSERT 语句生成 ====================

    /**
     * 本数据库的INSERT语句风格是否为批量
     */
    boolean insertBatch();

    /**
     * 格式化日期/时间类型的值为SQL字面量
     * <p>
     * 默认实现：返回 'yyyy-MM-dd HH:mm:ss' 或 'yyyy-MM-dd' 格式字符串（MySQL/PostgreSQL风格）。
     * Oracle需覆写为 TO_DATE/TO_TIMESTAMP 函数。
     *
     * @param val       字段值（Java对象）
     * @param strVal    字段值的字符串形式
     * @param typeUpper 字段类型（大写）
     * @param sourceDb  源数据库类型
     * @return SQL日期字面量（含引号或函数调用）
     */
    default String formatDateValue(Object val, String strVal, String typeUpper, DbType sourceDb) {
        String dateStr;
        if (sourceDb == DbType.Oracle) {
            // Oracle的DATE/TIMESTAMP包含时分秒，统一用datetime格式
            dateStr = SqlValUtil.toDateTimeStr(val, strVal);
        } else {
            // MySQL/PostgreSQL：DATE是纯日期，DATETIME/TIMESTAMP含时间
            dateStr = SqlValUtil.toDateOrDateTimeStr(val, strVal);
        }
        return "'" + dateStr + "'";
    }
}
