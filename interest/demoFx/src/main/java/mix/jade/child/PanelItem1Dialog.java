package mix.jade.child;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import mix.jade.entiy.Person;

public class PanelItem1Dialog extends Dialog<String[]> {
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
        grid.setVgap(10); // 设置垂直间隙
        // 添加两个输入框
        TextField input1 = new TextField(person.getName());
        TextField input2 = new TextField(String.valueOf(person.getAge()));
        grid.add(new Text("输入1："), 0, 0);
        grid.add(input1, 1, 0);
        grid.add(new Text("输入2："), 0, 1);
        grid.add(input2, 1, 1);

        DialogPane dialogPane = this.getDialogPane();
        dialogPane.setContent(grid);

        this.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new String[]{input1.getText(), input2.getText()};
            }
            return null;
        });
    }
}
