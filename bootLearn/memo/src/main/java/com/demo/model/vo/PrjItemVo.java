package com.demo.model.vo;

import com.demo.model.entity.PrjItem;

/**
 * 项目分页查询返回给前端
 *
 * @since 2025-05-14 11:08:43
 */
public class PrjItemVo extends PrjItem {
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
