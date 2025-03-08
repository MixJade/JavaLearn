package com.demo.common;

/**
 * 通用返回结果，封装增删改的响应数据
 *
 * @param code 1成功，0和其它数字为失败
 * @param msg  传递信息
 */
public record Result(int code, String msg) {

    public static Result choice(String msg, boolean res) {
        if (res)
            return new Result(1, msg + "成功");
        else
            return new Result(0, msg + "失败");
    }

    public static Result error(String msg) {
        return new Result(0, msg);
    }
}