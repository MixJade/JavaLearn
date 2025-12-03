package work.service;

import org.apache.ibatis.session.SqlSession;
import work.DogConfig;
import work.enums.DbType;
import work.mapper.DogMapper;
import work.model.dto.SheetDo;
import work.model.dto.TabXmlDo;
import work.model.entity.TableDDL;
import work.model.entity.TableName;
import work.utils.ExcelGen;
import work.utils.GenSqlScr;
import work.utils.MyBatisConfig;
import work.utils.TableXmlGen;

import java.util.ArrayList;
import java.util.List;


public class DogServiceImpl implements DogService {

    /**
     * 输出表的结构为xlsx
     *
     * @param xlsxName 输出xlsx的文件名
     */
    @Override
    public void genXlsxTableDDL(String xlsxName) {
        SqlSession session = MyBatisConfig.getFactory().openSession();
        DogMapper dogMapper = session.getMapper(DogMapper.class);
        // 第一个sheet页开头：表名目录
        String[] tableHeader = {"序号", "表名", "注释"};
        List<String[]> tableListData = new ArrayList<>();
        int tableNum = 0;
        // 各表字段的sheet页开头
        String[] header = {"序号", "字段名", "注释", "字段类型及精度", "是否主键", "是否非空", "默认值"};
        // 生成的sheet页数据
        List<SheetDo> sheetDoList = new ArrayList<>();
        for (TableName tabN : DogConfig.needOutTab) {
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
                ddlData.add(new String[]{od.columnId(), od.columnName(), od.comments(), od.dataType(), od.isPri(), od.isNotNull(), od.defaultVal()}); //写入第一行
            }
            sheetDoList.add(new SheetDo(sheetName, header, ddlData));
        }
        session.close();

        // 插入表目录在开头
        sheetDoList.add(0, new SheetDo("表名目录", tableHeader, tableListData));

        // 生成xlsx
        ExcelGen.creatExcel(xlsxName, sheetDoList);
    }

    // 从数据库中获取生成文件所需参数
    private List<TabXmlDo> getTabXmlDoList() {
        SqlSession session = MyBatisConfig.getFactory().openSession();
        DogMapper dogMapper = session.getMapper(DogMapper.class);

        List<TabXmlDo> tabXmlDoList = new ArrayList<>();
        for (TableName tabN : DogConfig.needOutTab) {
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

        return tabXmlDoList;
    }


    /**
     * 输出表的结构为xml
     *
     * @param xmlName 输出xml的文件名
     */
    @Override
    public void genXmlTableDDL(String xmlName) {
        List<TabXmlDo> tabXmlDoList = getTabXmlDoList();

        // 生成xml
        TableXmlGen.creatXml(tabXmlDoList, xmlName);
    }

    /**
     * 输出表的结构为建表语句SQL
     *
     * @param sqlName      输出的文件名
     * @param targetDb     目标数据库类型
     * @param addDropSql 是否添加删表语句
     */
    @Override
    public void genSqlTableDDL(String sqlName, DbType targetDb, boolean addDropSql) {
        List<TabXmlDo> tabXmlDoList = getTabXmlDoList();

        System.out.printf("\n开始生成建表语句，从【%s】到【%s】%n", DogConfig.dbType, targetDb);
        if (DogConfig.dbType == DbType.Oracle && targetDb == DbType.MySQL) {
            // 生成建表语句: Oracle转MySQL
            GenSqlScr.tranOracleToMySql(tabXmlDoList, sqlName, addDropSql);
        } else if (DogConfig.dbType == DbType.MySQL && targetDb == DbType.Oracle) {
            // 生成建表语句: MySQL转Oracle
            GenSqlScr.tranMysqlToOracle(tabXmlDoList, sqlName, addDropSql);
        }
    }
}
