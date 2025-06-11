package com.demo.model.baidu;

/**
 * @param refresh_token  忽略
 * @param expires_in     Access Token的有效期(秒为单位，有效期30天)
 * @param session_key    忽略
 * @param access_token   要获取的Access Token
 * @param scope          该token授权的api
 * @param session_secret 忽略
 */
public record AuthToken(String refresh_token, String expires_in, String session_key, String access_token, String scope,
                        String session_secret) {
}
