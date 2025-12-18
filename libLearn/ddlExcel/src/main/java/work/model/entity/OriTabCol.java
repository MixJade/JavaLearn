package work.model.entity;

/**
 * 表的原始字段详情
 *
 * @param columnName 列名
 * @param dataType   数据类型
 * @param numPre     (仅限数字类型)整数位
 * @param numSca     (仅限数字类型)小数位
 * @param comments   注释
 * @param isPri      是否主键
 */
public record OriTabCol(String columnName, String dataType, Integer numPre, Integer numSca, String comments,
                        boolean isPri) {
}
