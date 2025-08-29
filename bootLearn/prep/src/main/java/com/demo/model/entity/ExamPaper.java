package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.io.Serial;

/**
 * <p>
 * 试卷表
 * </p>
 *
 * @author MixJade
 * @since 2025-08-29
 */
@TableName("exam_paper")
@SuppressWarnings("unused")
public class ExamPaper implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 试卷主键
     */
    @TableId(value = "paper_id", type = IdType.AUTO)
    private Integer paperId;

    /**
     * 试卷名称
     */
    private String paperName;

    /**
     * 题源分类主键
     */
    private Integer categoryId;

    /**
     * 总分(自动计算)
     */
    private Integer totalScore;

    /**
     * 考试时长(秒)
     */
    private Integer duration;

    /**
     * 创建日期
     */
    private LocalDate createDate;

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
