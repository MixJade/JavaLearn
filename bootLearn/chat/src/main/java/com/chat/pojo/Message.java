package com.chat.pojo;

import com.chat.utils.JsonUtil;
import com.chat.utils.TimeUtil;

/**
 * 用户发送消息
 *
 * @param isSystem 是否为系统发送
 * @param userVo   发送者
 * @param message  信息
 * @param sendTime 发送时间
 */
public record Message(boolean isSystem, UserVo userVo, String message, String sendTime) {
    /**
     * 发送用户消息
     *
     * @param userVo  用户实体类
     * @param message 发送的消息
     * @return 序列化字符串
     */
    public static String getUserMsg(UserVo userVo, String message) {
        Message result = new Message(false,
                userVo,
                message,
                TimeUtil.nowTime());
        //把字符串转成json格式的字符串
        return JsonUtil.objToStr(result);
    }

    /**
     * 获取系统消息
     *
     * @param isUP     是否为上线通知
     * @param userName 上下线的用户名
     * @return 系统消息
     */
    public static String getSystemMsg(boolean isUP, String userName) {
        Message result = new Message(true,
                null,
                TimeUtil.nowTime() + " " + userName + " " + (isUP ? "上线" : "下线"),
                null);
        //把字符串转成json格式的字符串
        return JsonUtil.objToStr(result);
    }
}
