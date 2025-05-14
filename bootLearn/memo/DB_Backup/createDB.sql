-- 查询数据库是否存在
SELECT SCHEMA_NAME
FROM INFORMATION_SCHEMA.SCHEMATA
WHERE SCHEMA_NAME = 'memo';

-- 根据查询结果决定是否创建数据库
CREATE DATABASE IF NOT EXISTS memo
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

-- 验证数据库创建结果
SELECT '数据库创建成功！' AS result;