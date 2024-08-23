package mix.jade;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.io.File;
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
        System.out.println("这是一开始就会执行的方法,一般用于初始化一些组件");
    }

    @FXML
    public void changeScene() {
        HelloApplication.switchScene(1);
    }

    @FXML
    public void changeScene3() {
        HelloApplication.switchScene(3);
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

    /**
     * 选择文件
     */
    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File"); //设置文件选择器窗口的标题
        //设置文件选择器的初始目录（如果未设定，则默认为用户的主目录）
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        //添加文件类型筛选器(可省略)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        //显示打开文件对话框并获取选定的文件（将文件选择器对象传递给showOpenDialog方法以显示文件选择器对话桓）
        File file = fileChooser.showOpenDialog(HelloApplication.getStage());
        if (file != null) {
            output.setText("所选文件名称：" + file.getName() + "\n路径" + file.getPath());
        }
    }
}
