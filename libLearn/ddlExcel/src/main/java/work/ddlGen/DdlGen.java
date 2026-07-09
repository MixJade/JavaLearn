package work.ddlGen;

import work.enums.DbType;
import work.model.dto.TabXmlDo;

import java.util.List;

/**
 * DDL（建表语句）生成器接口
 * <p>
 * 各数据库实现此接口，生成对应方言的 CREATE TABLE 语句。
 * 新增数据库类型时，只需实现此接口并注册到 {@link DdlGenFactory}。
 *
 * @since 2026-07-09
 */
public interface DdlGen {

    /**
     * 生成建表语句并写入文件
     *
     * @param tabXmlDos  生成所需表字段参数
     * @param sqlName    生成的sql文件名称
     * @param sourceDb   源数据库类型
     * @param addDropSql 是否添加删表语句
     */
    void generate(List<TabXmlDo> tabXmlDos, String sqlName, DbType sourceDb, boolean addDropSql);
}
