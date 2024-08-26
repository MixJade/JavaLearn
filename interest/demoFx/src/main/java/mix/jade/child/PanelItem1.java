package mix.jade.child;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mix.jade.entiy.Person;

import java.net.URL;
import java.util.ResourceBundle;

public class PanelItem1 implements Initializable {
    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, String> name, age;
    @FXML
    public Label checkText;

    private Person nowPerson;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("面板1初始化");
        // 初始化表格列
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));

        // 创建并添加数据到表格中
        ObservableList<Person> data = FXCollections.observableArrayList(
                new Person("张三", 23),
                new Person("李四", 44),
                new Person("王五", 25)
        );
        tableView.setItems(data);

        // 设置每行点击事件
        tableView.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Person person = row.getItem();
                    nowPerson = person;
                    if (event.getClickCount() == 1) {
                        System.out.println(person);
                        checkText.setText("当前选中值:" + person.getName());
                    } else if (event.getClickCount() == 2) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("消息弹框标题");
                        alert.setHeaderText("表格事件");
                        alert.setContentText("双击了【" + person.getName() + "】");
                        alert.showAndWait();
                    }
                }
            });
            return row;
        });
    }

    public void updatePerson() {
        if (nowPerson == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("消息弹框标题");
            alert.setHeaderText(null);
            alert.setContentText("没有选中行");
            alert.showAndWait();
        } else {
            // 创建一个新的对话框
            Dialog<String[]> dialog = new PanelItem1Dialog(nowPerson);
            // 显示对话框，并获取结果
            String[] result = dialog.showAndWait().orElse(null);
            // 输出结果
            if (result != null) {
                System.out.println("输入1: " + result[0]);
                System.out.println("输入2: " + result[1]);
            }
        }
    }
}
