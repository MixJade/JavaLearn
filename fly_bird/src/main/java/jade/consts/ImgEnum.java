package jade.consts;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum ImgEnum {
    BACKGROUND_DAY("day.png"), // 白背景
    BACKGROUND_NIGHT("night.png"), // 黑背景
    FLOOR("floor.png"), // 地面
    GAME_OVER("game-over.png"), // 游戏结束图片
    GAME_STAR("guide.png"), // 游戏开始图片
    RED_PIPE("red-pipe.png"), // 红色管道
    GREEN_PIPE("green-pipe.png"); // 绿色管道
    private final String imgName;

    ImgEnum(String imgName) {
        this.imgName = imgName;
    }

    public BufferedImage getImg() {
        try {
            return ImageIO.read(new File(GameConst.IMG_PATH + imgName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
