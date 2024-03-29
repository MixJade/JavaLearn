package pojo;

/**
 * 分页查询的请求参数
 *
 * @param studentName 学生姓名,模糊查询
 * @param societyId   社团id,模糊查询
 * @param pageNum     当前页码
 * @param pageSize    一页的条目数
 */
public record QueryStuVo(String studentName, Integer societyId, int pageNum, int pageSize) {
}
