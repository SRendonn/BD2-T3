package com.db2_t3.app;

import com.db2_t3.models.EstadisticaDeptoMongoDB;
import com.db2_t3.models.EstadisticaGlobalMongoDB;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EstadisticasController implements Initializable {

    @FXML
    private TableView<EstadisticaDeptoMongoDB> tablaDeptos;
    @FXML
    private TableView<EstadisticaGlobalMongoDB> tablaGlobal;

    @FXML
    private TableColumn<EstadisticaDeptoMongoDB, String> ciudadNombreDepto;
    @FXML
    private TableColumn<EstadisticaDeptoMongoDB, String> ciudadVentasDepto;
    @FXML
    private TableColumn<EstadisticaDeptoMongoDB, String> mejorVendedorCedulaDepto;
    @FXML
    private TableColumn<EstadisticaDeptoMongoDB, String> mejorVendedorVentasDepto;
    @FXML
    private TableColumn<EstadisticaDeptoMongoDB, String> peorVendedorCedulaDepto;
    @FXML
    private TableColumn<EstadisticaDeptoMongoDB, String> peorVendedorVentasDepto;

    @FXML
    private TableColumn<EstadisticaGlobalMongoDB, String> deptoNombreGlobal;
    @FXML
    private TableColumn<EstadisticaGlobalMongoDB, String> deptoVentasGlobal;
    @FXML
    private TableColumn<EstadisticaGlobalMongoDB, String> ciudadNombreGlobal;
    @FXML
    private TableColumn<EstadisticaGlobalMongoDB, String> ciudadVentasGlobal;
    @FXML
    private TableColumn<EstadisticaGlobalMongoDB, String> mejorVendedorCedulaGlobal;
    @FXML
    private TableColumn<EstadisticaGlobalMongoDB, String> mejorVendedorVentasGlobal;
    @FXML
    private TableColumn<EstadisticaGlobalMongoDB, String> peorVendedorCedulaGlobal;
    @FXML
    private TableColumn<EstadisticaGlobalMongoDB, String> peorVendedorVentasGlobal;

    public void volver() throws IOException {
        App.setRoot("menu");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<EstadisticaDeptoMongoDB> deptos = EstadisticaDeptoMongoDB.getEstadisticasDepartamento();
        ObservableList<EstadisticaDeptoMongoDB> dataDeptos = FXCollections.observableArrayList(deptos);

        ArrayList<EstadisticaGlobalMongoDB> global = EstadisticaGlobalMongoDB.getEstadisticasGlobales();
        ObservableList<EstadisticaGlobalMongoDB> dataGlobal = FXCollections.observableArrayList(global);

        tablaDeptos.setItems(dataDeptos);
        tablaGlobal.setItems(dataGlobal);

        ciudadNombreDepto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMejorCiudad().getNombre()));
        ciudadVentasDepto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorCiudad().getTotalVentas())));
        mejorVendedorCedulaDepto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorVendedor().getCedula())));
        mejorVendedorVentasDepto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorVendedor().getVentas())));
        peorVendedorCedulaDepto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPeorVendedor().getCedula())));
        peorVendedorVentasDepto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPeorVendedor().getVentas())));

        deptoNombreGlobal.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getMejorDepartamento().getNombre()));
        deptoVentasGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorDepartamento().getTotalVentas())));
        ciudadNombreGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMejorCiudad().getNombre()));
        ciudadVentasGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorCiudad().getTotalVentas())));
        mejorVendedorCedulaGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorVendedor().getCedula())));
        mejorVendedorVentasGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorVendedor().getVentas())));
        peorVendedorCedulaGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPeorVendedor().getCedula())));
        peorVendedorVentasGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPeorVendedor().getVentas())));
    }
}
