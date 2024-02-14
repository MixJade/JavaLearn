package jade.modal;

import jade.consts.GameConst;
import jade.consts.ImgEnum;
import jade.utils.MyUtil;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 管道类
 */
public class Pipe {
    private final BufferedImage img;// 图片
    private int x, y;// 坐标
    private final int width, height;// 柱子宽高
    private final Random random = new Random();// 一个生成随机数的对象
    private int speed = GameConst.MOVE_SPEED;

    public Pipe(int x) {
        img = MyUtil.choice(ImgEnum.RED_PIPE, ImgEnum.GREEN_PIPE);
        width = img.getWidth();
        height = img.getHeight();
        this.x = x;
        y = random.nextInt((int) (GameConst.HEIGHT * 0.3), (int) (GameConst.HEIGHT * 0.7));
    }

    // 柱子移动，游戏一旦开始则柱子移动
    public void move() {
        x -= speed;
        if (x <= -width) {
            x = GameConst.WIDTH;
            y = random.nextInt((int) (GameConst.HEIGHT * 0.3), (int) (GameConst.HEIGHT * 0.7));
        }
    }

    /**
     * 检查一个坐标是否在柱子内
     *
     * @param bx 检测物x坐标
     * @param by 检测物y坐标
     * @param bw 检测物的宽度
     * @param bh 检测物的高度
     * @return 在柱子内为真
     */
    public boolean inPipe(int bx, int by, int bw, int bh) {
        if (bx + bw > x && bx < getRightX()) {
            return by < (y - GameConst.PIPE_GAP) || by + bh > y;
        } else return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * 获得翻转后的Y坐标
     *
     * @return 翻转后的y坐标
     */
    public int getTranY() {
        return y - GameConst.PIPE_GAP - height;
    }


    /**
     * 获取柱子最右边的坐标
     *
     * @return 柱子最右边的坐标
     */
    public int getRightX() {
        return width + x;
    }

    public BufferedImage getImg() {
        return img;
    }

    /**
     * 将图片上下翻转
     *
     * @return 翻转后的管道图片
     */
    public BufferedImage getTranImg() {
        AffineTransform transform = AffineTransform.getScaleInstance(1.0, -1.0);
        transform.translate(0, -img.getHeight());
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage flippedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(img, flippedImage);
        return flippedImage;
    }

    public void speedUp() {
        this.speed++;
    }

    public int getSpeed() {
        return speed;
    }
}