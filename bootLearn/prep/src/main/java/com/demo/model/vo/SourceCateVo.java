package com.demo.model.vo;

import com.demo.model.entity.SourceCategory;

/**
 * 题源分类表 查询结果
 *
 * @since 2025-06-11 14:31:37
 */
public class SourceCateVo extends SourceCategory {
    /**
     * 对应记录数量
     */
    Integer recordNum;

    public Integer getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(Integer recordNum) {
        this.recordNum = recordNum;
    }
}
