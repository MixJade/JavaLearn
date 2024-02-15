# mybatis的xml模糊查询

* 我们应该使用bind标签，来拼接模糊查询的字符串。
* （拼接后的名字可以与原名相同）
* 注意：xml里的%要加上单引号表示这是字符串

```xml
    <!--分页查询-->
    <select id="selectPetPage" resultType="com.ship.model.dto.PetDto">
        SELECT
        pet_id,pet_name
        FROM pet
        WHERE is_del = 0
        <if test="petName!=null and petName!=''">
            <bind name="petName" value="'%'+petName+'%'"/>
            and pet_name like #{petName}
        </if>
    </select>
```