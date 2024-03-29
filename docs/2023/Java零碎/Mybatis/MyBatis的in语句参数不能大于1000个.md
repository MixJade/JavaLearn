# MyBatis的in语句参数不能大于1000个

> 2023年7月26日，前情提要，这里使用的MyBatis-Plus，但只出现了一个MyBatis_Plus的API

* 我收到一个Bug，说是一个人离职时删除其所有的帮派中的喽啰关系，报错了。

* 一看打印的日志，“列表中的最大表达式数为1000”，然后下面是执行的SQL，显示我删除喽啰关系时用的是IN语句，补充说明：IN后面的括号最多接受1000个参数，这个我也是现在才知道。
* 然后就开始各种解决问题，手段越来越粗暴，主要围绕着分割列表进行展开。

## 一、SQL注入式分割参数

我找到了下面的SQL，那位作者对于myBatis的foreach与SQL的语法可以说是炉火纯青了。

```xml
<!--删除超过1000条的数据-->
<delete id="removeThousandGangThug">
    DELETE GangThug WHERE thug_id IN
    <foreach collection="thugIDList" item="thugID" index="index" open="(" close=")" separator=",">
        <if test="(index % 500) == 499">
            '-' ) OR thug_id IN (
        </if>
        #{thugID}
    </foreach>
</delete>
```

你猜怎么着，我等了至少十分钟，然后报错。

```text
### Cause: java.sql.SQLSyntaxErrorException: ORA-01745: 无效的主机/绑定变量名;

bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-01745: 无效的主机/绑定变量名
```

我去网上找了一下，发现：

```text
1.在mybatis中的mapping映射，SQL语句中忘记加逗号，且逗号处有换行。

2.当数据量过大时，清洁的SQL语句长度太长，同样会导致这个异常。
```

怎么绘世呢？这个人有那么多的喽啰关系？

然后我去查了一下，顺便验证一下上面的SQL语法

> 下面的【131】是待删除人的客户号，【42】是一个不相干的人
>
> 最终结果是**89374**，如果只有【131】，则是**80782**

~~什么人能够加那么多的帮派？~~

```sql
SELECT
	COUNT( thug_id ) 
FROM
	GangThug 
WHERE
	client_id IN ('131','42','-')
```

* **注意：在逗号后面加上`'-'`，可以让结尾的逗号不报错，同时也不影响结果**
* ***但这个`'-'`好像一个颜文字啊^_^***

## 二、套娃列表分割参数

我怀疑是上面的那条**在执行时分割参数的SQL**边循环边计算导致的SQL语句过长，又或者是在不知名的地方导致SQL错误。

我使用套娃列表对参数进行分割。

先是Service层，直接接收那八万个参数，对其进行分割，变成`List<List<String>>`。

```java
@Override
@Transactional(rollbackFor = Exception.class)
public void removeBatchGangThug(List<String> thugIDList) {
    if (thugIDList.size() > 999) {
            // 计算分成多少帮派
            int gangSize = 998;
            int numGang = thugIDList.size() / gangSize + 1;
            log.info("这个人加入太多帮派了，每组998个能分成{}组", numGang);
            // 开始分割
            List<List<String>> resList = new ArrayList<>(numGang);
            for (int i = 0; i < numGang; i++) {
                resList.add(thugIDList.subList(i * gangSize, Math.min((i + 1) * gangSize, thugIDList.size())));
            }
            baseMapper.removeThousandGangThug(resList);
        } else {
            removeByIds(thugIDList);
        }
}
```

然后Mapper接口：

```java
/**
 * 解决删除的喽啰关系超过一千时报错
 *
 * @param idGangList 喽啰关系的主键列表
 * @return: void
 */
void removeThousandGangThug(@Param("idGangList") List<List<String>> idGangList);
```

然后是SQL:

```xml
<!--删除超过1000条的数据-->
<delete id="removeThousandGangThug">
    DELETE GangThug WHERE
    <foreach collection="idGangList" item="idGang" separator="OR">
        thug_id IN
        <foreach collection="idGang" item="thugID" open="(" close=")" separator=",">
            #{thugID}
        </foreach>
    </foreach>
</delete>
```

* 我又等了十分钟，并且打开了MyBatisPlus的SQL日志。
* 我就见识到了什么叫**控制台下雨**，满屏幕的问号与逗号（MyBatisPlus的SQL占位符，这些符号一个对应一个参数），一路向上划，划到顶了要再等一会，等上一页加载完了继续向上划，一路都是望不到头的问号与逗号，当然最后还是没有划到头，但场面极其震撼。
* 放弃查看SQL日志有多长的想法，直接将滚动条拉到最下方，发现还是熟悉的报错。

```text
### Cause: java.sql.SQLSyntaxErrorException: ORA-01745: 无效的主机/绑定变量名;
```

## 三、简单粗暴的循环删除

* 我听说，解决这种情况最好是建一张临时表，然后遍历这张临时表进行删除，但是这个地方不好用。
* 因为这些待删除的喽啰关系主键是要通过MQ下发给消费端的，并且下发的交易码【删除喽啰关系】是和别人共用的，不太好改。或者我可以新增一个交易码？但这样得写一个文档，很麻烦。
* 我可以试试一个简单粗暴的手段，就是把SQL放在循环体里，循环请求数据库。

**下面是Service实现层方法**

在循环体中调用MyBatisPlus的批量删除API，虽说不推荐在循环体中操作数据库，但只是不推荐而已。

```java
/**
 * 解决删除的喽啰关系超过一千时报错
 *
 * @param thugIDList 喽啰关系的主键列表
 * @return: void
 */
@Override
@Transactional(rollbackFor = Exception.class)
public void removeBatchGangThug(List<String> thugIDList) {
    if (thugIDList.size() > 999) {
        // 计算分成多少帮派
        int gangSize = 998;
        int numGang = thugIDList.size() / gangSize + 1;
        // 开始分割
        for (int i = 0; i < numGang; i++) {
            removeByIds(thugIDList.subList(i * gangSize, Math.min((i + 1) * gangSize, thugIDList.size())));
        }
    } else {
        removeByIds(thugIDList);
    }
}
```

**然后终于成功了！！！**

果然花里胡哨的操作只能增加出错概率。

## 四、更加简单的直指源头

我使用循环体删除8万条数据的做法被人说这样做不好，然后我想了好久，终于想到一个更简单的办法。那就是这些数据是谁产生的，就去处理谁，只要这些数据不出现，就可以避免参数过多。

以下是我的思路：

* 当下之患，不过是数据过多，只要在数据不多时进行处理，让这大量数据不出现就行。
* 那就是这些数据怎么产生的，就把产生的那个源头处理掉。
* 比如现在这个人离职，是先从前端接收客户号，然后通过客户号查出喽啰关系的主键，接着通过这8万条主键，去从喽啰关系列表中删除。
* 也就是说，只要我跳过**“通过客户号查喽啰关系主键”**，就可以避免见到这大量数据。
* 最后结果是什么呢？以上流程被简化成一条SQL:`「DELETE GangThug WHERE client_id=#{client_id}」`
* 没错，就是这么简单，当然这得新增一个交易码，最后还是逃不掉写文档。