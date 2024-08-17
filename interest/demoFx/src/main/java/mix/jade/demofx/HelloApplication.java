package mix.jade.demofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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