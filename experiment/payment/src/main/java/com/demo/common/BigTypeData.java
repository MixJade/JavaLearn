package com.demo.common;

import com.demo.model.vo.BigType;

import java.util.Arrays;
import java.util.List;

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
            new BigType(6, "通讯"),
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
}
