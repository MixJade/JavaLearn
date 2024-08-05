package someUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

interface ImgConfig {
    String IMG_PATH = "unusedFile/"; // 验证码图片保存路径
    int WIDTH = 90, HEIGHT = 20; // 定义图片的宽高
    int CODE_COUNT = 5; // 定义图片上显示验证码的个数
    int FONT_HEIGHT = 18; // 字体高度
    char[] CODE_SEQUENCE = {'A', 'B', 'C', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
}

/**
 * 从网上找到生成验证码的程序
 */
public class CheckCodeUtils {
    /**
     * 生成一个map集合
     * code为生成的验证码
     * codePic为生成的验证码BufferedImage对象
     */
    public static Map<String, Object> generateCodeAndPic() {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(ImgConfig.WIDTH, ImgConfig.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics gd = buffImg.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, ImgConfig.WIDTH, ImgConfig.HEIGHT);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Times New Roman", Font.BOLD, ImgConfig.FONT_HEIGHT);
        // 设置字体。
        gd.setFont(font);

        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, ImgConfig.WIDTH - 1, ImgConfig.HEIGHT - 1);

        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(ImgConfig.WIDTH);
            int y = random.nextInt(ImgConfig.HEIGHT);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red, green, blue;

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < ImgConfig.CODE_COUNT; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(ImgConfig.CODE_SEQUENCE[random.nextInt(ImgConfig.CODE_SEQUENCE.length)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * 15, 16);

            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        Map<String, Object> map = new HashMap<>();
        //存放验证码
        map.put("code", randomCode);
        //存放生成的验证码BufferedImage对象
        map.put("codePic", buffImg);
        return map;
    }

    public static void main(String[] args) throws Exception {
        //创建文件输出流对象
        OutputStream out = new FileOutputStream(ImgConfig.IMG_PATH + System.currentTimeMillis() + ".jpg");
        Map<String, Object> map = CheckCodeUtils.generateCodeAndPic();
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", out);
        System.out.println("验证码的值为：" + map.get("code"));
        System.out.println("文件已输出至：" + ImgConfig.IMG_PATH);
    }
}

