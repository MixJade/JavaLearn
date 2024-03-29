package jade.utils;

import jade.consts.ImgEnum;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 随机选择
 */
public interface MyUtil {
    /**
     * 随机选择一个图片
     *
     * @param imgEnums 对应图片的枚举，多个参数
     * @return 所选择的图片
     */
    static BufferedImage choice(ImgEnum... imgEnums) {
        Random random = new Random();
        return imgEnums[random.nextInt(imgEnums.length)].getImg();
    }
}
