module mix.jade {
    requires javafx.controls;
    requires javafx.fxml;


    opens mix.jade to javafx.fxml;
    exports mix.jade;
    exports mix.jade.child;
    opens mix.jade.child to javafx.fxml;
}