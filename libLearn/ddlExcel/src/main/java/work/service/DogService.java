package work.service;

import org.apache.ibatis.session.SqlSession;
import work.enums.DbType;
import work.mapper.DogMapper;
import work.model.entity.TableDDL;
import work.model.entity.TableName;
import work.model.excel.SheetDo;
import work.utils.ExcelGen;
import work.utils.SqlUtil;

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
    public void genTableDDL(DbType dbType, String[] needOutTab, String xlsxName) {
        SqlSession session = SqlUtil.getFactory(dbType).openSession();
        DogMapper dogMapper = session.getMapper(DogMapper.class);

        String[] header = {"序号", "字段名", "注释", "字段类型及精度", "是否主键", "是否非空"};
        List<SheetDo> sheetDoList = new ArrayList<>();
        for (String tabName : needOutTab) {
            // 表名称
            TableName tableName = dogMapper.queryTableName(tabName);
            // 表字段
            List<TableDDL> ddlList = dogMapper.queryTableDDL(tabName);

            // 填入Sheet
            String sheetName = tableName.getTableName() + "(" + tableName.getComments() + ")";
            List<String[]> ddlData = new ArrayList<>();
            for (TableDDL od : ddlList) {
                ddlData.add(new String[]{od.getColumnId(), od.getColumnName(), od.getComments(), od.getDataType(), od.getIsPri(), od.getIsNotNull()}); //写入第一行
            }
            sheetDoList.add(new SheetDo(sheetName, header, ddlData));
        }
        session.close();

        // 生成xlsx
        ExcelGen.creatExcel(xlsxName, sheetDoList);
    }
}
