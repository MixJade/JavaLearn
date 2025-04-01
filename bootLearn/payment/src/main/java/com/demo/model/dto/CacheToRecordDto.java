package com.demo.model.dto;

import com.demo.model.entity.PaymentRecord;

/**
 * 前端从缓存表添加记录表所用
 */
@SuppressWarnings("unused")
public class CacheToRecordDto extends PaymentRecord {
    /**
     * 缓存主键
     */
    private Integer cacheId;

    public Integer getCacheId() {
        return cacheId;
    }

    public void setCacheId(Integer cacheId) {
        this.cacheId = cacheId;
    }
}
