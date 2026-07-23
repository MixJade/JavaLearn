package com.chat.ima;

import com.chat.ima.req.GetMediaInfoReq;
import com.chat.ima.req.SearchKnowledgeReq;
import com.chat.ima.resp.ImaResp;
import com.chat.pojo.Message;
import com.chat.pojo.UserVo;
import com.chat.utils.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * IMA 知识库全静态服务
 * <p>
 * 对接腾讯 IMA 知识库 OpenAPI，提供知识库搜索、内容浏览、URL 导入等能力。
 * 与 LlamaService 同模式：全静态方法、WebSocket / Controller 直接调用。
 * <p>
 */
@Component
public class ImaService {

    private static final Logger log = LoggerFactory.getLogger(ImaService.class);

    // ===================== 静态配置 =====================
    private static String imaApiKey;
    private static String imaClientId;
    private static String imaKbId;

    /**
     * IMA 对话开关（全局共享、多线程可见）
     * 通过前端按钮手动切换，与 LlamaService.llamaAlive 同模式
     */
    private static final AtomicBoolean imaAlive = new AtomicBoolean(false);

    // ===================== 静态全局对象 =====================
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://ima.qq.com";

    // ===================== Spring 注入静态字段 =====================
    @Value("${ima.api-key:}")
    public void setStaticApiKey(String key) {
        imaApiKey = key;
    }

    @Value("${ima.client-id:}")
    public void setStaticClientId(String id) {
        imaClientId = id;
    }

    @Value("${ima.kb-id:}")
    public void setStaticKbId(String kbId) {
        imaKbId = kbId;
    }

    // ===================== 公开静态方法 =====================

    /**
     * 检查 IMA 对话是否开启（前端按钮控制）
     */
    public static boolean isAlive() {
        return imaAlive.get();
    }

    /**
     * 切换 IMA 对话开关（CAS 自旋保证原子性）
     *
     * @return 切换后的新状态
     */
    public static boolean toggleAlive() {
        boolean current;
        boolean next;
        do {
            current = imaAlive.get();
            next = !current;
        } while (!imaAlive.compareAndSet(current, next));
        log.info("IMA 对话开关切换为：{}", next);
        return next;
    }

    /**
     * 对话框入口：将用户消息作为关键词搜索知识库，返回格式化结果。
     * <p>与 LlamaService.chat() 同模式，供 WebSocket onMessage 直接调用。
     * <p>当 IMA 开启（isAlive=true）时，每条用户消息都会作为搜索关键词查询默认知识库。
     *
     * @param userMsg 用户发送的原始消息 JSON（由 Message.getUserMsg 构造）
     * @return 格式化后的 AI 回复 JSON（Message 格式）
     */
    public static String chat(String userMsg) {
        if (userMsg == null) {
            return buildReply("消息为空");
        }

        try {
            Message msg = JsonUtil.strToObj(userMsg, Message.class);
            if (msg == null) {
                return buildReply("消息解析失败");
            }
            String query = msg.message();
            if (query == null || query.isBlank()) {
                return buildReply("搜索内容为空");
            }

            String kbId = imaKbId;
            if (kbId == null || kbId.isBlank()) {
                return buildReply("未配置默认知识库 ID，请在 application.properties 中设置 ima.kb-id");
            }

            // 执行搜索
            String data = searchKnowledge(kbId, query);
            if (data == null) {
                return buildReply("知识库搜索失败，请检查凭证配置或网络连接");
            }

            return formatSearchResult(data, query);

        } catch (Exception e) {
            log.error("IMA 对话处理异常：{}", e.getMessage());
            return buildReply("知识库查询异常：" + e.getMessage());
        }
    }

    /**
     * 格式化搜索结果为 HTML 列表
     */
    private static String formatSearchResult(String dataJson, String query) {
        try {
            JsonNode root = JsonUtil.strToObj(dataJson, JsonNode.class);
            if (root == null) {
                return buildHtmlReply("<p>结果解析失败，请稍后再试</p>");
            }
            JsonNode infoList = root.path("info_list");
            if (!infoList.isArray() || infoList.isEmpty()) {
                return buildHtmlReply(String.format("<p>未找到「%s」相关内容，试试换个关键词？</p>", escapeHtml(query)));
            }

            StringBuilder html = new StringBuilder();
            html.append(String.format("<p>知识库搜索「<b>%s</b>」找到 <b>%d</b> 条结果：</p>",
                    escapeHtml(query), infoList.size()));
            html.append("<ul style=\"padding-left:18px;margin:6px 0;\">");

            int maxShow = Math.min(infoList.size(), 5);
            for (int i = 0; i < maxShow; i++) {
                JsonNode item = infoList.get(i);
                String title = item.path("title").asText("无标题");
                String mediaId = item.path("media_id").asText("");
                String highlight = item.path("highlight_content").asText("");
                html.append("<li style=\"margin-bottom:6px;\">");

                // 有 media_id 的文件名做成可点击链接
                if (!mediaId.isBlank()) {
                    String href = "/api/ima/getMedia?mediaId=" + URLEncoder.encode(mediaId, StandardCharsets.UTF_8);
                    html.append("<a href=\"").append(href).append("\" target=\"_blank\">");
                    html.append("<b>").append(escapeHtml(title)).append("</b>");
                    html.append("</a>");
                } else {
                    html.append("<b>").append(escapeHtml(title)).append("</b>");
                }
                if (!highlight.isBlank()) {
                    String brief = highlight.length() > 150 ? highlight.substring(0, 150) + "…" : highlight;
                    html.append("<br><span style=\"font-size:12px;color:#888;\">")
                            .append(escapeHtml(brief)).append("</span>");
                }
                html.append("</li>");
            }

            html.append("</ul>");

            if (infoList.size() > maxShow) {
                html.append(String.format("<p style=\"font-size:12px;color:#999;\">… 还有 %d 条结果</p>",
                        infoList.size() - maxShow));
            }
            return buildHtmlReply(html.toString());
        } catch (Exception e) {
            log.error("格式化搜索结果失败：{}", e.getMessage());
            return buildHtmlReply("<p>结果解析失败，请稍后再试</p>");
        }
    }

    /** HTML 特殊字符转义，防止 XSS */
    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    // ===================== 消息构建工具 =====================
    private static final UserVo IMA_USER = new UserVo("IMA知识库", "#07c160", "库");

    /** 构建纯文本消息 */
    private static String buildReply(String content) {
        return Message.getUserMsg(IMA_USER, content);
    }

    /** 构建 HTML 消息（前端 v-html 渲染） */
    private static String buildHtmlReply(String html) {
        return Message.getUserMsg(IMA_USER, html, true);
    }

    /**
     * 搜索知识库内容
     *
     * @param kbId  知识库 ID
     * @param query 搜索关键词
     * @return 搜索结果 JSON 字符串，失败返回 null
     */
    public static String searchKnowledge(String kbId, String query) {
        return searchKnowledge(SearchKnowledgeReq.build(kbId, query));
    }

    public static String searchKnowledge(SearchKnowledgeReq req) {
        try {
            String resp = doPost("/openapi/wiki/v1/search_knowledge", req);
            log.info("知识库返回：{}", resp);
            return extractData(resp, "search_knowledge");
        } catch (Exception e) {
            log.error("搜索知识库内容失败：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取知识库文件的完整内容（两阶段：先拿下载 URL，再拉取内容）
     *
     * @param mediaId 文件 media_id
     * @return 文件内容字符串，失败返回 null
     */
    public static String getMediaContent(String mediaId) {
        try {
            // 阶段 1：调用 get_media_info 获取下载 URL 和鉴权 headers
            String resp = doPost("/openapi/wiki/v1/get_media_info", new GetMediaInfoReq(mediaId));
            String dataJson = extractData(resp, "get_media_info");
            JsonNode data = JsonUtil.strToObj(dataJson, JsonNode.class);
            if (data == null) {
                log.error("get_media_info data 解析为空");
                return null;
            }

            String downloadUrl = data.path("url_info").path("url").asText();

            if (downloadUrl.isBlank()) {
                log.error("get_media_info 未返回下载 URL");
                return null;
            }
            // 阶段 2：下载文件内容
            log.info("下载 IMA 文件：mediaId={}", mediaId);
            return restTemplate.execute(downloadUrl, HttpMethod.GET, null,
                    response -> StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("获取媒体内容失败：mediaId={}, err={}", mediaId, e.getMessage());
            return null;
        }
    }


    // ===================== 私有工具方法 =====================

    /**
     * 执行 IMA API POST 请求
     *
     * @param path API 路径（如 /openapi/wiki/v1/search_knowledge_base）
     * @param body 请求体对象（会被序列化为 JSON）
     * @return 原始响应 JSON 字符串
     */
    private static String doPost(String path, Object body) {
        log.debug("IMA POST {} - {}", path, JsonUtil.objToStr(body));

        HttpHeaders headers = new HttpHeaders();
        headers.set("ima-openapi-clientid", imaClientId);
        headers.set("ima-openapi-apikey", imaApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(BASE_URL + path, entity, String.class);
    }

    /**
     * 从 IMA 统一响应格式中提取 data 字段
     * <pre>{@code
     * { "code": 0, "msg": "成功", "data": { ... } }
     * }</pre>
     *
     * @param rawJson 原始响应 JSON 字符串
     * @param apiName API 名称（用于日志）
     * @return data 字段的 JSON 字符串
     * @throws RuntimeException 当 code != 0 时抛出
     */
    private static String extractData(String rawJson, String apiName) {
        try {
            ImaResp resp = JsonUtil.strToObj(rawJson, ImaResp.class);
            if (resp == null) {
                throw new RuntimeException("IMA 响应解析失败：JSON 为空");
            }

            if (resp.code() != 0) {
                String errMsg = String.format("IMA %s 失败 [code=%d]：%s", apiName, resp.code(), resp.msg());
                log.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            JsonNode data = resp.data();
            if (data == null || data.isNull()) {
                log.warn("IMA {} 返回空 data", apiName);
                return "{}";
            }
            return JsonUtil.objToStr(data);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("IMA {} 解析响应失败：{}", apiName, e.getMessage());
            throw new RuntimeException("IMA 响应解析失败：" + e.getMessage(), e);
        }
    }
}
