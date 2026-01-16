package mock;

/**
 * api配置
 *
 * @param path     请求上下文路径
 * @param jsonName 内容的json名称，或重定向的url(需http或https开头)
 */
public record ApiConf(String path, String jsonName) {
}
