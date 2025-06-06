package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 题目表
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@SuppressWarnings("unused")
public class Question implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 题目主键
     */
    @TableId(value = "question_id", type = IdType.AUTO)
    private Integer questionId;

    /**
     * 试卷主键
     */
    private Integer paperId;

    /**
     * 题目内容
     */
    private String questContent;

    /**
     * 题目解析
     */
    private String questAnalysis;

    /**
     * 题目序号(也是文件夹名)
     */
    private Integer questNo;

    /**
     * 分值
     */
    private Integer score;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getQuestContent() {
        return questContent;
    }

    public void setQuestContent(String questContent) {
        this.questContent = questContent;
    }

    public String getQuestAnalysis() {
        return questAnalysis;
    }

    public void setQuestAnalysis(String questAnalysis) {
        this.questAnalysis = questAnalysis;
    }

    public Integer getQuestNo() {
        return questNo;
    }

    public void setQuestNo(Integer questNo) {
        this.questNo = questNo;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
