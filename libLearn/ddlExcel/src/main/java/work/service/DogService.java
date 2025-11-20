package work.service;

import org.apache.ibatis.session.SqlSession;
import work.enums.DbType;
import work.mapper.DogMapper;
import work.model.dto.SheetDo;
import work.model.dto.TabXmlDo;
import work.model.entity.TableDDL;
import work.model.entity.TableName;
import work.utils.ExcelGen;
import work.utils.SqlUtil;
import work.utils.TableXmlGen;

import java.util.ArrayList;
import java.util.List;


public class DogService {

    /**
     * 输出表的结构为xlsx
     *
     * @param dbType     数据库类型
     * @param needOutTab 需要输出的表名
     * @param xlsxName   输出xlsx的文件名
     */
    public void genXlsxTableDDL(DbType dbType, List<TableName> needOutTab, String xlsxName) {
        SqlSession session = SqlUtil.getFactory(dbType).openSession();
        DogMapper dogMapper = session.getMapper(DogMapper.class);
        // 第一个sheet页开头：表名目录
        String[] tableHeader = {"序号", "表名", "注释"};
        List<String[]> tableListData = new ArrayList<>();
        int tableNum = 0;
        // 各表字段的sheet页开头
        String[] header = {"序号", "字段名", "注释", "字段类型及精度", "是否主键", "是否非空"};
        // 生成的sheet页数据
        List<SheetDo> sheetDoList = new ArrayList<>();
        for (TableName tabN : needOutTab) {
            // 表名称
            TableName tableName = dogMapper.queryTableName(tabN.tableName());
            // 表字段
            List<TableDDL> ddlList = dogMapper.queryTableDDL(tabN.tableName());

            // 填入Sheet
            String comments = tableName.comments();
            if (comments == null || "".equals(comments)) {
                comments = tabN.comments();
            }
            String sheetName = tableName.tableName() + "(" + comments + ")";
            // 记录在表目录
            tableNum++;
            tableListData.add(new String[]{String.valueOf(tableNum), tableName.tableName(), comments});
            // 开始生成表字段
            List<String[]> ddlData = new ArrayList<>();
            for (TableDDL od : ddlList) {
                ddlData.add(new String[]{od.columnId(), od.columnName(), od.comments(), od.dataType(), od.isPri(), od.isNotNull()}); //写入第一行
            }
            sheetDoList.add(new SheetDo(sheetName, header, ddlData));
        }
        session.close();

        // 插入表目录在开头
        sheetDoList.add(0, new SheetDo("表名目录", tableHeader, tableListData));

        // 生成xlsx
        ExcelGen.creatExcel(xlsxName, sheetDoList);
    }

    /**
     * 输出表的结构为xml
     *
     * @param dbType     数据库类型
     * @param needOutTab 需要输出的表名
     * @param xmlName    输出xml的文件名
     */
    public void genXmlTableDDL(DbType dbType, List<TableName> needOutTab, String xmlName) {
        SqlSession session = SqlUtil.getFactory(dbType).openSession();
        DogMapper dogMapper = session.getMapper(DogMapper.class);

        List<TabXmlDo> tabXmlDoList = new ArrayList<>();
        for (TableName tabN : needOutTab) {
            // 表名称
            TableName tableName = dogMapper.queryTableName(tabN.tableName());
            // 表字段
            List<TableDDL> ddlList = dogMapper.queryTableDDL(tabN.tableName());

            // xml传参
            if (tableName.comments() == null || tableName.comments().isEmpty()) {
                tableName = tabN; // 若查出来的表注释为空,使用传参的注释
            }
            tabXmlDoList.add(new TabXmlDo(tableName, ddlList));
        }
        session.close();

        // 生成xml
        TableXmlGen.creatXml(tabXmlDoList, xmlName);
    }
}
