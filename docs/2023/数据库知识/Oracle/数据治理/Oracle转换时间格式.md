# Oracle转换时间格式


## 治理已有时间格式

> 2024-09-12 09:42:42

背景：在Oracle中，有一张表有两个数据格式：`20230710 16:15:59`与`26-5月 -23`这两种，

如何将这两种格式统一转换成`YYYY-MM-DD HH24:MI:SS`这种格式

### 一、转换1

1. `LENGTH(PUSH_TIME) > 15`是为了防止两种格式混在一起，筛选时间字符串长度大于15的，即`20240123 16:43:02`这种格式的时间，从而排除`26-5月 -23`这种格式的。

```sql
-- 当前时间格式：20240123 16:43:02
SELECT TO_CHAR(TO_DATE(PUSH_TIME, 'YYYYMMDD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS')
FROM MY_TRAN_TABLE
WHERE PUSH_TIME IS NOT NULL
  AND LENGTH(PUSH_TIME) > 15;
  
-- 正式转换
UPDATE MY_TRAN_TABLE SET PUSH_TIME = TO_CHAR(TO_DATE(PUSH_TIME, 'YYYYMMDD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS')
WHERE PUSH_TIME IS NOT NULL
  AND LENGTH(PUSH_TIME) > 15;
```

### 二、转换2

* 将`26-5月 -23`这种格式转换，需要先去掉里面的中文才行。
* `LENGTH(PUSH_TIME) < 10`同样是防止两种格式混合，特别是当上一个转换执行之后，防止改变其结果。

```sql
-- 当前时间格式：26-5月 -23
SELECT TO_CHAR(TO_DATE(REPLACE('26-5月-23', '月', ''), 'DD-MM -YY'), 'YYYY-MM-DD HH24:MI:SS')
FROM DUAL;
-- 当前时间格式：26-11月-23
SELECT TO_CHAR(TO_DATE(REPLACE('26-11月-23', '月', ''), 'DD-MM -YY'), 'YYYY-MM-DD HH24:MI:SS')
FROM DUAL;

-- 当前时间格式：26-5月 -23
SELECT TO_CHAR(TO_DATE(REPLACE(PUSH_TIME, '月', ''), 'DD-MM -YY'), 'YYYY-MM-DD HH24:MI:SS')
FROM MY_TRAN_TABLE
WHERE PUSH_TIME IS NOT NULL
  AND LENGTH(PUSH_TIME) < 10;
  
-- 正式转换
-- 当前时间格式：26-5月 -23
UPDATE MY_TRAN_TABLE SET PUSH_TIME = TO_CHAR(TO_DATE(REPLACE(PUSH_TIME, '月', ''), 'DD-MM -YY'), 'YYYY-MM-DD HH24:MI:SS')
WHERE PUSH_TIME IS NOT NULL
  AND LENGTH(PUSH_TIME) < 10;
```

## 插入时临时转换格式

> 2026-01-28 16:22:28

* 插入数据的时间格式不对，会报错`ORA-01861: 文字与格式字符串不匹配`

```sql
ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD HH24:MI:SS';
```

核心原因是插入的日期时间字符串（'2025-12-01 14:35:16'）和数据库表中字段的日期格式不匹配。

* **错误原因分析**

Oracle 默认的日期格式通常是 `DD-MON-RR`（例如 `01-DEC-25`），而插入的是 `YYYY-MM-DD HH24:MI:SS` 格式的字符串，数据库无法直接识别，因此抛出 “文字与格式字符串不匹配” 的错误。

### 一、使用TO_DATE
你需要显式指定日期字符串的格式，让 Oracle 能够正确解析。以下是修复后的完整 INSERT 语句：

```sql
INSERT INTO xxxxx (
    xxxx, 
    CREATE_TIME
) VALUES (
    'xxxx', 
    TO_DATE('2025-12-01 14:35:16', 'YYYY-MM-DD HH24:MI:SS')  -- 显式指定日期格式
);
```

### 二、修改临时会话日期格式
如果不想每次都写 TO_DATE，可以临时修改会话的日期格式（仅对当前会话有效）：
```sql
ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD HH24:MI:SS';
-- 之后直接插入字符串即可
INSERT INTO sys_menus_info (...) VALUES (...,'2025-12-01 14:35:16',...);
```

