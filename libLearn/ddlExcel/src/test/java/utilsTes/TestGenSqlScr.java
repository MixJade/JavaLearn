package utilsTes;

import org.junit.Test;
import work.model.dto.TabXmlDo;
import work.model.entity.TableDDL;
import work.model.entity.TableName;
import work.utils.GenSqlScr;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试关于SQL文件的生成
 *
 * @since 2025-12-03 17:20:27
 */
public class TestGenSqlScr {

    @Test
    public void testOracleToMysql() {
        // 构建测试数据（Oracle类型）
        List<TableDDL> ddlList = new ArrayList<>();
        ddlList.add(new TableDDL("1", "ID", "NUMBER(19)", "主键ID", "Y", "Y", null));
        ddlList.add(new TableDDL("2", "USER_NAME", "VARCHAR2(50)", "用户姓名", "N", "Y", null));
        ddlList.add(new TableDDL("3", "AGE", "NUMBER(3)", "年龄", "N", "N", "12"));
        ddlList.add(new TableDDL("4", "BALANCE", "NUMBER(10,2)", "账户余额", "N", "N", null));
        ddlList.add(new TableDDL("5", "CREATE_TIME", "DATE", "创建时间", "N", "Y", "SYSDATE"));
        ddlList.add(new TableDDL("6", "REMARK", "CLOB", "备注信息", "N", "N", null));

        TabXmlDo tabXmlDo = new TabXmlDo(new TableName("T_USER_INFO", "表注释"), ddlList);
        // 生成建表语句
        List<TabXmlDo> tabXmlDos = new ArrayList<>();
        tabXmlDos.add(tabXmlDo);
        tabXmlDos.add(tabXmlDo);
        GenSqlScr.tranOracleToMySql(tabXmlDos, "测试Oracle到MySql建表语句.sql", false);
    }


    @Test
    public void testMysqlToOracle() {
        // 构建测试数据（MySQL类型）
        List<TableDDL> ddlList = new ArrayList<>();
        ddlList.add(new TableDDL("1", "ID", "BIGINT", "主键ID", "Y", "Y", null));
        ddlList.add(new TableDDL("2", "USER_NAME", "VARCHAR(50)", "用户姓名", "N", "Y", null));
        ddlList.add(new TableDDL("3", "AGE", "INT", "年龄", "N", "N", "12"));
        ddlList.add(new TableDDL("4", "BALANCE", "DECIMAL(10,2)", "账户余额", "N", "N", null));
        ddlList.add(new TableDDL("5", "CREATE_TIME", "DATETIME", "创建时间", "N", "Y", "CURRENT_TIMESTAMP"));
        ddlList.add(new TableDDL("6", "REMARK", "TEXT", "备注信息", "N", "N", null));

        TabXmlDo tabXmlDo = new TabXmlDo(new TableName("t_user_info", "用户信息表"), ddlList);
        List<TabXmlDo> tabXmlDos = new ArrayList<>();
        tabXmlDos.add(tabXmlDo);
        // 生成Oracle建表语句
        GenSqlScr.tranMysqlToOracle(tabXmlDos, "测试MySql到Oracle建表语句.sql", true);
    }
}
