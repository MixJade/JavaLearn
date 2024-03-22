package gameUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Noita(女巫)的坐标图
 *
 * @since 2023年6月23日 21:39
 */
@SuppressWarnings("SpellCheckingInspection")
public class NoitaMap {
    public static void main(String[] args) {
        // 定义坐标点及其描述信息
        List<Coordinate> coordinates = readCSV();
        generateImage(coordinates, "Noita地图.png");
    }

    /**
     * 从csv文件中读取坐标点对象
     *
     * @return 关键坐标点的列表
     */
    private static List<Coordinate> readCSV() {
        String line;
        String csvSplitBy = ",";
        List<Coordinate> coordinateList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/gameUtil/NoitaDot.csv"))) {
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // 跳过第一行
                }
                String[] fields = line.split(csvSplitBy);
                String name = fields[0].trim();
                int x = Integer.parseInt(fields[1].trim());
                int y = Integer.parseInt(fields[2].trim());
                Coordinate coordinate = new Coordinate(name, x, y);
                coordinateList.add(coordinate);
            }
            coordinateList.remove(0); // 去掉第一行
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordinateList;
    }

    /**
     * 画Noita的坐标图
     *
     * @param coordinates 坐标对象列表
     * @param outName     输出图片名字
     */
    public static void generateImage(List<Coordinate> coordinates, String outName) {
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

    /**
     * 坐标对象
     *
     * @param des 坐标名
     * @param x   x坐标
     * @param y   y坐标
     */
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
}
