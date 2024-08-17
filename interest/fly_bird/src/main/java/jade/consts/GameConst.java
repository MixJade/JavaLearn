package jade.consts;

public interface GameConst {
    int MOVE_SPEED = 1, // 地面及柱子移动初始速度。
            UP_GAP = 5, // 等级提高的分数间隔
            MAX_LEVEL = UP_GAP * 5; // 最大速度时的分数
    // 界面参数
    String TITLE = "老鸟管道";
    int WIDTH = 300,
            HEIGHT = 533;
    // 地面的高度，可以在地面类中打印
    int FLOOR_HEIGHT = 112;
    // 小鸟的参数
    int BIRD_NO_BEGIN_X = 133, // 小鸟初始坐标，与开始界面的灰色小鸟重合
            BIRD_NO_BEGIN_Y = 247,
            BIRD_BEGIN_X = 60, // 小鸟起始坐标
            BIRD_BEGIN_Y = 213,
            DISTANCE_UP = 10;// 每点一次鼠标或键盘，移动的位置
    double G = 9.8; // 重力加速度
    // 柱子参数
    int PIPE_GAP = 120, // 管道通道距离
            PIPE_DISTANCE = 160; // 管道间隔，即每244间距产生一根柱子
}
