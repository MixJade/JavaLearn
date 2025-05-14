package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 备忘sql表
 * </p>
 *
 * @author MixJade
 * @since 2025-05-14
 */
@TableName("memo_sql")
@SuppressWarnings("unused")
public class MemoSql implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 备忘主键
     */
    @TableId(value = "memo_id", type = IdType.AUTO)
    private Integer memoId;

    /**
     * 备忘名称
     */
    private String memoName;

    /**
     * 项目主键
     */
    private Integer itemId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 备忘内容
     */
    private String memoContent;

    /**
     * 是否作废
     */
    private Boolean isDel;

    public Integer getMemoId() {
        return memoId;
    }

    public void setMemoId(Integer memoId) {
        this.memoId = memoId;
    }

    public String getMemoName() {
        return memoName;
    }

    public void setMemoName(String memoName) {
        this.memoName = memoName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}
