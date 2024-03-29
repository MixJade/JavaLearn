# 关于Oracle导出表

> 导出数据库中的表是一个学问

* 建议直接使用IDEA的导出。

## 样例

> 最终应该是下面这样

* **推荐使用IDEA的数据库工具**，点开要导出的表，然后点击上方栏的"DDL"，就能看到建表语句。
* **或者在IDEA的数据库菜单中选择SQL生成器**，生成建表语句（可以生成全部数据库的）
* 或者图方便，用Navicat转储SQL文件，但记得删掉一些不必要的东西。

```sql
-- ----------------------------
-- Table structure for 所导之表
-- ----------------------------
CREATE TABLE "所导之表"
(
    "主键" NVARCHAR2(32) VISIBLE NOT NULL,
    "用户编号"       NVARCHAR2(32) VISIBLE NOT NULL,
    "用户姓名"       NVARCHAR2(32) VISIBLE NOT NULL
);
COMMENT ON COLUMN "所导之表"."主键" IS '表主键ID';
COMMENT ON COLUMN "所导之表"."用户编号" IS '外键，来自用户表的用户编号';
COMMENT ON COLUMN "所导之表"."用户姓名" IS '用户姓名';

COMMENT ON TABLE "所导之表" IS '用于展示的表';

-- ----------------------------
-- Primary Key structure for table 所导之表
-- ----------------------------
ALTER TABLE "所导之表"
    ADD CONSTRAINT "SYS_C8888848" PRIMARY KEY ("主键");

-- ----------------------------
-- Checks structure for table 所导之表
-- ----------------------------
ALTER TABLE "所导之表"
    ADD CONSTRAINT "SYS_C8888849" CHECK ("主键" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "所导之表"
    ADD CONSTRAINT "SYS_C8888850" CHECK ("用户编号" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "所导之表"
    ADD CONSTRAINT "SYS_C8888851" CHECK ("用户姓名" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
```

## 错误示例一：导出了USER表空间

* Navicat导出表的时候，偶尔会在sql的中间加一个表空间的声明。
* 如果你把它导入到其他数据库，你会发现在那个数据库插入的时候，会提示你【对表空间’USERS‘无操作权限】
* 这个时候，只能把表删了，重新建一个。

```sql
-- 杂七杂八的建表语句

-- 下面就是USERS表空间，这个必须删除，不然在别的数据库插入数据时，会提示你无权插入
TABLESPACE "USERS"
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;

-- 杂七杂八的注释与添加列的语句
```

## 错误示例二：加了删除表的语句

* Navicat导出表的时候会在头上加一个删除已存在表的语句。
*  这个如果在其他数据库导入的时候会因为其他数据库没有那个表，然后就找不到删除对象，然后就会报错。
* 目前这种情况只限于Oracle，因为MySQL会在删除前判断表是否存在。

```sql
-- 下面这个删除表的语句不能加，不然会因为找不到表而报错。
-- 就是找到表了，直接删也是很危险的。
DROP TABLE "待删除的表名";

-- 乱七八糟的建表语句
CREATE TABLE "待删除的表名" (...)
```



## 一个建议：新增字段的语句

* 其实很多时候，都是因为表新增了字段，然后把表拿去同步。
* 这个时候可以把建表语句中的新增字段的语句单独拿出来
* 比如：

```sql
-- ----------------------------
-- Table structure for 待添加字段的表
-- ----------------------------
ALTER TABLE "待添加字段的表" ADD ("所添加字段" VARCHAR2(40 BYTE) VISIBLE );

COMMENT ON COLUMN "待添加字段的表"."所添加字段" IS '新的字段注释';
```
