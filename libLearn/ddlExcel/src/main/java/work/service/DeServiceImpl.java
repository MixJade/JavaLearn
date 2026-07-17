package work.service;

import org.apache.ibatis.session.SqlSession;
import work.DeConfig;
import work.enums.DbType;
import work.mapper.DeMapper;
import work.model.dto.CodeTab;
import work.model.dto.SheetDo;
import work.model.dto.TabXmlDo;
import work.model.entity.OriTabCol;
import work.model.entity.TableDDL;
import work.model.entity.TableName;
import work.model.dto.TableInsertData;
import work.model.entity.TableRowData;
import work.utils.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DeServiceImpl implements DeService {
    /**
     * 输出表的结构为xlsx
     */
    @Override
    public void genXlsxTableDDL() {
        // xlsxName 输出xlsx的文件名
        String xlsxName = DeConfig.outFileName + ".xlsx";
        // 逻辑正式开始
        SqlSession session = MyBatisConfig.getFactory().openSession();
        DeMapper deMapper = session.getMapper(DeMapper.class);
        // 第一个sheet页开头：表名目录
        String[] tableHeader = {"序号", "表名", "注释"};
        List<String[]> tableListData = new ArrayList<>();
        int tableNum = 0;
        // 各表字段的sheet页开头
        String[] header = {"序号", "字段名", "注释", "字段类型及精度", "是否主键", "是否非空", "默认值"};
        // 生成的sheet页数据
        List<SheetDo> sheetDoList = new ArrayList<>();
        for (TableName tabN : DeConfig.needOutTab) {
            // 表名称
            TableName tableName = deMapper.queryTableName(tabN.tableName());
            // 表字段
            List<TableDDL> ddlList = deMapper.queryTableDDL(tabN.tableName());

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
        DeMapper deMapper = session.getMapper(DeMapper.class);

        List<TabXmlDo> tabXmlDoList = new ArrayList<>();
        for (TableName tabN : DeConfig.needOutTab) {
            // 表名称
            TableName tableName = deMapper.queryTableName(tabN.tableName());
            // 表字段
            List<TableDDL> ddlList = deMapper.queryTableDDL(tabN.tableName());

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
     */
    @Override
    public void genXmlTableDDL() {
        List<TabXmlDo> tabXmlDoList = getTabXmlDoList();
        // xmlName 输出xml的文件名
        String xmlName = DeConfig.outFileName + ".xml";
        // 生成xml
        TableXmlGen.creatXml(tabXmlDoList, xmlName);
    }

    /**
     * 输出表的结构为建表语句SQL
     */
    @Override
    public void genSqlTableDDL() {
        // 输出表结构的SQL
        String sqlName = DeConfig.outFileName + ".sql";

        System.out.println("当前源数据库类型：" + DeConfig.dbType);
        // 创建键盘输入扫描器
        Scanner scanner = new Scanner(System.in);
        // 1. 输入目标数据库类型
        System.out.println("请输入目标数据库类型（1=MySql，2=PostgreSql，0=Oracle）：");
        String dbInput = scanner.nextLine().trim();
        DbType targetDb = switch (dbInput) {
            case "0" -> DbType.Oracle;
            case "2" -> DbType.PostgreSql;
            default -> DbType.MySql;
        };
        // 2. 输入是否生成删表语句
        System.out.println("请输入是否生成删表语句（1=是，0=否）：");
        String dropInput = scanner.nextLine().trim();
        boolean addDropSql = "1".equals(dropInput); // 仅输入1时为true，其余为false
        // 关闭扫描器
        scanner.close();

        List<TabXmlDo> tabXmlDoList = getTabXmlDoList();
        // 开始生成建表语句
        GenSqlScr.tranTabDDL(tabXmlDoList, sqlName, DeConfig.dbType, targetDb, addDropSql);
    }

    @Override
    public void genCodeTableDDL() {
        // 创建键盘输入扫描器
        Scanner scanner = new Scanner(System.in);
        // 输入生成类型
        System.out.println("请输入生成类型（1-normal 2-swagger）：");
        String inNormal = scanner.nextLine().trim();
        boolean isNormal = !"2".equals(inNormal); // 仅输入2时为false，其余为true
        // 关闭扫描器
        scanner.close();

        // 开始生成
        SqlSession session = MyBatisConfig.getFactory().openSession();
        DeMapper deMapper = session.getMapper(DeMapper.class);
        List<CodeTab> codeTabList = new ArrayList<>();
        for (TableName tabN : DeConfig.needOutTab) {
            // 表名称
            TableName tableName = deMapper.queryTableName(tabN.tableName());
            // 表字段
            List<OriTabCol> oriTabColList = deMapper.queryOriTabField(tabN.tableName());
            // xml传参
            if (tableName.comments() == null || tableName.comments().isEmpty()) {
                tableName = tabN; // 若查出来的表注释为空,使用传参的注释
            }
            // 组装参数
            codeTabList.add(GenCodeUtil.convertCodeCol(DeConfig.dbType, tableName, oriTabColList));
        }
        session.close();
        // 调用生成器方法
        GenCodeUtil.genCodeFile(DeConfig.outFileName, DeConfig.author, DeConfig.parentPack, isNormal, codeTabList);
    }

    /**
     * 将数据导出为INSERT语句
     */
    @Override
    public void genInsertSql() {
        // 输出文件名
        String sqlName = DeConfig.outFileName + "_insert.sql";

        System.out.println("当前源数据库类型：" + DeConfig.dbType);
        // 创建键盘输入扫描器
        Scanner scanner = new Scanner(System.in);
        // 1. 输入目标数据库类型
        System.out.println("请输入目标数据库类型（1=MySql，2=PostgreSql，0=Oracle）：");
        String dbInput = scanner.nextLine().trim();
        DbType targetDb = switch (dbInput) {
            case "0" -> DbType.Oracle;
            case "2" -> DbType.PostgreSql;
            default -> DbType.MySql;
        };
        // 关闭扫描器
        scanner.close();

        // 2. 复用 MyBatis 连接，通过 JDBC 直接查询数据（获取原生字段类型）
        SqlSession session = MyBatisConfig.getFactory().openSession();
        DeMapper deMapper = session.getMapper(DeMapper.class);
        Connection conn = session.getConnection();

        List<TableInsertData> tableDataList = new ArrayList<>();

        try {
            for (TableName tabN : DeConfig.needOutTab) {
                // 查询表名称（含注释）—— 仍走 MyBatis
                TableName tableName = deMapper.queryTableName(tabN.tableName());
                if (tableName == null || tableName.comments() == null || tableName.comments().isBlank()) {
                    tableName = tabN;
                }

                // 用 JDBC ResultSetMetaData 直接获取：列名 + 数据库原生类型 + 值
                List<List<TableRowData>> rows = JdbcQueryUtil.queryTableData(conn, tabN.tableName());

                tableDataList.add(new TableInsertData(tableName, rows));
            }
        } catch (SQLException e) {
            throw new RuntimeException("查询表数据失败：" + e.getMessage(), e);
        } finally {
            session.close();
        }

        // 3. 生成INSERT语句
        GenInsertScr.genInsertSql(tableDataList, sqlName, DeConfig.dbType, targetDb);
    }
}
