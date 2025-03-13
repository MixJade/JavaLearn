package com.chat.controller;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class QRCodeController {
    private static final Logger log = LoggerFactory.getLogger(QRCodeController.class);

    /**
     * 生成二维码
     *
     * @param url 需要二维码包含的内容
     * @return 二维码图片
     */
    @GetMapping(value = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateQRCode(@RequestParam String url) {
        int width = 300;
        int height = 300;
        return generateQRCodeImage(url, width, height);
    }

    /**
     * 解析上传的二维码内容
     *
     * @param qrFile 二维码图片
     * @return 二维码内容
     */
    @PostMapping(value = "/qrcode")
    public String parseQRCode(MultipartFile qrFile) {
        if (qrFile == null) return "请选择图片";
        String qrCodeText = parseQRCodeText(qrFile);
        log.info("解析二维码内容:{}", qrCodeText);
        return qrCodeText;
    }

    /**
     * 生成二维码图片
     *
     * @param text   文本
     * @param width  宽度
     * @param height 高度
     * @return 图片的二进制流
     */
    private byte[] generateQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            //内容编码格式
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 指定纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            return pngOutputStream.toByteArray();
        } catch (WriterException e) {
            log.error("图片生成失败:{}", e.getMessage());
            return null;
        } catch (IOException e) {
            log.error("IO异常:{}", e.getMessage());
            return null;
        }
    }

    /**
     * 解析上传的二维码内容
     *
     * @param qrFile 二维码图片
     * @return 二维码内容
     */
    private String parseQRCodeText(MultipartFile qrFile) {
        try {
            // 获取文件的输入流
            InputStream inputStream = qrFile.getInputStream();
            // 将输入流转换为BufferedImage对象
            BufferedImage image = ImageIO.read(inputStream);

            // 创建LuminanceSource对象，用于表示图像的亮度信息
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
            // 创建Binarizer对象，用于将亮度信息二值化
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // 创建MultiFormatReader对象，用于读取二维码
            MultiFormatReader reader = new MultiFormatReader();
            // 解码二维码
            Result result = reader.decode(bitmap);

            // 返回解码结果
            return result.getText();
        } catch (IOException e) {
            log.error("IO异常:{}", e.getMessage());
            return "IO异常";
        } catch (NotFoundException e) {
            log.error("二维码解析失败:{}", e.getMessage());
            return "二维码解析失败";
        }
    }

}