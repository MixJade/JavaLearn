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

## 二、设置宽高

* 这块删去其它无关代码

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

## 三、监听高度变化

* 同理可以监听宽度，主要用于做响应式适配
*  `addListener`中的lamba表达式其实是`ChangeListener<Number>`这个接口，可以自行实现

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