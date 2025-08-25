package work.model.entity;

/**
 * 表的字段详情
 *
 * @param columnId   列排序
 * @param columnName 列名
 * @param dataType   数据类型
 * @param comments   注释
 * @param isPri      是否主键
 * @param isNotNull  是否非空
 */
public record TableDDL(String columnId, String columnName, String dataType, String comments, String isPri,
                       String isNotNull) {
}
