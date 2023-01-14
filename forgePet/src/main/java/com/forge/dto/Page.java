package com.forge.dto;

public class Page<T> {
    private int len;
    private T data;

    public static <T> Page<T> page(T object, int len) {
        Page<T> p = new Page<>();
        p.data = object;
        p.len = len;
        return p;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
