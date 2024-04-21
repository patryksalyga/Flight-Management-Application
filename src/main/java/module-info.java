module lot.lotapp {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens lot.lotapp to javafx.fxml;
    exports lot.lotapp;
}