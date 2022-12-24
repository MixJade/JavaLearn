package com.demo.common;

/**
 * 通用返回结果，封装增删改的响应数据
 */
public class Result {
    private Integer code; //编码：1成功，0和其它数字为失败
    private String msg; //传递信息

    public static Result choice(String msg, boolean res) {
        Result r = new Result();
        if (res) {
            r.code = 1;
            r.msg = msg + "成功";
        } else {
            r.code = 0;
            r.msg = msg + "失败";
        }
        return r;
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.code = 0;
        r.msg = msg;
        return r;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}