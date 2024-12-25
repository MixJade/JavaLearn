package com.demo.common;

import com.demo.model.vo.BigType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收支类型大类
 */
public class BigTypeData {
    private static final List<BigType> bigTypes = Arrays.asList(
            new BigType(1, "餐饮"),
            new BigType(2, "出行"),
            new BigType(3, "居住"),
            new BigType(4, "娱乐"),
            new BigType(5, "学习"),
            new BigType(6, "日常"),
            new BigType(7, "购物"),
            new BigType(8, "医疗"),
            new BigType(9, "工资"),
            new BigType(10, "借款"),
            new BigType(11, "理财"),
            new BigType(12, "其他")
    );

    public static List<BigType> getBigTypes() {
        return bigTypes;
    }

    public static Map<Integer, String> getMap() {
        Map<Integer, String> bigTypeMap = new HashMap<>();
        for (BigType bigType : bigTypes) {
            bigTypeMap.put(bigType.typeKey(), bigType.typeName());
        }
        return bigTypeMap;
    }

    /**
     * 获取大类对应的颜色
     *
     * @param bigType 大类主键
     * @return 颜色代码
     */
    public static String getBigTypeColor(int bigType) {
        return switch (bigType) {
            case 1 -> "#FF4500"; // 橙色
            case 2 -> "#1E90FF"; // 天蓝色
            case 3 -> "#6B8E23"; // 橄榄色
            case 4 -> "#FF1493"; // 深粉色
            case 5 -> "#800080"; // 紫色
            case 6 -> "#008000"; // 绿色
            case 7 -> "#B59900"; // 金色
            case 8 -> "#FF6347"; // 番茄色
            case 9 -> "#00A9A9"; // 青色
            case 10 -> "#A52A2A"; // 棕色
            case 11 -> "#000080"; // 深蓝色
            case 12 -> "#696969"; // 暗灰色
            default -> "#409EFF"; // 默认蓝色
        };
    }
}
