package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.io.Serial;

/**
 * <p>
 * 题目表
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@TableName("exam_quest")
@SuppressWarnings("unused")
public class ExamQuest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 题目主键
     */
    @TableId(value = "quest_id", type = IdType.AUTO)
    private Integer questId;

    /**
     * 试卷主键
     */
    private Integer paperId;

    /**
     * 题目类型,1选择,2填空,3大题
     */
    private Integer questType;

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

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getQuestType() {
        return questType;
    }

    public void setQuestType(Integer questType) {
        this.questType = questType;
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
}
