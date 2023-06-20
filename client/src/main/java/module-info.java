module com.sugarcoat.client {
    requires com.sugarcoat.gui;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;

    opens com.sugarcoat.client to javafx.fxml;
    exports com.sugarcoat.client;
}