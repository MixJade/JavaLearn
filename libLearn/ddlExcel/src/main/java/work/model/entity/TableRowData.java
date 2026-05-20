package work.model.entity;

/**
 * 表数据行中的单个字段信息
 *
 * @param fieldName 字段名（与数据库中一致）
 * @param fieldType 字段数据库类型（大写，如 VARCHAR、DATE、NUMBER 等）
 * @param value     字段值（可为 null）
 */
public record TableRowData(String fieldName, String fieldType, Object value) {
}
