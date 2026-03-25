package com.chat.llama;

import com.chat.llama.req.ChatMessage;
import com.chat.llama.req.ChatRequest;
import com.chat.llama.resp.AiMsg;
import com.chat.llama.resp.ChatResponse;
import com.chat.pojo.Message;
import com.chat.pojo.UserVo;
import com.chat.utils.JsonUtil;
import com.chat.utils.TimeUtil;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 全静态化 Llama 服务
 * 多线程安全、全局共享、WebSocket 直接调用
 */
@Component
public class LlamaService {

    private static final Logger log = LoggerFactory.getLogger(LlamaService.class);

    // ===================== 静态配置 =====================
    private static String llamaExePath;
    private static String modelPath;
    private static String llamaPort;

    // ===================== 静态全局对象 =====================
    private static Process process;
    private static final RestTemplate restTemplate = new RestTemplate();

    // ===================== Spring 注入静态字段 =====================
    @Value("${llama.exe-path}")
    public void setStaticLlamaExePath(String path) {
        llamaExePath = path;
    }

    @Value("${llama.model-path}")
    public void setStaticModelPath(String path) {
        modelPath = path;
    }

    @Value("${llama.port}")
    public void setStaticLlamaPort(String port) {
        llamaPort = port;
    }

    // ===================== 静态工具方法 =====================

    /**
     * 检查服务是否存活
     */
    public static boolean isAlive() {
        return process != null && process.isAlive();
    }

    /**
     * 启动 llama-server
     */
    public static synchronized String start() {
        if (isAlive()) {
            return "LLama 已在运行中";
        }

        try {
            process = new ProcessBuilder(
                    llamaExePath,
                    "-m", modelPath,
                    "--host", "0.0.0.0",
                    "--port", llamaPort
            ).start();

            log.info("Llama 服务启动成功，端口：{}", llamaPort);
            return "启动成功";
        } catch (IOException e) {
            log.error("Llama 启动失败", e);
            return "启动失败：" + e.getMessage();
        }
    }

    public static String chat(String userInput) {
        String chatAi = chatAi(userInput);
        Message message = new Message(false, new UserVo("千问", "#2233fd", "千"), chatAi, TimeUtil.nowTime());
        return JsonUtil.objToStr(message);
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
            chatMessages.add(new ChatMessage("user", sendMsg));
            ChatRequest request = ChatRequest.buildReq(chatMessages);

            String url = "http://localhost:" + llamaPort + "/v1/chat/completions";
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

    public static String stop() {
        // 1. 检查进程是否存在且存活
        if (process == null || !process.isAlive()) {
            return "LLama 服务未运行，无需停止";
        }
        try {
            // 2. 尝试优雅销毁进程
            process.destroy();
            log.info("已发送停止指令给 Llama 服务，等待进程退出...");
            // 3. 等待进程退出（最多等待5秒）
            boolean isTerminated = process.waitFor(5, TimeUnit.SECONDS);
            if (isTerminated) {
                // 清空process引用，避免后续isAlive判断异常
                process = null;
                return "LLama 服务已成功停止";
            } else {
                // 4. 若等待超时，强制销毁进程
                process.destroyForcibly();
                process = null;
                return "Llama 服务强制停止成功";
            }
        } catch (Exception e) {
            String msg = "停止Llama服务失败：" + e.getMessage();
            log.error(msg, e);
            return msg;
        }
    }

    /**
     * 关闭服务（Spring 容器关闭时自动执行）
     */
    @PreDestroy
    public void destroy() {
        if (isAlive()) {
            process.destroy();
            log.info("Llama 服务已关闭");
        }
    }
}