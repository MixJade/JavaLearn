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
    // 保存数据源引用
    private ObservableList<Person> personList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("面板1初始化");
        // 初始化表格列
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));

        // 创建并添加数据到表格中
        personList = FXCollections.observableArrayList(
                new Person("张三", 23),
                new Person("李四", 44),
                new Person("王五", 25)
        );
        tableView.setItems(personList);

        // 设置每行点击事件（保持不变）
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
            Alert alert = new Alert(Alert.AlertType.WARNING, "没有选中行");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            // 创建一个新的对话框
            Dialog<Person> dialog = new PanelItem1Dialog(nowPerson);
            // 显示对话框，并获取结果
            Person result = dialog.showAndWait().orElse(null);
            // 输出结果并更新数据源
            if (result != null) {
                System.out.println("输入数值: " + result);
                // 找到原对象在列表中的位置
                int index = personList.indexOf(nowPerson);
                if (index != -1) {
                    // 替换原对象（会自动通知表格刷新）
                    personList.set(index, result);
                    // 更新当前选中对象的引用
                    nowPerson = result;
                    // 更新显示的选中文本
                    checkText.setText("当前选中值:" + result.getName());
                }
            }
        }
    }

    public void addPerson() {
        // 创建一个新的对话框
        Dialog<Person> dialog = new PanelItem1Dialog(new Person());
        // 显示对话框，并获取结果
        Person result = dialog.showAndWait().orElse(null);
        // 输出结果并添加到数据源
        if (result != null) {
            System.out.println("输入数值: " + result);
            // 添加新对象到列表（会自动通知表格刷新）
            personList.add(result);
        }
    }
}