package mix.jade;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HelloController {
    @FXML
    public TextField myTextField;
    @FXML
    private Label welcomeText;

    /**
     * 按钮点击事件
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * 右键菜单事件
     */
    @FXML
    public void handleRightAction1() {
        myTextField.setText("右键事件1");
    }

    /**
     * 右键菜单事件2
     */
    @FXML
    public void handleRightAction2() {
        myTextField.setText("右键事件2");
    }

    /**
     * 键盘事件(前提是焦点在控件上)
     */
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("消息弹框标题");
            alert.setHeaderText("按键提示");
            alert.setContentText("你刚才按下了回车键是吧");
            alert.showAndWait();
        }
    }

    @FXML
    public void changeScene() {
        StartApp.switchScene(2);
    }
}