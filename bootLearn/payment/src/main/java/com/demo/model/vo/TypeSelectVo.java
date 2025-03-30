package com.demo.model.vo;

import java.util.List;

/**
 * 使用时的支付分类(二级下拉框所用)
 *
 * @param oneKey   大类编号
 * @param oneValue 大类名称
 * @param twoList  大类的支付小类
 */
public record TypeSelectVo(Integer oneKey, String oneValue, List<TypeSelectTwoVo> twoList) {
}
