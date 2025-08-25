package work.model.dto;

import work.model.entity.TableDDL;
import work.model.entity.TableName;

import java.util.List;

/**
 * 生成xml时参数
 *
 * @param tableName    表的名称+注释
 * @param tableDDLList 表下的各字段
 */
public record TabXmlDo(TableName tableName, List<TableDDL> tableDDLList) {
}
