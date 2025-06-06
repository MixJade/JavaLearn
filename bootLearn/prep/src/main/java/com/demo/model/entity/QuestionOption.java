package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 题目选项表
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@TableName("question_option")
@SuppressWarnings("unused")
public class QuestionOption implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 选项主键
     */
    @TableId(value = "option_id", type = IdType.AUTO)
    private Integer optionId;

    /**
     * 题目主键
     */
    private Integer questionId;

    /**
     * 选项内容
     */
    private String opContent;

    /**
     * 是否正确选项
     */
    private Boolean isCorrect;

    /**
     * 选项排序(1,2,3)
     */
    private Integer opNo;

    /**
     * 选项名称(A,B,C)
     */
    private String opName;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getOpContent() {
        return opContent;
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getOpNo() {
        return opNo;
    }

    public void setOpNo(Integer opNo) {
        this.opNo = opNo;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }
}
