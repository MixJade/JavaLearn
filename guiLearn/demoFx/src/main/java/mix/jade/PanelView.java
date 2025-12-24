package mix.jade;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class PanelView {
    @FXML
    private StackPane contentPane;

    public void initialize() {
        loadFxml(1);
    }


    private void loadFxml(int menuItem) {
        System.out.println("菜单项：" + menuItem);
        try {
            // 根据菜单项的名称加载FXML文件,返回此FXML文件的根节点
            Parent node;
            if (menuItem == 1) {
                node = new FXMLLoader(getClass().getResource("child/PanelItem1.fxml")).load();
            } else {
                node = new FXMLLoader(getClass().getResource("child/PanelItem2.fxml")).load();
            }
            // 将当前页面添加到右侧的容器中，并清除原有的页面
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFxml_1() {
        loadFxml(1);
    }

    public void loadFxml_2() {
        loadFxml(2);
    }
}
