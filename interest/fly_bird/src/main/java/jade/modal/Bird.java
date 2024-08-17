package jade.modal;

import jade.consts.GameConst;
import jade.consts.ImgEnum;
import jade.consts.MusicEnum;
import jade.utils.MusicPlayer;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 小鸟类
 */
public class Bird {
    private BufferedImage img;// 小鸟当前图片
    private final BufferedImage[] images; // 小鸟状态图片
    private final int[] frames = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1};// 当前皮肤的序号
    private int index = 0; // 当前状态索引
    private final int weight, height; // 小鸟的宽高
    private int x, y;// 小鸟的初始坐标

    private double v = 0; // 小鸟上升速度

    /**
     * 获取小鸟的图片
     *
     * @return 小鸟翅膀在上方、中间、下方的图片
     */
    private static BufferedImage[] getBie() {
        BufferedImage[] bufferedImages = new BufferedImage[3];
        Random random = new Random();
        int color = random.nextInt(3);
        switch (color) {
            case 0 -> {
                bufferedImages[0] = ImgEnum.RED_UP.getImg();
                bufferedImages[1] = ImgEnum.RED_MID.getImg();
                bufferedImages[2] = ImgEnum.RED_DOWN.getImg();
            }
            case 1 -> {
                bufferedImages[0] = ImgEnum.BLUE_UP.getImg();
                bufferedImages[1] = ImgEnum.BLUE_MID.getImg();
                bufferedImages[2] = ImgEnum.BLUE_DOWN.getImg();
            }
            case 2 -> {
                bufferedImages[0] = ImgEnum.YELLOW_UP.getImg();
                bufferedImages[1] = ImgEnum.YELLOW_MID.getImg();
                bufferedImages[2] = ImgEnum.YELLOW_DOWN.getImg();
            }
        }
        return bufferedImages;
    }

    public Bird() {
        images = getBie();
        img = images[1];
        // 获取小鸟的宽高
        weight = img.getWidth();
        height = img.getHeight();
        // 初始化小鸟的坐标位置
        x = GameConst.BIRD_NO_BEGIN_X;
        y = GameConst.BIRD_NO_BEGIN_Y;
    }

    /**
     * 小鸟飞翔的图片切换
     */
    public void fly() {
        // 小鸟翅膀切换
        if (index == frames.length - 1) {
            index = 0;
        } else index++;
        img = images[frames[index]];
    }

    /**
     * 小鸟自然下降
     */
    public void down() {
        // 小鸟自然下降的时长
        double t = 0.18;
        v -= GameConst.G * t; // 末速度Vt＝Vo-gt
        y -= (int) (v * t - GameConst.G * t * t / 2); // 位移h＝v*t-gt²/2
    }

    /**
     * 上升，点鼠标或点键盘向上键
     */
    public void up() {
        v = 20;
        y -= GameConst.DISTANCE_UP;
        MusicPlayer.music(MusicEnum.FLY);
    }

    /**
     * 游戏开始，设置xy值
     */
    public void setXY() {
        this.x = GameConst.BIRD_BEGIN_X;
        this.y = GameConst.BIRD_BEGIN_Y;
    }

    /**
     * 检查是否碰到地面或者天空
     *
     * @return 碰到地面或天空为真
     */
    public boolean hitEdge() {
        return (y + height >= (GameConst.HEIGHT - GameConst.FLOOR_HEIGHT)) || y < 0;
    }

    /**
     * 碰到管道时的检测
     *
     * @param p 管道对象
     * @return 碰到为真
     */
    public boolean hitPip(Pipe p) {
        // x方向小鸟和柱子碰撞的条件
        return p.inPipe(x, y, weight, height);
    }

    /**
     * 通过柱子增加积分
     *
     * @param p 柱子对象
     * @return 加分为真
     */
    public boolean isScore(Pipe p) {
        return x >= p.getRightX() && x < p.getRightX() + p.getSpeed();
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
}
