package operateFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResizeImage {
    public static void main(String[] args) {
        String path = "src/main/resources/operateFile/testResize.jpg";  // 图片的路径
        int width = 128;  // 新的宽度
        int height = 128;  // 新的高度
        // 注意：这个方法只能将图片处理为正方形(因为裁剪时考虑不妥当)
        boolean resizeSuc = new ResizeImage().resize(path, width, height);
        System.out.println("转化" + (resizeSuc ? "成功" : "失败"));
    }

    /**
     * 重写图片的尺寸
     *
     * @param path 图片路径
     * @param newW 新的宽度
     * @param newH 新的高度
     * @return 是否转化成功
     */
    private boolean resize(String path, int newW, int newH) {
        String formatName = path.substring(path.lastIndexOf(".") + 1);
        System.out.println("文件后缀是：" + formatName);
        File input = new File(path);
        try {
            BufferedImage img = ImageIO.read(input);
            // 裁剪图片
            int size = Math.min(img.getWidth(), img.getHeight());
            BufferedImage subImage = img.getSubimage(0, 0, size, size);
            // 开始转化图片
            Image tmp = subImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(newW, newH, subImage.getType());
            Graphics2D g2d = resized.createGraphics();
            boolean drawImage = g2d.drawImage(tmp, 0, 0, null);
            System.out.println("图片处理：转化" + (drawImage ? "成功" : "失败"));
            g2d.dispose();
            // 转化完成，将图片转存回去
            ImageIO.write(resized, formatName, input);
            return true;
        } catch (IOException e) {
            System.out.println("图片读取失败");
            return false;
        }
    }
}