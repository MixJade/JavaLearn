package jade.recognition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

class SkinSnake {
    private final LinkedList<FruitSnake> body = new LinkedList<>();
    private int direction = KeyEvent.VK_DOWN;

    public SkinSnake() {
        for (int i = 200; i < 260; i += 10) {
            body.add(new FruitSnake(i, 100));
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
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
}

class FruitSnake {
    private int x;
    private int y;

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

public class SnakeWindow {
    private final SkinSnake snake = new SkinSnake(); //蛇
    private final FruitSnake food = new FruitSnake(); //食物
    private JFrame SNAKE = new JFrame("贪吃蛇-上下左右键");
    private JPanel jp;
    private int score;
    private int eject = 0;

    public SnakeWindow() {
        init();
        repaintEveryPeriod();// 开计时器。
        initDirectionListener();
        food.randomXY(); // 食物获取随机坐标
        SNAKE.setVisible(true);
        SNAKE.setBounds(200, 160, 600, 401);
        SNAKE.setDefaultCloseOperation(SNAKE.DISPOSE_ON_CLOSE);
        SNAKE.setResizable(false);
    }

    private void init() {
        jp = new JPanel() {
            @Override
            public void paint(Graphics g) {
                //先清理掉 之前画的内容
                g.clearRect(0, 0, 600, 401);
                //绘制横线
                for (int i = 0; i < 37; i++) {
                    g.drawLine(0, i * 10, 580, i * 10);
                }
                //绘制竖线
                for (int i = 0; i < 59; i++) {
                    g.drawLine(i * 10, 0, i * 10, 360);
                }
                //绘制食物
                g.fillRect(food.getX(), food.getY(), 10, 10);
                //获取蛇
                LinkedList<FruitSnake> body = snake.getBody();
                g.setColor(Color.RED);
                //绘制蛇头
                g.fillRect(body.getFirst().getX(), body.getFirst().getY(), 10, 10);
                g.setColor(Color.BLACK);
                //遍历集合 绘制蛇身。
                for (int i = 1; i < body.size(); i++) {
                    g.fillRect(body.get(i).getX(), body.get(i).getY(), 10, 10);
                }
            }
        };
        jp.setBounds(0, 0, 600, 401);
        SNAKE.add(jp);
    }

    private void repaintEveryPeriod() {
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                FruitSnake last = snake.getBody().getLast();
                snake.move();
                if (snake.eatAble(food)) {
                    // 吃食物
                    snake.getBody().addLast(last);
                    food.randomXY();
                    score++;
                }
                jp.repaint();
                // 判断蛇是否还活着  死了 就停止线程
                boolean alive = snake.isAlive();
                if (!alive) {
                    String msg = "您的得分是：" + score + "\n是否重开?";
                    eject = JOptionPane.showConfirmDialog(null, msg, "你已经嗝屁了", JOptionPane.YES_NO_OPTION);
                    if (eject == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(new JFrame(), "已重开");
                        reSnake();
                    } else {
                        SNAKE.dispose(); // 关闭窗口
                        SNAKE = null; // 及时清空，以免复活
                    }
                    t.cancel(); // 取消定时器任务，很重要
                }
            }
        };
        t.schedule(task, 0, 200);
    }

    public void reSnake() {
        SNAKE.dispose();
        SNAKE = null;
        new SnakeWindow();
    }

    private void initDirectionListener() {
        SNAKE.addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        //获取 键盘按下的键。
                        int keyCode = e.getKeyCode();
                        switch (keyCode) {
                            case KeyEvent.VK_UP:
                                if (snake.getDirection() != KeyEvent.VK_DOWN)
                                    snake.setDirection(KeyEvent.VK_UP);
                                break;
                            case KeyEvent.VK_DOWN:
                                if (snake.getDirection() != KeyEvent.VK_UP)
                                    snake.setDirection(KeyEvent.VK_DOWN);
                                break;
                            case KeyEvent.VK_LEFT:
                                if (snake.getDirection() != KeyEvent.VK_RIGHT)
                                    snake.setDirection(KeyEvent.VK_LEFT);
                                break;
                            case KeyEvent.VK_RIGHT:
                                if (snake.getDirection() != KeyEvent.VK_LEFT)
                                    snake.setDirection(KeyEvent.VK_RIGHT);
                                break;
                        }
                    }
                });
    }
}