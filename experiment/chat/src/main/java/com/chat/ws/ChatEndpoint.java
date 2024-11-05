package com.chat.ws;


import com.chat.pojo.Message;
import com.chat.pojo.MsgUser;
import com.chat.pojo.SocketMsg;
import com.chat.utils.NameUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 通信类
 */
@ServerEndpoint(value = "/chat", configurator = HttpSessionEndpoint.class)
@Component
public class ChatEndpoint {
    private static final Logger log = LoggerFactory.getLogger(ChatEndpoint.class);

    private static final CopyOnWriteArrayList<MsgUser> MSG_USER_LIST = new CopyOnWriteArrayList<>();

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
        String username = (String) httpSession.getAttribute("user");
        if (username == null) return;
        MSG_USER_LIST.add(new MsgUser(username, session.getBasicRemote()));
        // 将当前在线用户的用户名推送给所有的客户端
        broadcastAllUsers();
    }

    /**
     * 将当前在线用户的用户名推送给所有的客户端
     */
    private void broadcastAllUsers() {
        String message = SocketMsg.getMsg(true, null, NameUtil.nowName);
        log.info("系统广播是:" + message);
        try {
            //将消息推送给所有的客户端
            for (MsgUser msgUser : MSG_USER_LIST) {
                msgUser.basic().sendText(message);
            }
        } catch (Exception e) {
            log.warn("系统广播失败");
        }
    }

    //收到客户端发送数据
    @OnMessage
    public void onMessage(String message) {
        //将数据转换成对象
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message mess = mapper.readValue(message, Message.class);
            log.info("信息是" + mess);
            String toName = mess.toName();
            String data = mess.message();
            String username = (String) httpSession.getAttribute("user");
            String socketMsg = SocketMsg.getMsg(false, username, data);
            //发送数据
            for (MsgUser msgUser : MSG_USER_LIST) {
                if (msgUser.username().equals(toName))
                    msgUser.basic().sendText(socketMsg);
            }
        } catch (Exception e) {
            log.warn("信息发送失败");
        }
    }

    //链接关闭
    @OnClose
    public void onClose() {
        String username = (String) httpSession.getAttribute("user");
        if (username == null) return;
        // 从容器中删除指定的用户
        MSG_USER_LIST.removeIf(i -> i.username().equals(username));
        NameUtil.nowName.remove(username);
        // 通知用户
        broadcastAllUsers();
    }
}

