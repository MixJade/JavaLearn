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
            new BigType(1, "餐饮", "#FF4500"),
            new BigType(2, "出行", "#1E90FF"),
            new BigType(3, "居住", "#6B8E23"),
            new BigType(4, "娱乐", "#FF1493"),
            new BigType(5, "修炼", "#800080"),
            new BigType(6, "日常", "#008000"),
            new BigType(7, "杂物", "#B59900"),
            new BigType(8, "医疗", "#FF6347"),
            new BigType(9, "工资", "#00A9A9"),
            new BigType(10, "借款", "#A52A2A"),
            new BigType(11, "形象", "#000080")
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
     * 获取大类对应的颜色(此事在js文件亦有记载)
     *
     * @return 颜色代码
     */
    public static Map<Integer, String> getColorMap() {
        Map<Integer, String> bigTypeMap = new HashMap<>();
        for (BigType bigType : bigTypes) {
            bigTypeMap.put(bigType.typeKey(), bigType.color());
        }
        return bigTypeMap;
    }
}
