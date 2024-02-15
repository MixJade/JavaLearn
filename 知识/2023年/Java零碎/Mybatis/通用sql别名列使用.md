# 通用sql别名列使用

```xml
 <!-- 别名查询结果列 -->
<sql id="Alias_Column_List"> 
   <if test="true"/>
   ${alias}.GROUP_ID,
   ${alias}.GROUP_NM,
   ${alias}.GROUP_TP
</sql>
```

在使用的时候指定对应的字段与替换的名字

```xml
<select id="queryTest" resultMap="BaseResultMap">
    SELECT
    <include refid="Alias_Column_List">
        <property name="alias" value="ANS"/>
    </include>
    FROM
    AndNiSi AS ANS
    <where>
        <if test="jsonParam.name != null and jsonParam.name != '' ">
            <bind name="patternNm" value="'%'+jsonParam.name+'%'"/>
            and ANS.Name like #{patternNm}
        </if>
    </where>
    ORDER BY ANS.CREATE_TIME desc
</select>
```