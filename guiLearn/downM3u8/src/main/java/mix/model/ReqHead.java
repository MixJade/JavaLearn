package mix.model;

/**
 * 从配置文件中读取的请求头配置
 *
 * @param key   键
 * @param value 值
 */
public record ReqHead(String key, String value) {
}
