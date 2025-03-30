package com.demo.model.vo;

/**
 * 使用时的支付分类(小类)(二级下拉框所用)
 *
 * @param oneKey   大类编号
 * @param twoKey   小类编号
 * @param twoValue 小类名称
 */
public record TypeSelectTwoVo(Integer oneKey, Integer twoKey, String twoValue) {
}
