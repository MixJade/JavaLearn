package gameUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Nota(女巫)的坐标图
 *
 * @since 2023年6月23日 21:39
 */
public class CoordinateMap {

    public static void main(String[] args) {
        // 定义坐标点及其描述信息
        Coordinate[] coordinates = {
                new Coordinate("出生地", 227, -79),
                new Coordinate("魔塔奖励", 9992, 4372),
                new Coordinate("巫师巢穴魔球", 10511, 16256),
                new Coordinate("古代法师", -4871, 923),
                new Coordinate("遗忘者", -11544, 13185),
                new Coordinate("奶酪月", 264, -25839),
                new Coordinate("结局房间", 6463, 15173),
                new Coordinate("祭坛", 817, -1149),
                new Coordinate("水精髓", -5384, 16660),
                new Coordinate("气精髓", -13084, -5355),
                new Coordinate("传送门房间", 3836, 7414),
                new Coordinate("陶笛", -9994, -6400),
                new Coordinate("积雪深渊魔球", -9142, 14660),
                new Coordinate("湖泊小岛", -14228, 212),
                new Coordinate("珊瑚宝箱", 11470, -4863),
                new Coordinate("天赋祭坛", 14089, 7563),
                new Coordinate("地狱魔球", -257, 16257),
                new Coordinate("地精髓", 16130, -1771),
                new Coordinate("天平左侧", 12876, 59),
                new Coordinate("暗月", 464, 37844),
                new Coordinate("酒精髓", -14228, 13588),
                new Coordinate("三眼", 3523, 13111),
        };
        generateImage(coordinates, "Nota地图.png");
    }

    public static void generateImage(Coordinate[] coordinates, String outName) {
        int width = 38000 >> 6, height = 66000 >> 6; // 图片的宽、高
        int leftX = 14500 >> 6, topY = 26800 >> 6; // 坐标系的原点
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        // 设置背景色并填充图片
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 设置坐标轴
        g.translate(leftX, topY);
        g.setColor(Color.GRAY);
        g.drawLine(-leftX, 0, width - leftX, 0);
        g.drawLine(0, -topY, 0, height - topY);
        // 设置绘制颜色并绘制坐标点和描述
        g.setColor(Color.BLACK);
        g.setFont(new Font("宋体", Font.PLAIN, 12));
        FontMetrics fm = g.getFontMetrics();
        for (Coordinate coords : coordinates) {
            g.drawString(coords.tDes(), coords.tX(fm.stringWidth(coords.des())), coords.tY());
        }
        g.drawString("平行世界宽度约35800", 4000 >> 6, -13000 >> 6);
        g.drawRect(4000 >> 6, -13800 >> 6, 8000 >> 6, 1000 >> 6);
        // 绘制坐标点
        g.setColor(Color.RED);
        for (Coordinate coords : coordinates) {
            g.drawLine(coords.x() + 1, coords.y() + 1, coords.x() - 1, coords.y() - 1);
            g.drawLine(coords.x() - 1, coords.y() + 1, coords.x() + 1, coords.y() - 1);
        }
        // 保存图像到输出路径
        try {
            String desktop = System.getProperty("user.home") + "\\Desktop\\";
            ImageIO.write(image, "PNG", new File(desktop + outName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("图片生成成功，请前往桌面查看!");
    }
}

record Coordinate(String des, int x, int y) {
    public String tDes() {
        return des + "(" + x + "," + y + ")";
    }

    @Override
    public int x() {
        return x >> 6;
    }

    @Override
    public int y() {
        return y >> 6;
    }

    public int tX(int strLen) {
        return (x - strLen / 2) >> 6;
    }

    public int tY() {
        return (y >> 6) + 5;
    }

}