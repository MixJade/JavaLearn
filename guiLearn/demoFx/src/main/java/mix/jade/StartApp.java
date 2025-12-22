package mix.jade;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StartApp extends Application {
    private static Scene scene_1, scene_2, scene_3;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        //缓存主Stage
        stage = primaryStage;
        // 设置图标
        primaryStage.getIcons().add(new Image("v.png"));

        // 设置场景
        switchScene(1);

        primaryStage.show(); // 让窗口出现必须调show
    }

    /**
     * 切换场景
     *
     * @param sceneOrder 场景顺序
     */
    public static void switchScene(int sceneOrder) {
        try {
            switch (sceneOrder) {
                case 1 -> {
                    if (scene_1 == null) {
                        System.out.println("场景1初始化");
                        FXMLLoader firstFXML = new FXMLLoader(StartApp.class.getResource("hello-view.fxml"));
                        scene_1 = new Scene(firstFXML.load(), 620, 240);
                    }
                    stage.setScene(scene_1);
                    stage.setTitle("Hello!");
                }
                case 2 -> {
                    if (scene_2 == null) {
                        System.out.println("场景2初始化");
                        FXMLLoader secondFXML = new FXMLLoader(StartApp.class.getResource("world-view.fxml"));
                        scene_2 = new Scene(secondFXML.load(), 300, 275);
                    }
                    stage.setScene(scene_2);
                    stage.setTitle("Second Scene");
                }
                case 3 -> {
                    if (scene_3 == null) {
                        System.out.println("场景3初始化");
                        FXMLLoader secondFXML = new FXMLLoader(StartApp.class.getResource("panel-view.fxml"));
                        scene_3 = new Scene(secondFXML.load(), 600, 400);
                    }
                    stage.setScene(scene_3);
                    stage.setTitle("Scene_3");
                }
            }
        } catch (IOException e) {
            System.out.println("场景加载失败");
            throw new RuntimeException(e);
        }
    }

    public static Stage getStage() {
        return stage;
    }

    /**
     * 第一课：窗口宽高相关
     *
     * @apiNote 包括不可改变窗口大小、最大最小宽高、监听窗口尺寸
     * @since 2024-8-17 22:32:44
     * @deprecated
     */
    public void startLesson1(Stage stage) {
        // 设置不可改变窗口大小
        stage.setResizable(false);
        // 可以设置最大的宽高(最大化也不能超过这个高度)
        stage.setMaxHeight(600);
        stage.setMaxWidth(800);
        // 也可以设置最小的宽高
        stage.setMinHeight(200);
        stage.setMinWidth(200);

        // 当然可以监听高度变化
        stage.heightProperty().addListener((observableValue, number, t1) -> {
            System.out.println("旧高度:" + number.doubleValue());
            System.out.println("新高度:" + t1.doubleValue());
        });

        stage.show(); // 让窗口出现必须调show

        // 可以获取宽高，但最好是在show方法之后
        System.out.println("窗口宽度:" + stage.getWidth());
        System.out.println("窗口高度:" + stage.getHeight());
    }

    /**
     * 第二课：窗口坐标相关
     *
     * @apiNote 包括设置透明度、xy坐标、监听坐标变化
     * @since 2024-8-17 22:34:07
     * @deprecated
     */
    public void startLesson2(Stage stage) {
        // 设置透明度(0是完全透明,1是不透明)
        stage.setOpacity(0.6);
        // 设置总是在上面
        stage.setAlwaysOnTop(true);
        // 设置窗口初始坐标
        stage.setX(50);
        stage.setY(50);
        // 监听窗口的xy轴变化(以窗口左上角为基)
        stage.xProperty().addListener((observableValue, number, t1) -> {
            System.out.println("旧x:" + number.doubleValue());
            System.out.println("新x:" + t1.doubleValue());
        });

        stage.show(); // 让窗口出现必须调show
    }

    /**
     * 第三课：设置窗口样式与联动
     *
     * @apiNote 包括窗口样式(没啥用)、父子联动(比如不关闭子窗口别想操控父窗口)
     * @since 2024-8-17 22:50:12
     * @deprecated
     */
    public void startLesson3() {
        // 设置窗口样式:默认样式
        Stage stage1 = new Stage();
        stage1.initStyle(StageStyle.DECORATED); // 默认样式
        stage1.setTitle("stage1");
        stage1.show();

        // 设置窗口样式:完全透明
        Stage stage2 = new Stage();
        stage2.initStyle(StageStyle.TRANSPARENT); // 完全透明(不影响scene)
        stage2.setTitle("stage2");
        // 设置拥有者为第一个窗口
        // 这样stage1关闭会联动stage2关闭，同时系统会认为stage1与2是同一个窗口
        stage2.initOwner(stage1); // 设置拥有者为第一个窗口
        stage2.show();

        // 设置窗口样式:窗口全白
        Stage stage3 = new Stage();
        stage3.initStyle(StageStyle.UNIFIED); // 窗口全白,scene必须要透明不然看不到
        // 设置窗口模式,APPLICATION_MODAL为不关闭这个窗口别想操控其它的
        // 默认为NONE,
        stage3.initModality(Modality.APPLICATION_MODAL);
        stage3.setTitle("stage3");
        stage3.show();

        // 设置窗口样式:完全透明
        Stage stage4 = new Stage();
        stage4.initStyle(StageStyle.UNDECORATED); // 也是完全透明(不影响scene)
        stage4.setTitle("stage4");
        stage4.initOwner(stage3); // 设置拥有者为第3窗口
        stage4.show();

        // 设置窗口样式:特殊版本的window
        Stage stage5 = new Stage();
        stage5.initStyle(StageStyle.UTILITY); // 用于特殊版本的window,在win10上样式不协调
        stage5.setTitle("stage5");
        // 还有WINDOW_MODAL，当子窗口存在时父窗口不能操作(设在子窗口上)
        // 比如stage3的子窗口stage5，在关闭之前不能点stage3(但可以点不相干的stage1)
        stage5.initModality(Modality.WINDOW_MODAL);
        stage5.initOwner(stage3); // 设置拥有者为第3个窗口
        stage5.show();
    }

    /**
     * 第四课：Platform使用
     *
     * @apiNote Platform设置关闭窗口后不退出程序
     * @since 2024-8-18 10:42:33
     * @deprecated
     */
    public void startLesson4(Stage stage) {
        // Platform设置关闭窗口后不退出程序(甚至不会触发生命周期的stop方法),用于隐藏到托盘所用
        Platform.setImplicitExit(false);
        // 想要退出可以使用Platform.exit()
        Platform.exit();

        stage.show(); // 让窗口出现必须调show
    }

    /**
     * 第五课：屏幕类的使用
     *
     * @apiNote 包括获取屏幕dpi、分辨率、可视分辨率
     * @since 2024-8-18 10:54:17
     * @deprecated
     */
    public void startLesson5() {
        // 获取屏幕对象
        Screen screen = Screen.getPrimary();
        // 获取当前屏幕的dpi
        double dpi = screen.getDpi();
        System.out.println("当前屏幕dpi=" + dpi);
        // 获取整个屏幕的对象
        Rectangle2D bounds = screen.getBounds();
        System.out.printf("当前屏幕分辨率：%sx%s%n", bounds.getWidth(), bounds.getHeight());
        System.out.printf("当前屏幕左上角坐标：(%s,%s)%n", bounds.getMinX(), bounds.getMinY());
        System.out.printf("当前屏幕右下角坐标：(%s,%s)%n", bounds.getMaxX(), bounds.getMaxY());
        // 获取当前可看的屏幕对象(会少一点任务栏的)
        Rectangle2D visualBounds = screen.getVisualBounds();
        System.out.printf("当前屏幕可看分辨率：%sx%s%n", visualBounds.getWidth(), visualBounds.getHeight());
        System.out.printf("当前屏幕可看左上角坐标：(%s,%s)%n", visualBounds.getMinX(), visualBounds.getMinY());
        System.out.printf("当前屏幕可看右下角坐标：(%s,%s)%n", visualBounds.getMaxX(), visualBounds.getMaxY());
    }

    /**
     * 第六课：场景类与网络服务使用
     *
     * @apiNote 包括舞台与场景的关系、设置鼠标悬停样式、打开特定网址
     * @since 2024-8-18 10:54:17
     * @deprecated
     */
    public void startLesson6(Stage stage) {
        // 加入一个按钮
        Button button = new Button("测试按钮");
        button.setCursor(Cursor.CROSSHAIR); // 设置按钮的鼠标悬停样式
        button.setPrefSize(200,200); // 设置按钮大小
        // 设置按钮坐标，不设默认为(0,0)
        button.setLayoutX(50);
        button.setLayoutY(50);

        // Group类的使用
        Group group = new Group();
        group.getChildren().add(button); // 也可以使用addAll一次添加多个
        // 建立一个场景
        Scene scene = new Scene(group);
        scene.setCursor(Cursor.HAND); // scene可以设置鼠标悬停样式

        // 设置舞台集成场景
        stage.setScene(scene);
        stage.show(); // 让窗口出现必须调show

        // 获取网络服务打开百度网页
        HostServices hostServices = getHostServices();
        hostServices.showDocument("www.baidu.com");

    }


    public static void main(String[] args) {
        launch();
    }
}