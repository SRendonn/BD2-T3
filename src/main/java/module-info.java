module com.db2_t3.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;

    opens com.db2_t3.app to javafx.fxml;
    exports com.db2_t3.app;
}
