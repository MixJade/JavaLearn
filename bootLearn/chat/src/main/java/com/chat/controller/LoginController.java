package com.chat.controller;

import com.chat.pojo.Result;
import com.chat.pojo.UserVo;
import com.chat.utils.ColorUtil;
import com.chat.ws.ChatEndpoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在登录页面所调用的接口
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private static final String[] CN_NUM = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    @GetMapping("/getUserName")
    public String getUserName(HttpServletRequest request) {
        String ip = getIp(request);
        log.info("访问人IP：{}", ip);
        return ipToName(ip);
    }

    /**
     * IP生成名字：倒数第二段逐位转中文大写数字 + 最后一段保留阿拉伯数字
     * 例：192.168.96.146 → 玖陆146，192.168.7.101 → 柒101
     */
    private String ipToName(String ip) {
        String[] parts = ip.split("\\.");
        if (parts.length < 4) {
            return "异常访问用户";
        }
        StringBuilder cnPart = new StringBuilder();
        for (char c : parts[2].toCharArray()) {
            int digit = c - '0';
            cnPart.append(CN_NUM[digit]);
        }
        return cnPart + parts[3];
    }

    /**
     * 获取访问人真实IP（支持代理场景）
     */
    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isBlank() && !"unknown".equalsIgnoreCase(ip)) {
            int idx = ip.indexOf(',');
            if (idx != -1) ip = ip.substring(0, idx);
            return ip.strip();
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isBlank() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.strip();
        }
        return request.getRemoteAddr();
    }

    @GetMapping("/toLogin")
    public Result toLogin(@RequestParam("user") String user, HttpSession session) {
        if (user != null && !user.isBlank()) {
            if (ChatEndpoint.isDupName(user))
                return new Result(false, "名称重复");
            session.setAttribute("user", new UserVo(user,
                    ColorUtil.randomColor(),
                    user.substring(0, 1)));
            log.info("用户【{}】登录", user);
            return new Result(true, "成功");
        } else {
            return new Result(false, "失败");
        }
    }
}

