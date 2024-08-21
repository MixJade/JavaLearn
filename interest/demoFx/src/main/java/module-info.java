module mix.jade {
    requires javafx.controls;
    requires javafx.fxml;


    opens mix.jade to javafx.fxml;
    exports mix.jade;
    exports mix.jade.controller;
    opens mix.jade.controller to javafx.fxml;
}