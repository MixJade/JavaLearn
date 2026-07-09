package work.utils;

import work.ddlGen.DdlGenFactory;
import work.enums.DbType;
import work.model.dto.TabXmlDo;

import java.util.List;

/**
 * 建表语句生成工具
 *
 * @since 2025-11-26 09:13:12
 */
public final class GenSqlScr {

    /**
     * 使用数据库字段生成建表语句（支持不同数据库迁移）
     *
     * @param tabXmlDos  生成所需表字段参数
     * @param sqlName    生成的sql文件名称
     * @param sourceDb   源数据库类型
     * @param targetDb   目标数据库类型
     * @param addDropSql 是否添加删表语句
     */
    public static void tranTabDDL(List<TabXmlDo> tabXmlDos, String sqlName, DbType sourceDb, DbType targetDb, boolean addDropSql) {
        System.out.printf("\n开始生成建表语句，从【%s】到【%s】%n", sourceDb, targetDb);
        DdlGenFactory.get(targetDb)
                .generate(tabXmlDos, sqlName, sourceDb, addDropSql);
    }
}
