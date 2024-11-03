package jade.show;

import jade.consts.GameConst;
import jade.consts.ImgEnum;
import jade.consts.MusicEnum;
import jade.modal.*;
import jade.utils.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义JPanel类的子类
 */
public class GamePanel extends JPanel {

    private BackGround bg; // 声明背景对象
    private Floor floor; // 声明背景对象
    private Bird bird;// 声明小鸟对象
    private Pipe pipe1, pipe2; // 声明管道对象
    private Score score; // 声明分数对象
    private GameState state = GameState.NO_BEGIN; // 游戏状态
    private final BufferedImage beginImg, endImg;

    /*
     * 构造函数
     */
    public GamePanel() {
        // 设置开始图片与结束图片
        beginImg = ImgEnum.GAME_STAR.getImg();
        endImg = ImgEnum.GAME_OVER.getImg();
        initGame();
    }

    /**
     * 初始化游戏
     */
    private void initGame() {
        // 单例模式声明背景对象和地面对象
        bg = new BackGround();
        floor = new Floor();
        // 声明小鸟对象
        bird = new Bird();
        // 声明两个柱子，并分别设置柱子的起始X坐标
        pipe1 = new Pipe(GameConst.WIDTH);
        pipe2 = new Pipe(GameConst.WIDTH + GameConst.PIPE_DISTANCE);
        // 声明分数
        score = new Score();
        // 设置游戏未开始
        state = GameState.NO_BEGIN;
    }

    /**
     * 当前面板中绘制组件（加载图片等）
     * paint方法会在初始化以及最小和最大化时自动调用该方法
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Graphics对象绘制背景图案
        g.drawImage(bg.getImg(), 0, 0, null);
        // 判断游戏状态
        switch (state) {
            case NO_BEGIN -> {
                // 开始界面
                g.drawImage(beginImg, 58, 77, null);
                // 开始界面要绘制小鸟
                g.drawImage(bird.getImg(), bird.getX(), bird.getY(), null);
            }
            case ING -> {
                // 绘制小鸟
                g.drawImage(bird.getImg(), bird.getX(), bird.getY(), null);
                // 绘制柱子
                g.drawImage(pipe1.getImg(), pipe1.getX(), pipe1.getY(), null);
                g.drawImage(pipe1.getTranImg(), pipe1.getX(), pipe1.getTranY(), null);
                g.drawImage(pipe2.getImg(), pipe2.getX(), pipe2.getY(), null);
                g.drawImage(pipe2.getTranImg(), pipe2.getX(), pipe2.getTranY(), null);
                // 绘制分数
                for (int i = 0; i < score.getScore().length; i++) {
                    g.drawImage(score.getScore()[i], score.getX(i), score.getY(), null);
                }
            }
            case END -> {
                g.drawImage(endImg, 58, 77, null);
                // 绘制分数
                for (int i = 0; i < score.getScore().length; i++) {
                    g.drawImage(score.getScore()[i], score.getX(i), 150, null);
                }
            }
        }
        // 绘制地面
        g.drawImage(floor.getImg(), floor.getX(), floor.getY(), null);
    }

    /**
     * 设置鼠标监听并开始游戏
     */
    public void action() {
        // 设置鼠标监听，点击鼠标让鸟升天
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                setGameState();
            }
        });
        // 设置键盘监听事件，按下空格、K、向上键都能让鸟上升
        // 按下ESC可以退出游戏
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP, KeyEvent.VK_K, KeyEvent.VK_SPACE -> bird.up();
                    case KeyEvent.VK_ESCAPE -> System.exit(0);
                }
                setGameState();
            }
        });
        begin();
    }

    /**
     * 键盘与鼠标一起设置游戏状态
     */
    private void setGameState() {
        switch (state) {
            case NO_BEGIN -> {
                MusicPlayer.music(MusicEnum.BEGIN);
                state = GameState.ING;
                bird.setXY();
            }
            case ING -> {
                bird.up();
                pipe1.move();
                pipe2.move();
            }
            case END -> {
                initGame();
                begin();
            }
        }
    }

    /**
     * 游戏开始
     */
    private void begin() {
        System.out.println("游戏开始！");
        // 游戏开始，设置定时器
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            int scoreNum = 0; // 设置计数器，触发五次加分后，提高难度

            @Override
            public void run() {
                floor.move(); // 地面移动
                bird.fly(); // 小鸟起飞
                repaint();
                switch (state) {
                    case ING -> {
                        bird.down();
                        pipe1.move();
                        pipe2.move();
                        if (bird.hitEdge() || bird.hitPip(pipe1) || bird.hitPip(pipe2)) {
                            MusicPlayer.music(MusicEnum.HIT);
                            MusicPlayer.music(MusicEnum.DIE);
                            state = GameState.END; // 游戏结束
                            t.cancel();
                        } else if (bird.isScore(pipe1) || bird.isScore(pipe2)) {
                            score.add(); // 分数增加
                            scoreNum++; // 加分到一定程度会提高难度
                            if (GameConst.UP_SPEED_LEVEL.contains(scoreNum)) {
                                System.out.println("速度提升，第" + scoreNum + "分 ");
                                pipe1.speedUp();
                                pipe2.speedUp();
                                floor.speedUp();
                            }
                        }
                    }
                    case END -> t.cancel();
                }
            }
        };
        t.schedule(task, 0, 30);
    }
}

enum GameState {
    NO_BEGIN,// 游戏未开始
    ING, // 游戏进行中
    END // 游戏结束
}
