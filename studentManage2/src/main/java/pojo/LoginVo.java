package pojo;

/**
 * 登录注册的请求参数
 *
 * @param nameJade     用户名
 * @param passwordJade 密码
 * @param remember     记住我
 */
public record LoginVo(String nameJade, String passwordJade, boolean remember) {
}
