module com.db2_t3.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.db2_t3.app to javafx.fxml;
    exports com.db2_t3.app;
}
