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
 * @since 2025-06-12
 */
@TableName("exam_quest_opt")
@SuppressWarnings("unused")
public class ExamQuestOpt implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 选项主键
     */
    @TableId(value = "opt_id", type = IdType.AUTO)
    private Integer optId;

    /**
     * 题目主键
     */
    private Integer questId;

    /**
     * 选项内容
     */
    private String optCont;

    /**
     * 存在图片
     */
    private Boolean haveImg;

    /**
     * 图片名称
     */
    private String imgName;

    /**
     * 是否正确选项
     */
    private Boolean isCorrect;

    /**
     * 选项排序(1,2,3)
     */
    private Integer optNo;

    /**
     * 选项名称(A,B,C)
     */
    private String optName;

    public Integer getOptId() {
        return optId;
    }

    public void setOptId(Integer optId) {
        this.optId = optId;
    }

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public String getOptCont() {
        return optCont;
    }

    public void setOptCont(String optCont) {
        this.optCont = optCont;
    }

    public Boolean getHaveImg() {
        return haveImg;
    }

    public void setHaveImg(Boolean haveImg) {
        this.haveImg = haveImg;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getOptNo() {
        return optNo;
    }

    public void setOptNo(Integer optNo) {
        this.optNo = optNo;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }
}
