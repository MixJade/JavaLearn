# Mybatis学习笔记

1. Mybatis是一个Maven和sql的框架，存在目的是为了解决
    1. 原生模式的硬编码（加载对应接口后，可以以方法形式操作Sql）
    2. 简化后期执行SQL（主要是可以以包的形式来管理SQL语句）

## 使用Mybatis

1. **定义与SQL映射文件(mapper)同名的接口，并将之放在同一目录下。**

   注意：
    * 虽然直接拖文件夹也可以，但是不规范；
    * 同时也可以直接放在`main/java`和`main/resources`文件夹下，但如果后期文件多了会不好管理。

   推荐在`main/resources`文件夹下面建立一个新的文件夹，命名与mapper接口所在文件夹同名。

   > 例如接口导包路径为`example.mappe`r（在main/java文件夹下面的那个)，在`resources`新建文件夹时，命名`example/mapper`

2. **设置SQL映射文件的namespace属性为mapper接口的全限定名。**

   如上所说，namespace="example.mapper.StudentsMapper"

3. **在Config.xml文件中引用映射文件。**
   mapper resource="attempt02/StudentsMapper.xml"
   > 如果接口与Mapper同名且位于同一文件夹下，可以用导包语句。
   > 将mapper resource换成package name,后面的url换成example.mapper（只到文件夹）

4. **通过session.getMapper方法获取接口对象，然后调用对应方法完成SQL执行即可。**

## 推荐插件下载:MyBatisX

这个插件可以让你快捷的在mapper文件与接口之间跳转，同时会标记出mapper文件和接口，以及mapper文件中SQL语句的部分。
并且可以先在接口写方法，然后通过插件跳转过去会在mapper文件中自动补全。
~~（还是想说标记长得真可爱）~~

## 若数据库的字段与Java相异

如数据库中字段`student_name`到了Java中变成了`studentName`，会导致相应无法传到定义文件(StudentsMessage.java)中。

**解决方法**：

1. **在查询的时候给字段起别名。**
   > 缺点是代码会变得非常难读
    ```xml
    <select id="selectAll" resultType="attempt01.StudentsMessage">
    select 
        id,student_Name as studentName,english_Grade as englishGrade 
    from 
        students s1,societys s2 
    where 
        s1.societyId=s2.societyId
    </select>
    ```
2. **通过SQL片段来给列起别名，然后查询的时候引用。**
   > 第一种的升级版，缺点是不太灵活
    ```xml
    <sql id="students_column">
        id,student_Name as studentName,english_Grade as englishGrade
    </sql>
    <select id="selectAll" resultType="attempt01.StudentsMessage">
        select
            <include refid="students_column"/>
        from students s1,societys s2 
        where s1.societyId=s2.societyId
    </select>
    ```
3. **通过resultMap来给相应列起别名**
   > （推荐，比较灵活，只需定义不一致的列名即可）
   > 注意：这个方法中，resultMap的type就是原select的resultType属性，同时将select中的resultType改成相应的resultMap。

   ~~查出来的列名和map中对应不上的话，它会报错~~
    ```xml
    <resultMap id="studentsResultMap" type="attempt01.StudentsMessage">
    <result column="english_Grade" property="englishGrade"/>
    </resultMap>
    <select id="selectAll" resultMap="studentsResultMap">
        select 
            * 
        from 
            students s1,societys s2 
        where 
            s1.societyId=s2.societyId
    </select>
    ```

## 单条件查询

1. **单个条件查询。**
   只需要将JDBC中的`?`换成xml文件中的`#{条件参数}`即可。
   值得注意的是，在xml文件中，小于号`<`要进行转义成xml的小于号`&lt;`。或者使用转义字符`<![CDATA[内容]]>`

2. **参数占位符**
   两种参数占位符：
    1. **#{条件参数}**：执行SQL语句时会自动换成`?`，用来设置参数值。
    2. **${条件参数}**：单纯的SQL语句拼接，存在SQL语句注入问题，但是表名，列名进行设置，只能用它

3. **参数类型**(可以不加)
   *parameterType="int"*
    ```xml
    <select 
    id="selectByTwoSixty"
    parameterType="int" 
    resultType="attempt03.RankShow">
    ```

4. **模糊查询**
   比如用`like`来替换`=`号，后面参数是`%洪七%`意思是所有名字里面带着`洪七`两个字的。

|  通配符   |    描述     |               示例               |
|:------:|:---------:|:------------------------------:|
|   %    | 包含任意数量字符串 |       `ab%c`  以ab开头,c结尾        |
|   _    |  任何单个字符   |      `abc_` 以abc开头的任意四个字符      |
| regexp |   正则表达式   | 正常的正则表达式, 不过前面的`like`变成`rlike` |

## 多条件查询

> 多条件查询有三种方式

1. **散列函数**
   跟单个查询不同的地方在于，接口中要用 `@Param("xml中的条件参数")` 定义
    ```java
    List<RankShow> selectByTwoSixty(
        @Param("englishGrade") int englishGrade,
        @Param("mathGrade") int mathGrade
        );
    ```

2. **实体类封装参数**
   > SQL中的参数名要跟实体类属性名一样

   通过实体类对参数进行封装，注意，接口中定义的参数要用实体类对象

    ```xml
        <select id="selectDimObject" resultType="attempt03.RankShow">
            select
            id,
            studentName,
            englishGrade,
            mathGrade
            from students s1
            <!-- 新知识：like的意思是模糊查询-->
            where s1.studentName like #{studentName}
            and s1.englishGrade>=#{englishGrade}
            and s1.mathGrade>=#{mathGrade}
            order by englishGrade desc,mathGrade desc;
        </select>
    ```
   接口文件：
    ```java
    List<RankShow> selectDimObject(RankShow rankShow);
    ```
   实现用法见`MybatisFourthDemo.java`
   
3. **Map封装参数**
   > SQL中的参数名要跟map中的键一样

   通过HashMap封装参数，逻辑跟实体类封装参数差不多，但记得映射文件、接口、实现的java文件，都要改。

## 动态查询（条件缺失）

**当条件参数为空**(这里就显示出包装类的重要性了，平常的int根本不能设置为null)时。

* 可以通过`<if>`标签和`<where>`标签来设置条件参数为空的操作(见映射文件中的`id="selectLack"`的操作）。
* 关于`where`标签，其实如果不用where标签，若第一个条件语句因为`if`判定被取消了，会变成 `where and engilshGrade>=60`
  ，这样会报错，解决方法是通过在where后面加1=1来避免，但是有更好的方法，就是用`<where>`标签
* 注意在`SixthDemo.java`文件中加了判别名字为空时的`if`语句，因为不加的话会被认为查询一个名字叫`%%`的人。

## 动态查询（条件选择）

> 感觉跟上面那个没有区别的样子

将java中的语句与之对应:
参考映射文件中的`id="selectChoice"`以及JAVA文件`SeventhDemo.java`的操作

|   SQL映射   | JAVA语法  |
|:---------:|:-------:|
|  choose   | switch  |
|   when    |  case   |
| otherwise | default |

## 数据操作

### 插入操作

见`EighthDemo.java`

* 插入，返回0或1，
* 可以设置返回主键值，
* 事务

### 修改操作

> 静态修改，动态修改。举一反三罢了

见`TenthDemo.java`

* 返回受影响的行数
* 不会返回主键值

### 删除操作

* **单个删除**，很正常，记得提交事务就是
* **批量删除**，通过数组的形式，这要在接口中通过`@Param`重写默认数组名`array`。同时还有新的映射文件语法，见`id="deleteGroup"`

## 散列参数占位

> Mybatis通过ParamNameResolver类来进行参数传递

* 散装参数会封装成map集合，map有默认键名`arg0`,`arg1`,（对应参数1、2)，`param1`，`param2`（前面的另外一种表现形式）。可以通过`@param`注解来在接口中进行替换。
* `collention、list、array`都会封装成`map`集合，例如

|               -                |                  -                   |
|:------------------------------:|:------------------------------------:|
| `map.put("arg0",collection集合)` | `map.put("collection",collection集合)` |
|    `map.put("arg0",list集合)`    |       `map.put("list",list集合)`       |
|   `map.put("arg0",array集合)`    |      `map.put("array",array集合)`      |

* 其他参数传递方式例如对象、map，你已经懂了

## 注解开发

只需要在接口中通过注解进行定义SQL语句即可，不需要通过映射文件，这种方法更加直接，但是如果SQL语句较为复杂的话会非常难看。

```java
    @Select(
        "select * from 
        students s1, societys s2
        where
        s1.societyId = s2.societyId
        and s1.id =#{id};")
        List<StudentsMessage>selectByExplain(int id);
```

## Xml中大于小于号

xml文件中是不识别`<`,`>`,会将xml本身的元素命名搞混，得出无法解析。

| `&lt;` | <    | 小于号 |
| ---- | ---- | ------ |
| `&gt;` | >    | 大于号 |

样例如下：

```xml
<select id="selectByTwoSixty" resultType="backyard.RankShow">
    select studentName,
           englishGrade
    from students
    <!--这是小于号-->
    where englishGrade &lt;= #{englishGrade}
    order by englishGrade desc;
</select>
```

