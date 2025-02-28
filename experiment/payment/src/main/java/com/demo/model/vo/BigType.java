package com.demo.model.vo;

/**
 * 收支类型的大类
 *
 * @param typeKey  大类键
 * @param typeName 大类名称
 * @param color    大类颜色
 */
public record BigType(int typeKey, String typeName, String color) {
}
