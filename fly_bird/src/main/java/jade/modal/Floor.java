package jade.modal;

import jade.consts.GameConst;
import jade.consts.ImgEnum;

import java.awt.image.BufferedImage;

/**
 * 地面类
 */
public class Floor {
    private final BufferedImage img; // 地面图片
    private int x; // 地面的横坐标
    private final int y; // 地面绘制的起始坐标
    private final int width; // 地面宽度
    private int speed = GameConst.MOVE_SPEED;

    public Floor() {
        // ImageIO用于加载图片资源
        img = ImgEnum.FLOOR.getImg();
        // 获取地面图片的长度和高度
        width = img.getWidth();// 获取图片的宽度
        // 地面的宽、高
        int height = img.getHeight();// 获取图片的宽度
        x = 0;
        y = GameConst.HEIGHT - height;// 背景高度与地面图片高度的差值就是地面图片的起始Y坐标
    }

    // 地面移动
    public void move() {
        x -= speed;
        // 9为修正值，根据地面移动效果调整该数值，保证图片移动自然流畅。是地面图片中条纹间距的一半数值。
        if (x < GameConst.WIDTH + 12 - width) {
            x = 0;
        }
    }

    public BufferedImage getImg() {
        return img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void speedUp() {
        this.speed++;
    }
}
