package com.example.mysecurity.service;

/**
 * 这是密封类的语法，
 * 使用sealed修饰，permits之后跟着实现类
 * 实现类必须是final，或者也是一个密封类
 */
public sealed interface INowUser permits NowUserImpl {
    Object getUser();
}
