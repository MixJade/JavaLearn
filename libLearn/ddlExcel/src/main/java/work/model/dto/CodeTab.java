package work.model.dto;

import work.model.entity.TableName;

import java.util.List;

/**
 * 代码生成器，表模板参数
 *
 * @param lJNm     代码实体类名称
 * @param sJNm     代码实体名称(小驼峰)
 * @param tb       表名+注释
 * @param pkgList  引用的类路径
 * @param codeCols 字段模板参数
 */
public record CodeTab(String lJNm, String sJNm, TableName tb, List<String> pkgList, List<CodeCol> codeCols) {
}