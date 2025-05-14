package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 项目分类表
 * </p>
 *
 * @author MixJade
 * @since 2025-05-14
 */
@TableName("prj_item")
@SuppressWarnings("unused")
public class PrjItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 项目主键
     */
    @TableId(value = "item_id", type = IdType.AUTO)
    private Integer itemId;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 项目颜色
     */
    private String color;

    /**
     * 是否作废
     */
    private Boolean isDel;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}
