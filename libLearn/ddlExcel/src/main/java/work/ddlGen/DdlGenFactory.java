package work.ddlGen;

import work.enums.DbType;

import java.util.Map;

/**
 * 数据库方言生成器工厂
 * <p>
 * 通过 DbType 获取对应的 {@link DdlGen} 实例。
 * {@link DdlGen} 是统一接口，包含 DDL 生成、类型转换、INSERT 生成全部能力。
 * 新增数据库类型时，在此注册对应的 DdlGen 实现即可。
 *
 * @since 2026-07-09
 */
public final class DdlGenFactory {

    private static final Map<DbType, DdlGen> GENERATORS = Map.of(
            DbType.MySql, new MySqlDdlGen(),
            DbType.Oracle, new OracleDdlGen(),
            DbType.PostgreSql, new PostgreSqlDdlGen()
    );

    private DdlGenFactory() {
    }

    /**
     * 根据数据库类型获取对应的方言生成器
     * <p>
     * 返回的实例同时具备 DDL 生成、类型转换、INSERT 生成能力。
     *
     * @param dbType 数据库类型（可作为源库或目标库）
     * @return 方言生成器实例
     * @throws IllegalArgumentException 如果该数据库类型尚未支持
     */
    public static DdlGen get(DbType dbType) {
        DdlGen generator = GENERATORS.get(dbType);
        if (generator == null) {
            throw new IllegalArgumentException("未支持的数据库类型：" + dbType);
        }
        return generator;
    }
}
