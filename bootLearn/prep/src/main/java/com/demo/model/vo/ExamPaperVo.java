package com.demo.model.vo;

import com.demo.model.entity.ExamPaper;

/**
 * 试卷表 查询结果
 *
 * @since 2025-06-26 15:39:37
 */
public class ExamPaperVo extends ExamPaper {
    /**
     * 分类名称
     */
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
