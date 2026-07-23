package com.chat.ima.resp;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * IMA API 统一响应体
 *
 * @param code 状态码，0 表示成功
 * @param msg  状态信息
 * @param data 业务数据（JsonNode，可为 null）
 * @param request_id 请求id（无用）
 */
public record ImaResp(int code, String msg, JsonNode data, String request_id) {
}
