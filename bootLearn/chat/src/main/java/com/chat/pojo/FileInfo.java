package com.chat.pojo;

/**
 * 文件信息
 *
 * @param fileName 文件名
 * @param fileSize 文件大小(带单位)
 * @since 2025-03-23 09:40:55
 */
public record FileInfo(String fileName, String fileSize) {
    public static FileInfo bu(String fileName, long length) {
        String fileSize;
        if (length < (1 << 10))
            // 小于1kb则以b为单位
            fileSize = length + "B";
        else if (length < (1 << 20)) {
            // 小于1mb则kb单位
            long intPart = length >> 10; //整数部分
            long decPart = (length - (intPart << 10)) * 100 >> 10; // 小数部分(x100保留两位小数)
            fileSize = intPart + "." + decPart + "KB";
        } else {
            // 其余mb单位
            long intPart = length >> 20; //整数部分
            long decPart = (length - (intPart << 20)) * 100 >> 20; // 小数部分(x100保留两位小数)
            fileSize = intPart + "." + decPart + "MB";
        }
        return new FileInfo(fileName, fileSize);
    }
}
