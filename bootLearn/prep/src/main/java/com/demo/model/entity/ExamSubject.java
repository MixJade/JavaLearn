package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.io.Serial;

/**
 * <p>
 * 科目表
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@TableName("exam_subject")
@SuppressWarnings("unused")
public class ExamSubject implements Serializable {
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
     * 创建日期
     */
    private LocalDate createDate;

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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
