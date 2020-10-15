module dlsu.cpei {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.commonmark;
    requires org.commonmark.ext.front.matter;
    requires org.commonmark.ext.gfm.strikethrough;
    requires org.commonmark.ext.gfm.tables;
    requires org.commonmark.ext.image.attributes;
    requires org.commonmark.ext.task.list.items;

    opens dlsu.cpei to javafx.fxml;
    exports dlsu.cpei;
}