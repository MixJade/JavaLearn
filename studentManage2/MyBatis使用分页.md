# MyBatis使用分页

## 一、引入依赖

```xml
<!--PageHelper：MyBatis的分页插件-->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.3.1</version>
</dependency>
```

## 二、mybatisConfig.xml中引入插件

```xml
<plugins>
    <!-- 分页插件PageHelper -->
    <plugin interceptor="com.github.pagehelper.PageInterceptor">
        <!-- 分页参数合理化,当页码数<0时,显示第一页.当页码数>总页码数时,显示最后一页-->
        <property name="reasonable" value="true"/>
        <!-- 指定使用MySQL的分页语法-->
        <property name="helperDialect" value="mysql"/>
    </plugin>
</plugins>
```

## 三、在代码中使用

* 官方样例

```text
//第二种，Mapper接口方式的调用，推荐这种使用方式。
PageHelper.startPage(1, 10);
List<Country> list = countryMapper.selectIf(1);
```

* 我的使用

```text
public static PageInfo<StudentsMessage> selectPage(String studentName, Integer societyId, int pageNum, int pageSize) {
    // 开始查询
    SqlSession session = SqlUtil.getFactory().openSession();
    StudentsMapper mapper = session.getMapper(StudentsMapper.class);

    // 使用PageHelper.startPage对即将到来的下次查询进行分页
    PageHelper.startPage(pageNum, pageSize);
    // 这里的查询不需要加分页逻辑，
    // 且返回值是使用PageHelper的Page对象(继承自ArrayList，如果转化Json，只有查出来的list)
    Page<StudentsMessage> studentsMessages = mapper.selectByPage(studentName, societyId);
    // 将返回的Page对象封装成PageInfo对象，这样序列化就有页码、最大数据条数了
    PageInfo<StudentsMessage> pageInfo = new PageInfo<>(studentsMessages);

    session.close();
    return pageInfo;
}
```

## 四、返回的PageInfo对象

```json
{
  "endRow": 5,
  "hasNextPage": true,
  "hasPreviousPage": false,
  "isFirstPage": true,
  "isLastPage": false,
  "list": [
    {
      "birthday": "2021-02-01",
      "englishGrade": 80,
      "height": 1.98,
      "id": 1,
      "mathGrade": 100,
      "money": 500000,
      "sex": "男",
      "societyName": "散人",
      "studentName": "张三"
    },
    {
      "birthday": "1970-01-19",
      "englishGrade": 50,
      "height": 1.88,
      "id": 19,
      "mathGrade": 40,
      "money": 50000,
      "sex": "男",
      "societyName": "散人",
      "studentName": "洪七"
    },
    {
      "birthday": "1970-02-19",
      "englishGrade": 70,
      "height": 1.9,
      "id": 20,
      "mathGrade": 70,
      "money": 50,
      "sex": "男",
      "societyName": "散人",
      "studentName": "洪七公"
    },
    {
      "birthday": "1987-02-21",
      "englishGrade": 50,
      "height": 1.65,
      "id": 2,
      "mathGrade": 40,
      "money": 8000,
      "sex": "女",
      "societyName": "头文字D",
      "studentName": "夏树"
    },
    {
      "birthday": "1986-12-30",
      "englishGrade": 60,
      "height": 1.75,
      "id": 3,
      "mathGrade": 60,
      "money": 5000,
      "sex": "男",
      "societyName": "头文字D",
      "studentName": "拓海"
    }
  ],
  "navigateFirstPage": 1,
  "navigateLastPage": 4,
  "navigatePages": 8,
  "navigatepageNums": [
    1,
    2,
    3,
    4
  ],
  "nextPage": 2,
  "pageNum": 1,
  "pageSize": 5,
  "pages": 4,
  "prePage": 0,
  "size": 5,
  "startRow": 1,
  "total": 20
}
```

