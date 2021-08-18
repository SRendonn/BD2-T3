package com.db2_t3.app;

import com.db2_t3.models.EstadisticaMongoDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EstadisticasController implements Initializable {

    @FXML
    private TableView<EstadisticaMongoDB> tableView;

    public void volver() throws IOException {
        App.setRoot("menu");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EstadisticaMongoDB.getEstadisticas();
    }
}
