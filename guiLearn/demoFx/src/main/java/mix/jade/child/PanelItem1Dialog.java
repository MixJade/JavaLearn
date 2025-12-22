package mix.jade.child;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mix.entiy.Person;

import java.util.regex.Pattern;

public class PanelItem1Dialog extends Dialog<Person> {
    public PanelItem1Dialog(Person person) {
        super();
        // 创建一个新的对话框
        this.setTitle("模态对话框");
        this.setHeaderText("请输入信息：");

        // 设置默认的按钮类型
        ButtonType okButtonType = new ButtonType("确定");
        this.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // 创建一个网格布局
        GridPane grid = new GridPane();
        grid.setHgap(10); // 设置水平间隙
        grid.setVgap(5); // 设置垂直间隙
        // 添加两个输入框
        // 1. 名字输入框
        TextField nameInput = new TextField(person.getName());
        nameInput.setPromptText("请输入名字");
        grid.add(new Text("名字："), 0, 0);
        grid.add(nameInput, 1, 0);
        Label nameErrLab = new Label("");
        nameErrLab.setTextFill(Color.FIREBRICK);
        grid.add(nameErrLab, 1, 1);
        nameInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                nameErrLab.setText("请输入名字");
            } else {
                nameErrLab.setText("");
            }
        });
        // 2. 年龄输入框
        TextField ageInput = new TextField(String.valueOf(person.getAge()));
        grid.add(new Text("年龄："), 0, 2);
        grid.add(ageInput, 1, 2);
        Label ageErrLab = new Label("");
        ageErrLab.setTextFill(Color.FIREBRICK);
        grid.add(ageErrLab, 1, 3);
        DialogPane dialogPane = this.getDialogPane();
        dialogPane.setContent(grid);
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        // 设置点击确定按钮的校验事件
        Button dontCloseButton = (Button) getDialogPane().lookupButton(okButtonType);
        dontCloseButton.addEventFilter(ActionEvent.ACTION, event -> {
            boolean isErr = false;
            if (nameInput.getText().isBlank()) {
                nameErrLab.setText("请输入名字");
                isErr = true;
            }
            if (pattern.matcher(ageInput.getText()).matches()) {
                ageErrLab.setText("");
            } else {
                ageErrLab.setText("请输入数字");
                isErr = true;
            }
            if (isErr) event.consume();
        });

        // 点击按钮关闭对话框
        this.setResultConverter(buttonType -> {
            try {
                if (buttonType == okButtonType) {
                    String name = nameInput.getText();
                    return new Person(name, Integer.parseInt(ageInput.getText()));
                }
                return null;
            } catch (NumberFormatException e) {
                return null;
            }
        });
    }
}
