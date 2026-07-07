package com.chat.llama;

import com.chat.llama.req.ChatMessage;
import com.chat.llama.req.ChatRequest;
import com.chat.llama.resp.AiMsg;
import com.chat.llama.resp.ChatResponse;
import com.chat.pojo.Message;
import com.chat.pojo.UserVo;
import com.chat.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 全静态化 Llama 服务
 * 多线程安全、全局共享、WebSocket 直接调用
 */
@Component
public class LlamaService {

    private static final Logger log = LoggerFactory.getLogger(LlamaService.class);

    // ===================== 静态配置 =====================
    private static Integer llamaPort;

    // ===================== 静态全局对象 =====================
    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * llama-server 存活状态（全局共享、多线程可见）
     * 通过接口手动切换，不再通过端口探测
     * 使用 AtomicBoolean 保证读-改-写操作的原子性
     */
    private static final AtomicBoolean llamaAlive = new AtomicBoolean(false);

    // ===================== Spring 注入静态字段 =====================
    @Value("${llama.port}")
    public void setStaticLlamaPort(Integer port) {
        llamaPort = port;
    }

    // ===================== 静态工具方法 =====================

    /**
     * 检查服务是否存活（直接读取原子变量）
     */
    public static boolean isAlive() {
        return llamaAlive.get();
    }

    /**
     * 切换存活状态（CAS 自旋保证原子性）
     *
     * @return 切换后的新状态
     */
    public static boolean toggleAlive() {
        boolean current;
        boolean next;
        do {
            current = llamaAlive.get();
            next = !current;
        } while (!llamaAlive.compareAndSet(current, next));
        log.info("llama-server存活状态切换为：{}", next);
        return next;
    }

    public static String chat(String userInput) {
        String chatAi = chatAi(userInput);
        return Message.getUserMsg(new UserVo("千问", "#2233fd", "千"), chatAi);
    }

    /**
     * 核心：AI 对话（静态 + 多线程安全）
     */
    private static String chatAi(String userInput) {
        try {
            Message userMsg = JsonUtil.strToObj(userInput, Message.class);
            if (userMsg == null) {
                return "用户消息解析异常";
            }
            String sendMsg = userMsg.message();
            log.info("发送消息：{}", sendMsg);
            List<ChatMessage> chatMessages = new ArrayList<>();
            chatMessages.add(new ChatMessage("system", buildSystemPrompt(sendMsg)));
            chatMessages.add(new ChatMessage("user", sendMsg));
            ChatRequest request = ChatRequest.buildReq(chatMessages);

            String url = "http://127.0.0.1:" + llamaPort + "/v1/chat/completions";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);

            ChatResponse response = restTemplate.postForObject(url, entity, ChatResponse.class);

            if (response != null && response.choices() != null && !response.choices().isEmpty()) {
                AiMsg aiMsg = response.choices().get(0).message();
                log.info("AI思考：{}", aiMsg.reasoning_content());
                log.info("AI回答：{}", aiMsg.content());
                return aiMsg.content();
            }
            return "暂无回答";
        } catch (Exception e) {
            log.error("AI对话异常：{}", e.getMessage());
            return "服务异常，请稍后再试";
        }
    }

    /**
     * 设定系统提示词
     */
    private static String buildSystemPrompt(String userQuestion) {
        if (userQuestion.contains("吃的") || userQuestion.contains("吃什么")) {
            return "推荐西瓜、炸鸡";
        } else if (userQuestion.contains("时间") || userQuestion.contains("日期")) {
            return "当前时间：" + LocalDateTime.now();
        } else {
            return "每句结尾加喵";
        }
    }
}