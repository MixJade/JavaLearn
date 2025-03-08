package com.example.mysecurity.common;

public record Res(int code, String msg) {
    public static Res suc(String msg) {
        return new Res(1, msg);
    }

    public static Res err(String msg) {
        return new Res(0, msg);
    }
}
