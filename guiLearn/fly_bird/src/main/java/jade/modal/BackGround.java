package jade.modal;

import jade.consts.ImgEnum;
import jade.utils.MyUtil;

import java.awt.image.BufferedImage;

/**
 * step1：背景类
 */
public class BackGround {
    private final BufferedImage img;// 背景图片

    public BackGround() {
        img = MyUtil.choice(ImgEnum.BACKGROUND_DAY, ImgEnum.BACKGROUND_NIGHT);
    }

    public BufferedImage getImg() {
        return img;
    }
}
