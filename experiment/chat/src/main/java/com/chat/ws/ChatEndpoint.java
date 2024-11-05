package com.chat.ws;

import com.chat.pojo.Message;
import com.chat.pojo.UserVo;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.RemoteEndpoint.Basic;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 通信类
 */
@ServerEndpoint(value = "/chat", configurator = HttpSessionEndpoint.class)
@Component
public class ChatEndpoint {
    private static final Logger log = LoggerFactory.getLogger(ChatEndpoint.class);

    // 所有用户的远程端点集合
    private static final Map<String, Basic> BASICS_MAP = new ConcurrentHashMap<>();

    // 历史存储的消息
    private static final CopyOnWriteArrayList<String> MSG_LIST = new CopyOnWriteArrayList<>();

    /**
     * 声明HttpSession对象，登录时在HttpSession对象中存储了用户名
     */
    private HttpSession httpSession;

    //连接建立
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        //存储登陆的对象
        UserVo userVo = (UserVo) httpSession.getAttribute("user");
        if (userVo == null) return;
        Basic basicRemote = session.getBasicRemote();
        BASICS_MAP.put(userVo.username(), basicRemote);
        // 将刚上线的用户名推送给所有的客户端
        broadcastAllUsers(true, userVo.username());
        // 同步历史消息
        syncHistoryMsg(basicRemote);
    }

    /**
     * 将当前在线用户的用户名推送给所有的客户端
     */
    private void broadcastAllUsers(boolean isUP, String userName) {
        String message = Message.getSystemMsg(isUP, userName);
        // log.info("系统广播:" + message);
        try {
            // 将消息推送给所有的客户端
            Set<String> names = BASICS_MAP.keySet();
            for (String name : names)
                BASICS_MAP.get(name).sendText(message);
        } catch (Exception e) {
            log.warn("系统广播失败");
        }
    }

    /**
     * 将当前的历史消息同步给客户端
     *
     * @param basicRemote 客户端的远程端点
     */
    private void syncHistoryMsg(Basic basicRemote) {
        if (MSG_LIST.size() == 0) return;
        try {
            for (String message : MSG_LIST) {
                basicRemote.sendText(message);
            }
        } catch (Exception e) {
            log.warn("消息同步失败");
        }
    }

    /**
     * 收到客户端发送数据
     *
     * @param message 客户端发的消息,需json转换
     */
    @OnMessage
    public void onMessage(String message) {
        //将数据转换成对象
        try {
            UserVo userVo = (UserVo) httpSession.getAttribute("user");
            // log.info("用户【{}】说:{}", userVo.username(), message);
            String sendMsg = Message.getUserMsg(userVo, message);
            MSG_LIST.add(sendMsg);
            //发送数据
            //将消息推送给所有的客户端
            Set<String> names = BASICS_MAP.keySet();
            for (String name : names)
                BASICS_MAP.get(name).sendText(sendMsg);
        } catch (Exception e) {
            log.warn("信息发送失败");
        }
    }

    //链接关闭
    @OnClose
    public void onClose() {
        UserVo userVo = (UserVo) httpSession.getAttribute("user");
        if (userVo == null) return;
        String username = userVo.username();
        log.info("用户【{}】离线", username);
        // 从容器中删除指定的用户
        BASICS_MAP.remove(username);
        // 通知用户
        broadcastAllUsers(false, username);
    }

    /**
     * 配置错误信息处理
     *
     * @param session 客户端会话,缺少会报错
     * @param t       WebSocket抛的异常,缺少会报错
     */
    @OnError
    @SuppressWarnings("unused")
    public void onError(Session session, Throwable t) {
        // 什么都不想打印都去掉就好了
        log.info("WebSocket出错");
        // 这里打印的也是  java.io.EOFException: null
        // t.printStackTrace();
    }

    /**
     * 检查是否重名
     *
     * @param username 新的用户名
     * @return 重名了
     */
    public static boolean isDupName(String username) {
        return BASICS_MAP.containsKey(username);
    }
}

