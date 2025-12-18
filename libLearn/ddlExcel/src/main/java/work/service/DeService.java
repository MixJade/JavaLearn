package work.service;

public interface DeService {
    /**
     * 输出表的结构为xlsx
     */
    void genXlsxTableDDL();

    /**
     * 输出表的结构为xml
     */
    void genXmlTableDDL();

    /**
     * 输出表的结构为建表语句SQL
     */
    void genSqlTableDDL();
}
