package work.ddlGen;

/**
 * 数据库字段类型转换器
 *
 * @since 2026-07-09
 */
public interface TypeConvert {

    /**
     * 将源数据库的原生类型转换为中间类型（MySQL类型）
     *
     * @param nativeType 源数据库的原生类型字符串，如 "VARCHAR2(200)"、"NUMBER(10,2)"
     * @return 中间类型字符串（MySQL类型），如 "varchar(200)"、"decimal(10,2)"
     */
    String toMiddleType(String nativeType);

    /**
     * 将中间类型（MySQL类型）转换为本数据库的原生类型
     *
     * @param middleType 中间类型字符串（MySQL类型），如 "varchar(200)"、"int"
     * @return 本数据库的原生类型字符串，如 "VARCHAR2(200)"、"NUMBER(10)"
     */
    String fromMiddleType(String middleType);
}
