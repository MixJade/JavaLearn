package jade.modal;

import jade.consts.GameConst;
import jade.consts.MusicEnum;
import jade.utils.MusicPlayer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Score {
    private int scoreNum = 0; // 初始分数
    private int x;
    private final BufferedImage[] IMG = new BufferedImage[10];

    public Score() {
        try {
            for (int i = 0; i < 10; i++) {
                IMG[i] = ImageIO.read(new File(GameConst.IMG_PATH + i + ".png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取分数的图片形式
     *
     * @return 分数的图片形式
     */
    public BufferedImage[] getScore() {
        String numStr = Integer.toString(scoreNum);
        BufferedImage[] outImg = new BufferedImage[numStr.length()];
        // 将数字转为图片
        for (int i = 0; i < numStr.length(); i++) {
            outImg[i] = IMG[Character.getNumericValue(numStr.charAt(i))];
        }
        // 设置图片相应的x坐标
        int w = IMG[0].getWidth() + 3;  //数字之间的间距
        x = (GameConst.WIDTH - numStr.length() * w) / 2;
        return outImg;
    }

    /**
     * 加分
     */
    public void add() {
        MusicPlayer.music(MusicEnum.SCORE);
        scoreNum += 1;
    }

    /**
     * 分数的x坐标，根据分数的位数进行改变
     *
     * @param i 循环的次数，从0开始
     * @return x坐标
     */
    public int getX(int i) {
        return x + i * (IMG[0].getWidth() + 3);
    }

    /**
     * 获取分数的y坐标
     *
     * @return 屏幕高度的十分之一
     */
    public int getY() {
        return (int) (GameConst.HEIGHT * 0.1);
    }
}
