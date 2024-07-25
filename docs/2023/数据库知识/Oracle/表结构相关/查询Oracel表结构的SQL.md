# 查询Oracel表结构的SQL

> 2023-11-28

## 一、原版（精度有误）

* NVARCHAR2的精度会莫名乘2
* 且NUMBER的精度会算上小数点

```sql
SELECT
    cols.column_name AS "字段名称",
    cols.data_type || '(' || cols.data_length || ')' AS "字段类型及精度",
    comm.comments AS "字段注释",
    CASE WHEN pk.column_name IS NOT NULL THEN 'Y' END AS "是否为主键",
    CASE WHEN cols.nullable = 'N' THEN 'Y' END AS "是否非空"
FROM
    user_tab_columns cols
        LEFT JOIN
    user_col_comments comm
    ON
                cols.table_name = comm.table_name AND cols.column_name = comm.column_name
        LEFT JOIN
    (SELECT cols.table_name, cols.column_name
     FROM user_constraints cons, user_cons_columns cols
     WHERE cons.constraint_type = 'P' AND cons.constraint_name = cols.constraint_name
       AND cons.owner = cols.owner) pk
    ON
                cols.table_name = pk.table_name AND cols.column_name = pk.column_name
WHERE
        cols.table_name = '所查表名'
ORDER BY
    cols.column_id;
```

## 二、改良版

* 改良要求

```text
请将这句SQL改一下：cols.data_type || '(' || cols.data_length || ')' AS "字段类型及精度",如果data_type为NVARCHAR2，则data_length的值要除以2；如果data_type为NUMBER，则data_length的值要换成cols.DATA_SCALE,cols.DATA_PRECISION，中间使用逗号分割（且，如果DATA_SCALE为零，则只展示DATA_PRECISION，不用中间的逗号）；其它情况不变
```

* 改良结果

```sql
SELECT
    cols.COLUMN_ID AS "序号",
    cols.column_name AS "字段名称",
    CASE
        WHEN cols.data_type = 'NVARCHAR2' THEN cols.data_type || '(' || cols.data_length/2 || ')'
        WHEN cols.data_type = 'NUMBER' THEN
            CASE
                WHEN cols.DATA_SCALE = 0 THEN cols.data_type || '(' || cols.DATA_PRECISION || ')'
                ELSE cols.data_type || '(' || cols.DATA_SCALE || ',' || cols.DATA_PRECISION || ')'
                END
        ELSE cols.data_type || '(' || cols.data_length || ')'
        END AS "字段类型及精度",
    comm.comments AS "字段注释",
    CASE WHEN pk.column_name IS NOT NULL THEN 'Y' END AS "是否为主键",
    CASE WHEN cols.nullable = 'N' THEN 'Y' END AS "是否非空"
FROM
    user_tab_columns cols
        LEFT JOIN
    user_col_comments comm
    ON
                cols.table_name = comm.table_name AND cols.column_name = comm.column_name
        LEFT JOIN
    (SELECT cols.table_name, cols.column_name
     FROM user_constraints cons, user_cons_columns cols
     WHERE cons.constraint_type = 'P' AND cons.constraint_name = cols.constraint_name
       AND cons.owner = cols.owner) pk
    ON
                cols.table_name = pk.table_name AND cols.column_name = pk.column_name
WHERE
        cols.table_name = '所查表名'  -- 表名
ORDER BY
    cols.column_id;
```

# MyBatisPlus查询表结构的SQL

> 2024年7月25日09:10:32

## 一、查询表信息

```sql
SELECT *
FROM ALL_TAB_COMMENTS
WHERE TABLE_NAME IN ('目标表表名');
```

## 二、查询表字段信息

```sql
SELECT A.COLUMN_NAME,
       CASE
           WHEN A.DATA_TYPE = 'NUMBER' THEN (CASE
                                                 WHEN A.DATA_PRECISION IS NULL THEN A.DATA_TYPE
                                                 WHEN NVL(A.DATA_SCALE, 0) > 0
                                                     THEN A.DATA_TYPE || '(' || A.DATA_PRECISION || ',' || A.DATA_SCALE || ')'
                                                 ELSE A.DATA_TYPE || '(' || A.DATA_PRECISION || ')' END)
           ELSE A.DATA_TYPE END       DATA_TYPE,
       B.COMMENTS,
       DECODE(C.POSITION, '1', 'PRI') KEY
FROM ALL_TAB_COLUMNS A
         INNER JOIN ALL_COL_COMMENTS B
                    ON A.TABLE_NAME = B.TABLE_NAME AND A.COLUMN_NAME = B.COLUMN_NAME
         LEFT JOIN ALL_CONSTRAINTS D
                   ON D.TABLE_NAME = A.TABLE_NAME AND D.CONSTRAINT_TYPE = 'P'
         LEFT JOIN ALL_CONS_COLUMNS C ON C.CONSTRAINT_NAME = D.CONSTRAINT_NAME AND C.COLUMN_NAME = A.COLUMN_NAME
WHERE A.TABLE_NAME = '目标表表名'
ORDER BY A.COLUMN_ID;
```
