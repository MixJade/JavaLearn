package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.io.Serial;

/**
 * <p>
 * 题目记录表
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@TableName("record_quest")
@SuppressWarnings("unused")
public class RecordQuest implements Serializable {
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
    private Integer questId;

    /**
     * 题目类型,1选择,2填空,3大题
     */
    private Integer questType;

    /**
     * 题目序号
     */
    private Integer questNo;

    /**
     * 选项(A,B,略,填空)
     */
    private String opName;

    /**
     * 正确选项
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

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public Integer getQuestType() {
        return questType;
    }

    public void setQuestType(Integer questType) {
        this.questType = questType;
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
