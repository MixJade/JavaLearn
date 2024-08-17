module mix.jade {
    requires javafx.controls;
    requires javafx.fxml;


    opens mix.jade to javafx.fxml;
    exports mix.jade;
}