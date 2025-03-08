package jade.consts;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public enum ImgEnum {
    BACKGROUND_DAY("day.png"), // 白背景
    BACKGROUND_NIGHT("night.png"), // 黑背景
    FLOOR("floor.png"), // 地面
    GAME_OVER("game-over.png"), // 游戏结束图片
    GAME_STAR("guide.png"), // 游戏开始图片
    RED_PIPE("red-pipe.png"), // 红色管道
    GREEN_PIPE("green-pipe.png"), // 绿色管道
    ZERO("0.png"),
    ONE("1.png"),
    TWO("2.png"),
    THREE("3.png"),
    FOUR("4.png"),
    FIVE("5.png"),
    SIX("6.png"),
    SEVEN("7.png"),
    EIGHT("8.png"),
    NINE("9.png"),
    BLUE_DOWN("blue-down.png"),
    BLUE_MID("blue-mid.png"),
    BLUE_UP("blue-up.png"),
    RED_DOWN("red-down.png"),
    RED_MID("red-mid.png"),
    RED_UP("red-up.png"),
    YELLOW_DOWN("yellow-down.png"),
    YELLOW_MID("yellow-mid.png"),
    YELLOW_UP("yellow-up.png");
    private final String imgName;

    ImgEnum(String imgName) {
        this.imgName = imgName;
    }

    public BufferedImage getImg() {
        try (InputStream in = getClass().getResourceAsStream("/picture/" + imgName)) {
            if (in != null) {
                return ImageIO.read(in);
            } else {
                System.err.println("未读取到图片");
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
