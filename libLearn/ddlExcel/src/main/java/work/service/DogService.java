package work.service;

import work.enums.DbType;

public interface DogService {
    /**
     * 输出表的结构为xlsx
     *
     * @param xlsxName 输出xlsx的文件名
     */
    void genXlsxTableDDL(String xlsxName);

    /**
     * 输出表的结构为xml
     *
     * @param xmlName 输出xml的文件名
     */
    void genXmlTableDDL(String xmlName);

    /**
     * 输出表的结构为建表语句SQL
     *
     * @param sqlName  输出的文件名
     * @param targetDb 目标数据库类型
     */
    void genSqlTableDDL(String sqlName, DbType targetDb);
}
