package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/myController")
public class MyController {
    @RequestMapping("/first")
    @ResponseBody//设置返回值为相应内容,不然会认为返回的是路径，从而报404
    public String first() {
        System.out.println("This is a first");
        return "{'info','springMVC'}";
    }
    @RequestMapping("/second")
    @ResponseBody//设置返回值为相应内容,不然会认为返回的是路径，从而报404
    public String second() {
        System.out.println("This is a second");
        return "{'info','second'}";
    }
}
