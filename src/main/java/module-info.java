module dlsu.cpei {
    requires javafx.controls;
    requires javafx.fxml;

    opens dlsu.cpei to javafx.fxml;
    exports dlsu.cpei;
}