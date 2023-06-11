module student {
    requires javafx.controls;
    requires java.sql;
    requires org.jetbrains.annotations;
    opens ui to javafx.fxml;
    exports db;
    exports model;
    exports ui;
	exports util;
}