module com.db2_t3.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;

    opens com.db2_t3.app to javafx.fxml;
    opens com.db2_t3.models to javafx.base;

    exports com.db2_t3.app;
}
