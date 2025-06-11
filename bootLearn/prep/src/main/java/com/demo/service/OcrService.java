package com.demo.service;

/**
 * OCR图片识别服务
 *
 * @since 2025-06-11 10:45:30
 */
public interface OcrService {
    /**
     * 标准的OCR(识别拍照、截图的印刷体)
     *
     * @param imgPathStr 图片路径
     * @return 识别结果
     */
    String normalOCR(String imgPathStr);
}
