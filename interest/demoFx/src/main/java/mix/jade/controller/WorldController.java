package mix.jade.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import mix.jade.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class WorldController implements Initializable {
    @FXML
    private TextArea output;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("这是一开始就会执行的方法");
    }

    @FXML
    public void changeScene() {
        HelloApplication.switchScene(1);
    }

    /**
     * 文件拖拽到输入框上的事件(还没有放入)
     *
     * @param dragEvent 拖拽事件
     */
    public void fileDragOver(DragEvent dragEvent) {
        System.out.println("文件拖拽到输入框上的事件(还没有放入)");
        if (dragEvent.getGestureSource() != output &&
                dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }

    /**
     * 文件拖拽完成事件
     *
     * @param dragEvent 拖拽事件
     */
    public void fileDragDropped(DragEvent dragEvent) {
        System.out.println("文件拖拽完成事件");
        Dragboard db = dragEvent.getDragboard();
        if (db.hasFiles()) {
            output.setText("");
            db.getFiles().forEach(file -> {
                try (Stream<String> stringStream = Files.lines(file.toPath())) {
                    stringStream.forEach(line -> output.appendText(line + "\n"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        dragEvent.setDropCompleted(true);
        dragEvent.consume();
    }
}
