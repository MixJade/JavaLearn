package jade.model;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class SkinSnake extends KeyAdapter {
    private final LinkedList<FruitSnake> body = new LinkedList<>();
    private int direction = KeyEvent.VK_DOWN;

    public SkinSnake() {
        for (int i = 200; i < 260; i += 10) {
            body.add(new FruitSnake(i, 100));
        }
    }

    public void init() {
        direction = KeyEvent.VK_DOWN;
        body.clear();
        for (int i = 200; i < 260; i += 10) {
            body.add(new FruitSnake(i, 100));
        }
    }

    private int getDirection() {
        return direction;
    }

    private void setDirection(int direction) {
        this.direction = direction;
    }

    public LinkedList<FruitSnake> getBody() {
        return body;
    }

    public void move() {
        //比如向下拐弯
        // 填头  删尾
        move0();
        body.removeLast();
    }

    private void move0() {
        FruitSnake first = body.getFirst();
        switch (direction) {
            case KeyEvent.VK_UP -> body.addFirst(new FruitSnake(first.getX(), first.getY() - 10));
            case KeyEvent.VK_DOWN -> body.addFirst(new FruitSnake(first.getX(), first.getY() + 10));
            case KeyEvent.VK_LEFT -> body.addFirst(new FruitSnake(first.getX() - 10, first.getY()));
            case KeyEvent.VK_RIGHT -> body.addFirst(new FruitSnake(first.getX() + 10, first.getY()));
        }
    }

    /*判断蛇是否活着*/
    public boolean isAlive() {
        // 判断 撞墙
        FruitSnake first = body.getFirst();
        int x = first.getX();
        int y = first.getY();
        if (x < 0 || x > 570 || y < 0 || y > 350) {
            return false;
        }
        // 判断 吃到自己身体了
        for (int i = 3; i < body.size(); i++) {
            FruitSnake grid = body.get(i);
            if (x == grid.getX() && y == grid.getY()) {
                return false;
            }
        }
        return true;
    }

    /*判断 蛇头 是否和食物 重合  重了返回true  不重合返回false*/
    public boolean eatAble(FruitSnake food) {
        FruitSnake first = body.getFirst();
        return first.getX() == food.getX() && first.getY() == food.getY();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //获取 键盘按下的键。
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (getDirection() != KeyEvent.VK_DOWN)
                    setDirection(KeyEvent.VK_UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (getDirection() != KeyEvent.VK_UP)
                    setDirection(KeyEvent.VK_DOWN);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (getDirection() != KeyEvent.VK_RIGHT)
                    setDirection(KeyEvent.VK_LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (getDirection() != KeyEvent.VK_LEFT)
                    setDirection(KeyEvent.VK_RIGHT);
                break;
        }
    }
}
