package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 题目记录表
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@TableName("question_record")
@SuppressWarnings("unused")
public class QuestionRecord implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 题目记录主键
     */
    @TableId(value = "quest_record_id", type = IdType.AUTO)
    private Integer questRecordId;

    /**
     * 试卷记录主键
     */
    private Integer recordId;

    /**
     * 题目主键
     */
    private Integer questionId;

    /**
     * 题目序号
     */
    private Integer questNo;

    /**
     * 选项(A,B,略)
     */
    private String opName;

    /**
     * 正确选项(A,B,略)
     */
    private String correctOpName;

    /**
     * 得分
     */
    private Integer score;

    public Integer getQuestRecordId() {
        return questRecordId;
    }

    public void setQuestRecordId(Integer questRecordId) {
        this.questRecordId = questRecordId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestNo() {
        return questNo;
    }

    public void setQuestNo(Integer questNo) {
        this.questNo = questNo;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getCorrectOpName() {
        return correctOpName;
    }

    public void setCorrectOpName(String correctOpName) {
        this.correctOpName = correctOpName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
