package com.chat.ws;


import com.chat.pojo.Message;
import com.chat.utils.MessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通信类
 */
@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {

    //用来存储每个用户客户端对象的ChatEndpoint对象
    private static final Map<String,ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();

    //声明session对象，通过对象可以发送消息给指定的用户
    private Session session;

    //声明HttpSession对象，我们之前在HttpSession对象中存储了用户名
    private HttpSession httpSession;

    //连接建立
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        //存储登陆的对象
        String username = (String)httpSession.getAttribute("user");
        if (username==null) return;
        onlineUsers.put(username,this);
        //将当前在线用户的用户名推送给所有的客户端
        //1 获取消息
        String message = MessageUtils.getMessage(true, null, getNames());
        //2 调用方法进行系统消息的推送
        broadcastAllUsers(message);
    }

    private void broadcastAllUsers(String message){
        System.out.println(message);
        try {
            //将消息推送给所有的客户端
            Set<String> names = onlineUsers.keySet();
            for (String name : names) {
                ChatEndpoint chatEndpoint = onlineUsers.get(name);
                chatEndpoint.session.getBasicRemote().sendText(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //返回在线用户名
    private Set<String> getNames(){
        return onlineUsers.keySet();
    }

    //收到客户端发送数据
    @OnMessage
    public void onMessage(String message){
        //将数据转换成对象
        try {
            ObjectMapper mapper =new ObjectMapper();
            Message mess = mapper.readValue(message, Message.class);
            System.out.println("信息是"+mess);
            String toName = mess.toName();
            String data = mess.message();
            String username = (String) httpSession.getAttribute("user");
            String resultMessage = MessageUtils.getMessage(false, username, data);
            //发送数据
            onlineUsers.get(toName).session.getBasicRemote().sendText(resultMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //链接关闭
    @OnClose
    public void onClose() {
        String username = (String) httpSession.getAttribute("user");
        if (username==null) return;
        //从容器中删除指定的用户
        onlineUsers.remove(username);
        MessageUtils.getMessage(true,null,getNames());
    }}

