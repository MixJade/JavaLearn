package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 科目表
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@SuppressWarnings("unused")
public class Subject implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 科目主键
     */
    @TableId(value = "subject_id", type = IdType.AUTO)
    private Integer subjectId;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 正式考试日期
     */
    private LocalDate examStartDate;

    /**
     * 文件夹名称
     */
    private String folderName;

    /**
     * 主题色
     */
    private String themeColor;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public LocalDate getExamStartDate() {
        return examStartDate;
    }

    public void setExamStartDate(LocalDate examStartDate) {
        this.examStartDate = examStartDate;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
