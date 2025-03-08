package jade.model;

import java.util.Random;

public class FruitSnake {
    private int x, y;

    public FruitSnake() {
    }

    public FruitSnake(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void randomXY() {
        Random r = new Random();
        this.x = r.nextInt(58) * 10;  //[0-57]
        this.y = r.nextInt(36) * 10;  //[0-35]
    }
}
