package jade.view;

import jade.model.FruitSnake;
import jade.model.SkinSnake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeWindow extends JFrame {
    private final SkinSnake snake = new SkinSnake(); //蛇
    private final FruitSnake food = new FruitSnake(); //食物
    private JPanel jp;
    private int score;

    public SnakeWindow() {
        // 设置图标
        try (InputStream favor = getClass().getResourceAsStream("../th006.jpg")) {
            if (favor != null) setIconImage(new ImageIcon(ImageIO.read(favor)).getImage());
        } catch (IOException ignored) {
        }
        setTitle("贪吃蛇-上下左右键");
        init();
        repaintEveryPeriod();// 开计时器。
        addKeyListener(snake);
        food.randomXY(); // 食物获取随机坐标
        setVisible(true);
        setBounds(200, 160, 600, 401);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
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
        add(jp);
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
                    int eject = JOptionPane.showConfirmDialog(null, msg, "你已经嗝屁了", JOptionPane.YES_NO_OPTION);
                    if (eject == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "已重开");
                        reSnake();
                    } else {
                        dispose(); // 关闭窗口
                    }
                    t.cancel(); // 取消定时器任务，很重要
                }
            }
        };
        t.schedule(task, 0, 200);
        // 设置退出时取消定时器
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                t.cancel();
            }
        });
    }

    public void reSnake() {
        score = 0;
        remove(jp);
        snake.init();
        init();
        repaintEveryPeriod();// 开计时器。
    }
}