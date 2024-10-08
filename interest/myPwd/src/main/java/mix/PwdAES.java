package mix;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

/**
 * AES加密
 *
 * @since 2024-1-2 19:47
 */
public class PwdAES {

    /**
     * 使用AES对明文进行加密
     *
     * @param keyStr 密钥
     * @param ivStr  初始化向量
     * @param value  明文
     * @return 加密后的密文
     * @since 2024/1/2 15:00
     */
    public static String encrypt(String keyStr, String ivStr, String value) {
        // 处理密钥
        SecretKey key = new SecretKeySpec(padKeyString(keyStr), "AES");
        // 处理向量
        IvParameterSpec iv = new IvParameterSpec(padIvString(ivStr));
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            return "加密失败";
        }
    }

    /**
     * 对AES的密文进行解密
     *
     * @param keyStr    密钥
     * @param ivStr     初始化向量
     * @param encrypted 密文
     * @return 明文
     * @since 2024/1/2 15:00
     */
    public static String decrypt(String keyStr, String ivStr, String encrypted) {
        // 处理密钥
        SecretKey key = new SecretKeySpec(padKeyString(keyStr), "AES");
        // 处理向量
        IvParameterSpec iv = new IvParameterSpec(padIvString(ivStr));
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "解密失败";
        }
    }


    /**
     * 对输入的密钥进行处理，输出长度为16、24、32的数组
     *
     * @param input 输入的密钥文本
     * @return 长度为16、24、32的byte[]
     * @since 2024/1/2 15:00
     */
    private static byte[] padKeyString(String input) {
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        if (inputBytes.length < 24) {
            // 输出的数组长度小于24，则输出16位的密钥
            return padIvString(input, "WhatCannotBeSeen");
        } else if (inputBytes.length > 24 && inputBytes.length < 32) {
            // 输出数组的长度大于24但小于32，则输出24位数组
            byte[] result = new byte[24];
            // 将inputBytes前24位给复制到result中
            System.arraycopy(inputBytes, 0, result, 0, 24);
            return result;
        } else if (inputBytes.length > 32) {
            // 输出数组的长度大于32，则输出32位数组
            byte[] result = new byte[32];
            System.arraycopy(inputBytes, 0, result, 0, 32);
            return result;
        } else {
            // 刚好就是24、32的长度
            return inputBytes;
        }
    }

    /**
     * 重置方法：对输入的向量进行处理
     *
     * @param input 输入的向量文本
     * @return 16位的byte数组
     */
    private static byte[] padIvString(String input) {
        return padIvString(input, "TimeWaitForNoMan");
    }

    /**
     * 对输入的向量进行处理，保证输出16位的byte数组
     *
     * @param input  输入的向量文本
     * @param defTxt 默认填充文本
     * @return 16位的byte数组
     * @since 2024/1/2 15:00
     */
    private static byte[] padIvString(String input, String defTxt) {
        byte[] padBytes = defTxt.getBytes(StandardCharsets.UTF_8);
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        if (inputBytes.length < 16) {
            // 输出的数组长度小于16
            byte[] result = new byte[16];
            // 将inputBytes全部复制到result中
            System.arraycopy(inputBytes, 0, result, 0, inputBytes.length);
            // 将填空字符串拼接到输出字符串中
            System.arraycopy(padBytes, inputBytes.length, result, inputBytes.length, 16 - (inputBytes.length));
            return result;
        } else if (inputBytes.length > 16) {
            // 输出数组的长度大于16
            byte[] result = new byte[16];
            // 将inputBytes前16位给复制到result中
            System.arraycopy(inputBytes, 0, result, 0, 16);
            return result;
        } else {
            return inputBytes;
        }
    }

    /**
     * 使用AES对明文文件进行加密
     *
     * @param keyStr     密钥
     * @param ivStr      初始化向量
     * @param originFile 明文
     * @return 加密后的密文
     * @since 2024-10-02 23:09:16
     */
    public static boolean encryptFile(String keyStr, String ivStr, File originFile) {
        // 处理密钥
        SecretKey key = new SecretKeySpec(padKeyString(keyStr), "AES");
        // 处理向量
        IvParameterSpec iv = new IvParameterSpec(padIvString(ivStr));
        // 创建一个写入指定文件数据的文件输出流
        try (FileOutputStream outputStream = new FileOutputStream(originFile.getPath() + ".enc")) {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            String fileContext = Files.readString(originFile.toPath(), StandardCharsets.UTF_8);
            byte[] encrypted = cipher.doFinal(fileContext.getBytes(StandardCharsets.UTF_8));
            // 把二进制数据写入文件
            outputStream.write(encrypted);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 对AES的加密文件进行解密
     *
     * @param keyStr  密钥
     * @param ivStr   初始化向量
     * @param encFile 密文
     * @return 明文
     * @since 2024-10-02 23:09:20
     */
    public static String decryptFile(String keyStr, String ivStr, File encFile) {
        // 处理密钥
        SecretKey key = new SecretKeySpec(padKeyString(keyStr), "AES");
        // 处理向量
        IvParameterSpec iv = new IvParameterSpec(padIvString(ivStr));
        try (FileInputStream inputStream = new FileInputStream(encFile)) {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = cipher.doFinal(inputStream.readAllBytes());
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "解密失败";
        }
    }
}