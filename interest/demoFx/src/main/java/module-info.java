module mix.jade.demofx {
    requires javafx.controls;
    requires javafx.fxml;


    opens mix.jade.demofx to javafx.fxml;
    exports mix.jade.demofx;
}