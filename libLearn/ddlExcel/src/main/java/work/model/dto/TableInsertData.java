package work.model.dto;

import work.model.entity.TableName;
import work.model.entity.TableRowData;

import java.util.List;

/**
 * 表的数据导出信息（用于INSERT语句生成）
 *
 * @param tableName 表名及其中文注释
 * @param rows      该表的所有数据行，每行为有序的字段列表
 */
public record TableInsertData(TableName tableName, List<List<TableRowData>> rows) {
}
