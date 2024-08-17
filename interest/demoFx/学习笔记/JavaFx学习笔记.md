# JavaFx学习笔记

> 2024年8月17日21:24:10

## 一、生命周期

* 目前只有init、start、stop三种

```java
package mix.jade;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void init() throws Exception {
        System.out.println("生命周期初始化阶段(没什么用)");
        super.init();
    }

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

    @Override
    public void stop() throws Exception {
        System.out.println("生命周期结束阶段(没什么用)");
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}
```

## 二、窗口认识

### 2.1 设置宽高

* 这块删去其它无关代码
* 包括设置不可改变窗口大小
* 设置最大的宽高
* 设置最小的宽高
* 获取宽高

```java
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        // 设置不可改变窗口大小
        stage.setResizable(false);
        // 可以设置最大的宽高(最大化也不能超过这个高度)
        stage.setMaxHeight(600);
        stage.setMaxWidth(800);
        // 也可以设置最小的宽高
        stage.setMinHeight(200);
        stage.setMinWidth(200);

        stage.show(); // 让窗口出现必须调show

        // 可以获取宽高，但最好是在show方法之后
        System.out.println("窗口宽度:" + stage.getWidth());
        System.out.println("窗口高度:" + stage.getHeight());
    }
}
```

### 2.2 监听高度变化

* 同理可以监听宽度，主要用于做响应式适配
* `addListener`中的lamba表达式其实是`ChangeListener<Number>`这个接口，可以自行实现

```java
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        // 当然可以监听高度变化
        stage.heightProperty().addListener((observableValue, number, t1) -> {
            System.out.println("旧高度:" + number.doubleValue());
            System.out.println("新高度:" + t1.doubleValue());
        });

        stage.show(); // 让窗口出现必须调show
    }
}
```

### 2.3 其它函数

* 设置透明度
* 设置总是在上面
* 设置窗口初始坐标
* 监听窗口的xy轴变化

```java
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
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
    }
}
```

### 2.4 窗口样式与联动

* 五种没用的窗口样式
* 两种窗口联动模式(比如不关闭子窗口别想操控父窗口)

```java
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
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
}
```