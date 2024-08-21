package mix.jade.controller;

import javafx.fxml.FXML;
import mix.jade.HelloApplication;

public class WorldController {
    @FXML
    public void changeScene() {
        HelloApplication.switchScene(1);
    }
}
