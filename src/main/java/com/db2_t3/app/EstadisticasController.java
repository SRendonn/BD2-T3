package com.db2_t3.app;

import com.db2_t3.models.EstadisticaMongoDB;
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
    private TableView<EstadisticaMongoDB> tablaDeptos;
    @FXML
    private TableView<EstadisticaMongoDB> tablaGlobal;


    @FXML
    private TableColumn<EstadisticaMongoDB, String> deptoNombreDepto;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> deptoVentasDepto;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> ciudadNombreDepto;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> ciudadVentasDepto;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> mejorVendedorCedulaDepto;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> mejorVendedorVentasDepto;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> peorVendedorCedulaDepto;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> peorVendedorVentasDepto;

    @FXML
    private TableColumn<EstadisticaMongoDB, String> deptoNombreGlobal;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> deptoVentasGlobal;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> ciudadNombreGlobal;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> ciudadVentasGlobal;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> mejorVendedorCedulaGlobal;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> mejorVendedorVentasGlobal;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> peorVendedorCedulaGlobal;
    @FXML
    private TableColumn<EstadisticaMongoDB, String> peorVendedorVentasGlobal;

    public void volver() throws IOException {
        App.setRoot("menu");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<EstadisticaMongoDB> deptos = EstadisticaMongoDB.getEstadisticasDepartamento();
        ObservableList<EstadisticaMongoDB> dataDeptos = FXCollections.observableArrayList(deptos);

        ArrayList<EstadisticaMongoDB> global = EstadisticaMongoDB.getEstadisticasGlobales();
        ObservableList<EstadisticaMongoDB> dataGlobal = FXCollections.observableArrayList(global);

        tablaDeptos.setItems(dataDeptos);
        tablaGlobal.setItems(dataGlobal);

        deptoNombreDepto.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getDepartamento().getNombre()));
        deptoVentasDepto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getDepartamento().getTotalVentas())));
        ciudadNombreDepto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMejorCiudad().getNombre()));
        ciudadVentasDepto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorCiudad().getTotalVentas())));
        mejorVendedorCedulaDepto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorVendedor().getCedula())));
        mejorVendedorVentasDepto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorVendedor().getVentas())));
        peorVendedorCedulaDepto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPeorVendedor().getCedula())));
        peorVendedorVentasDepto.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPeorVendedor().getVentas())));

        deptoNombreGlobal.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getDepartamento().getNombre()));
        deptoVentasGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getDepartamento().getTotalVentas())));
        ciudadNombreGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMejorCiudad().getNombre()));
        ciudadVentasGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorCiudad().getTotalVentas())));
        mejorVendedorCedulaGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorVendedor().getCedula())));
        mejorVendedorVentasGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorVendedor().getVentas())));
        peorVendedorCedulaGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPeorVendedor().getCedula())));
        peorVendedorVentasGlobal.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPeorVendedor().getVentas())));
    }
}
