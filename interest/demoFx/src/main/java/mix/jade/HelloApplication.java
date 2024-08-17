package mix.jade;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("生命周期启动阶段");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 240);
        // 设置图标
        stage.getIcons().add(new Image("v.png"));
        stage.setTitle("Hello!");
        stage.setScene(scene);

        stage.show(); // 让窗口出现必须调show
    }

    /**
     * 第一课：窗口宽高相关
     *
     * @since 2024-8-17 22:32:44
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
     * @since 2024-8-17 22:34:07
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
     * 第三课：设置窗口样式(没啥用)
     *
     * @since 2024-8-17 22:50:12
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
        stage2.show();

        // 设置窗口样式:窗口全白
        Stage stage3 = new Stage();
        stage3.initStyle(StageStyle.UNIFIED); // 窗口全白,scene必须要透明不然看不到
        stage3.setTitle("stage3");
        stage3.show();

        // 设置窗口样式:完全透明
        Stage stage4 = new Stage();
        stage4.initStyle(StageStyle.UNDECORATED); // 也是完全透明(不影响scene)
        stage4.setTitle("stage4");
        stage4.show();

        // 设置窗口样式:特殊版本的window
        Stage stage5 = new Stage();
        stage5.initStyle(StageStyle.UTILITY); // 用于特殊版本的window,在win10上样式不协调
        stage5.setTitle("stage5");
        stage5.show();
    }


    public static void main(String[] args) {
        launch();
    }
}