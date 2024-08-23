package mix.jade.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class PanelController {
    @FXML
    private ListView<String> menuListView;
    @FXML
    private StackPane contentPane;

    public void initialize() {
        // 为ListView添加菜单项
        menuListView.getItems().addAll("Menu 1", "Menu 2", "Menu 3");
        // 为ListView的每个菜单项设置事件
        menuListView.setOnMouseClicked(event -> {
            // 获取被点击的菜单项，并加载对应的页面
            String selectedItem = menuListView.getSelectionModel().getSelectedItem();
            Node node = loadFxml(selectedItem); //假设loadFxml函数会根据参数加载对应的FXML文件，并返回其根节点

            if (node != null) {
                // 将当前页面添加到右侧的容器中，并清除原有的页面
                contentPane.getChildren().setAll(node);
            }
        });
    }

    private Node loadFxml(String menuItem) {
        System.out.println("菜单项：" + menuItem);
        // 根据菜单项名称加载对应的fxml文件，并返回其根节点
        // 这里是一个模拟的函数，实际操作应该会更复杂
        return null;
    }
}
