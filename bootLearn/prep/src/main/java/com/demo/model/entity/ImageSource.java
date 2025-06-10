package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 题源图片表
 * </p>
 *
 * @author MixJade
 * @since 2025-06-10
 */
@TableName("image_source")
@SuppressWarnings("unused")
public class ImageSource implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 题源主键
     */
    @TableId(value = "source_id", type = IdType.AUTO)
    private Integer sourceId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 题源分类主键
     */
    private Integer categoryId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 文字识别结果
     */
    private String ocrResult;

    /**
     * 识别时间
     */
    private LocalDateTime ocrTime;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getOcrResult() {
        return ocrResult;
    }

    public void setOcrResult(String ocrResult) {
        this.ocrResult = ocrResult;
    }

    public LocalDateTime getOcrTime() {
        return ocrTime;
    }

    public void setOcrTime(LocalDateTime ocrTime) {
        this.ocrTime = ocrTime;
    }
}
