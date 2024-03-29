# MP分页插件使用

* mybatis-plus的分页插件可以放在自己定义的mapper参数中，只能放第一个参数。放完以后默认使用mp自带的分页
* 但这样返回值就是MP的封装Page了

**就像这样：mapper接口**

```java
IPage<info> queryWhenAdd(Page<info> page,@Param("info") Info info);
```
**而xml文件中则不需要再定义分页语句了**

```xml
<select id="queryWhenAdd" resultMap="BaseResultMap">
    SELECT * FROM ANS
    ORDER BY ANS.CREATE_TIME desc
</select>
```