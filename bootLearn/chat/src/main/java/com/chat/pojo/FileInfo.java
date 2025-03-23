package com.chat.pojo;

/**
 * 文件信息
 *
 * @param fileName 文件名
 * @param fileSize 文件大小(带单位)
 * @since 2025-03-23 09:40:55
 */
public record FileInfo(String fileName, String fileSize) {
}
