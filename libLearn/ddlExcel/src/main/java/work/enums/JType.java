package work.enums;

/**
 * (代码生成器)Java字段类型
 */
public enum JType {
    // 基本类型包装类
    INT("Integer"),
    LONG("Long"),
    BOOL("Boolean"),
    DOUBLE("Double"),
    BYTE("byte[]"),

    // 字符串类型
    STR("String"),

    // 高精度小数
    DECIMAL("BigDecimal", "java.math.BigDecimal"),
    // 时间日期类型
    DATE("LocalDate", "java.time.LocalDate"),
    TIME("LocalTime", "java.time.LocalTime"),
    DATE_TIME("LocalDateTime", "java.time.LocalDateTime");

    // 字段类型名称
    public final String tpNm;
    // 类型所在的包路径（全类名）
    public final String pkg;

    JType(String tpNm, String pkg) {
        this.tpNm = tpNm;
        this.pkg = pkg;
    }

    JType(String tpNm) {
        this.tpNm = tpNm;
        this.pkg = "";
    }
}
