module dlsu.cpei {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.commonmark;

    opens dlsu.cpei to javafx.fxml;
    exports dlsu.cpei;
}