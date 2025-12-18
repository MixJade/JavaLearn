package work.model.dto;

/**
 * 代码生成器，字段模板参数
 *
 * @param jNm     Java字段名称
 * @param colNm   数据库中字段名
 * @param comment 字段注释
 * @param type    Java字段类型
 * @param keyFlag 是否主键
 */
public record CodeCol(String jNm, String colNm, String comment, String type, boolean keyFlag) {
}