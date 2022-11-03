package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
    @RequestMapping("/first")
    @ResponseBody//设置返回值即为响应
    public String first() {
        System.out.println("This is a first");
        return "{'info','springMVC'}";
    }
}
