package com.demo.model.vo;

import com.demo.model.entity.SourceImage;

/**
 * 前端查询图片表返回
 *
 * @since 2025-06-11 14:36:16
 */
public class SourceImgVo extends SourceImage {
    /**
     * 文件夹名称
     */
    private String folderName;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
