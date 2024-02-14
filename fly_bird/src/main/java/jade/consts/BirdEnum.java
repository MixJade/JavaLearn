package jade.consts;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public enum BirdEnum {
    RED("red"), BLUE("blue"), YELLOW("yellow");


    private final String color;

    public static BirdEnum randomBird() {
        BirdEnum[] birdEnums = {RED, BLUE, YELLOW};
        Random random = new Random();
        return birdEnums[random.nextInt(birdEnums.length)];
    }

    BirdEnum(String color) {
        this.color = color;
    }

    public BufferedImage[] getBie() {
        String[] state = {"-up.png", "-mid.png", "-down.png"};
        BufferedImage[] bufferedImages = new BufferedImage[3];
        try {
            for (int i = 0; i < state.length; i++) {
                bufferedImages[i] = ImageIO.read(new File(GameConst.IMG_PATH + color + state[i]));
            }
            return bufferedImages;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
