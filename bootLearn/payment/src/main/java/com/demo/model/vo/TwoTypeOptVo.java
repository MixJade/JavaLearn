package com.demo.model.vo;

import java.util.List;

/**
 * 二级下拉框数据(按收支区分)
 *
 * @param inList  收入类型的二级下拉框
 * @param outList 支出类型的二级下拉框
 */
public record TwoTypeOptVo(List<TypeSelectVo> inList, List<TypeSelectVo> outList) {
}
