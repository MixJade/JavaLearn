package work.utils;

import work.enums.DbType;
import work.enums.JType;
import work.model.dto.CodeCol;
import work.model.dto.CodeTab;
import work.model.entity.OriTabCol;
import work.model.entity.TableName;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

/**
 * 代码生成器
 *
 * @since 2025-12-18 17:00:56
 */
public final class GenCodeUtil {
    /**
     * 将数据库字段转为Java字段
     */
    public static CodeTab convertCodeCol(DbType dbType, TableName tableName, List<OriTabCol> oriTabCols) {
        // 引入的包装类路径
        Set<JType> jTypeSet = new HashSet<>();
        // 将数据库字段转为Java字段
        List<CodeCol> codeColList = new ArrayList<>();
        for (OriTabCol oriTabCol : oriTabCols) {
            JType jType = convertJavaCol(dbType, oriTabCol);
            jTypeSet.add(jType);
            codeColList.add(new CodeCol(
                    ConvertCase.snakeToSCamel(oriTabCol.columnName()),
                    oriTabCol.columnName(),
                    oriTabCol.comments(),
                    jType.tpNm,
                    oriTabCol.isPri()
            ));
        }
        List<String> pkgList = new ArrayList<>();
        for (JType jType : jTypeSet) {
            if (!"".equals(jType.pkg)) pkgList.add(jType.pkg);
        }
        // 组装表参数
        String lJNm = ConvertCase.snakeToLCamel(tableName.tableName());
        String sJNm = ConvertCase.snakeToSCamel(tableName.tableName());
        return new CodeTab(lJNm, sJNm, tableName, pkgList, codeColList);
    }

    private static JType convertJavaCol(DbType dbType, OriTabCol oriTabCol) {
        // 统一转为大写，避免大小写问题（如varchar和VARCHAR统一处理）
        String colType = oriTabCol.dataType().toUpperCase().trim();
        return switch (dbType) {
            case MySql -> convertMysqlType(colType);
            case Oracle -> convertOracleType(colType, oriTabCol.numPre(), oriTabCol.numSca());
        };
    }

    /**
     * MySQL字段类型转换
     */
    private static JType convertMysqlType(String pureType) {
        return switch (pureType) {
            // 整数类型
            case "INT", "INTEGER" -> JType.INT;
            case "BIGINT" -> JType.LONG;
            // 布尔类型
            case "TINYINT", "BOOLEAN" -> JType.BOOL;
            // 浮点/高精度类型
            case "FLOAT", "DOUBLE" -> JType.DOUBLE;
            case "DECIMAL", "NUMERIC" -> JType.DECIMAL;
            // 字符串类型
            case "VARCHAR", "CHAR", "TEXT", "LONGTEXT", "MEDIUMTEXT", "TINYTEXT" -> JType.STR;
            // 日期时间类型
            case "DATE" -> JType.DATE;
            case "TIME" -> JType.TIME;
            case "DATETIME", "TIMESTAMP" -> JType.DATE_TIME;
            // 二进制类型
            case "BLOB", "LONGBLOB", "MEDIUMBLOB", "TINYBLOB" -> JType.BYTE;
            // 兜底：未匹配的类型统一返回String
            default -> JType.STR;
        };
    }

    /**
     * Oracle字段类型转换（需处理NUMBER的参数）
     */
    private static JType convertOracleType(String pureType, Integer numPre, Integer numSca) {
        return switch (pureType) {
            // 数字类型（重点处理NUMBER的不同长度）
            case "NUMBER" -> handleOracleNumberType(numPre, numSca);
            // 字符串类型
            case "VARCHAR2", "CHAR", "NVARCHAR2", "CLOB", "NCLOB" -> JType.STR;
            // 日期时间类型
            case "DATE", "TIMESTAMP" -> JType.DATE_TIME;
            // 布尔类型（Oracle 12c+支持）
            case "BOOLEAN" -> JType.BOOL;
            // 二进制类型
            case "BLOB" -> JType.BYTE;
            // 兜底：未匹配的类型统一返回String
            default -> JType.STR;
        };
    }

    /**
     * 处理Oracle NUMBER类型的细分映射
     *
     * <ul>
     *     <li>10,0 → Integer</li>
     *     <li>20,0 → Long</li>
     *     <li>10,2 → BigDecimal</li>
     * </ul>
     */
    private static JType handleOracleNumberType(Integer numPre, Integer numSca) {
        // 处理有小数位的情况（如NUMBER(10,2)）→ 直接用BigDecimal
        if (numSca != null && numSca > 0) {
            return JType.DECIMAL;
        }
        // 处理无小数位的情况（如NUMBER(10)）
        if (numPre != null && numPre > 0) {
            if (numPre <= 9) {
                return JType.INT; // 精度≤9 → Integer
            } else if (numPre <= 18) {
                return JType.LONG; // 精度≤18 → Long
            } else {
                return JType.DECIMAL; // 超长长整型 → BigDecimal
            }
        }
        // 无参数的NUMBER（默认精度）→ BigDecimal
        return JType.DECIMAL;
    }

    /**
     * 生成代码文件
     */
    public static void genCodeFile(String outDir, String author, String pack, boolean isNormal, List<CodeTab> codeTabs) {
        // 1. 准备模板数据
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("date", LocalDate.now().toString());
        dataModel.put("author", author);
        dataModel.put("pack", pack);
        String rootDir = "生成结果";
        String useTemp = isNormal ? "normal/" : "swagger/";
        for (CodeTab codeTab : codeTabs) {
            dataModel.put("tab", codeTab);
            String serviceName = codeTab.lJNm() + "Service";
            if (isNormal) serviceName = "I" + serviceName;
            dataModel.put("serviceName", serviceName);
            // 加载模板并渲染, 返回生成的代码,输出为文件
            System.out.println("=".repeat(15) + " 开始生成 " + codeTab.lJNm() + " " + "=".repeat(15));
            // 生成controller
            StrToFile.toFilePath(
                    FtlUtil.fillTempStr(dataModel, useTemp + "controller.java.ftl"),
                    Path.of(rootDir, outDir, "controller", String.format("%sController.java", codeTab.lJNm()))
            );
            // 生成实体类
            StrToFile.toFilePath(
                    FtlUtil.fillTempStr(dataModel, useTemp + "entity.java.ftl"),
                    Path.of(rootDir, outDir, "model", "entity", String.format("%s.java", codeTab.lJNm()))
            );
            // 生成Ts实体类
            StrToFile.toFilePath(
                    FtlUtil.fillTempStr(dataModel, useTemp + "tsEntity.ts.ftl"),
                    Path.of(rootDir, outDir, "tsEntity", String.format("%s.ts", codeTab.lJNm()))
            );
            // 生成Service
            StrToFile.toFilePath(
                    FtlUtil.fillTempStr(dataModel, useTemp + "service.java.ftl"),
                    Path.of(rootDir, outDir, "service", String.format("%s.java", serviceName))
            );
            // 生成ServiceImpl
            StrToFile.toFilePath(
                    FtlUtil.fillTempStr(dataModel, useTemp + "serviceImpl.java.ftl"),
                    Path.of(rootDir, outDir, "service", "impl", String.format("%sServiceImpl.java", codeTab.lJNm()))
            );
        }
    }
}
