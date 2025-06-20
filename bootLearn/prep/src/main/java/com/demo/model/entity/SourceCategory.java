package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.io.Serial;

/**
 * <p>
 * 题源分类表
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@TableName("source_category")
@SuppressWarnings("unused")
public class SourceCategory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 题源分类主键
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 文件夹名称
     */
    private String folderName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建日期
     */
    private LocalDate createDate;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
