package com.demo.model.vo;

import java.util.List;

/**
 * 通过题目编号查询图片列表
 *
 * @since 2025-06-27 16:10:34
 */
public record QuestImgListVo(Integer categoryId, List<QuestImgVo> questImgVoList) {
}
