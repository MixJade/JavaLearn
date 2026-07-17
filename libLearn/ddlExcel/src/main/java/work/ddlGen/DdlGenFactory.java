package work.ddlGen;

import work.enums.DbType;

import java.util.Map;

/**
 * DDL 生成器工厂
 * <p>
 * 通过目标 DbType 获取对应的 DdlGenerator 实例。
 * 新增数据库类型时，在此注册对应的 DdlGenerator 即可。
 *
 * @since 2026-07-09
 */
public final class DdlGenFactory {
    private static final Map<DbType, DdlGen> GENERATORS = Map.of(
            DbType.MySql, new MySqlDdlGen(),
            DbType.Oracle, new OracleDdlGen(),
            DbType.PostgreSql, new PostgreSqlDdlGen()
    );

    private static final Map<DbType, TypeConvert> CONVERTERS = Map.of(
            DbType.MySql, new MySqlDdlGen(),
            DbType.Oracle, new OracleDdlGen(),
            DbType.PostgreSql, new PostgreSqlDdlGen()
    );

    private DdlGenFactory() {
    }

    /**
     * 根据目标数据库类型获取对应的 DDL 生成器
     *
     * @param targetDb 目标数据库类型
     * @return DDL 生成器实例
     * @throws IllegalArgumentException 如果该数据库类型尚未支持
     */
    public static DdlGen get(DbType targetDb) {
        DdlGen generator = GENERATORS.get(targetDb);
        if (generator == null) {
            throw new IllegalArgumentException("未支持的数据库类型：" + targetDb);
        }
        return generator;
    }


    /**
     * 根据数据库类型获取对应的类型转换器
     *
     * @param dbType 数据库类型
     * @return 类型转换器实例
     * @throws IllegalArgumentException 如果该数据库类型尚未支持
     */
    public static TypeConvert getType(DbType dbType) {
        TypeConvert converter = CONVERTERS.get(dbType);
        if (converter == null) {
            throw new IllegalArgumentException("未支持的数据库类型：" + dbType);
        }
        return converter;
    }
}
