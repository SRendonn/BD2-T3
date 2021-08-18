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

        setTableCellValues(deptoNombreDepto, deptoVentasDepto, ciudadNombreDepto, ciudadVentasDepto, mejorVendedorCedulaDepto, mejorVendedorVentasDepto, peorVendedorCedulaDepto, peorVendedorVentasDepto);
        setTableCellValues(deptoNombreGlobal, deptoVentasGlobal, ciudadNombreGlobal, ciudadVentasGlobal, mejorVendedorCedulaGlobal, mejorVendedorVentasGlobal, peorVendedorCedulaGlobal, peorVendedorVentasGlobal);
    }

    private static void setTableCellValues(TableColumn<EstadisticaMongoDB, String> deptoNombre, TableColumn<EstadisticaMongoDB, String> deptoVentas, TableColumn<EstadisticaMongoDB, String> ciudadNombre, TableColumn<EstadisticaMongoDB, String> ciudadVentas, TableColumn<EstadisticaMongoDB, String> mejorVendedorCedula, TableColumn<EstadisticaMongoDB, String> mejorVendedorVentas, TableColumn<EstadisticaMongoDB, String> peorVendedorCedula, TableColumn<EstadisticaMongoDB, String> peorVendedorVentas) {
        deptoNombre.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getDepartamento().getNombre()));
        deptoVentas.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getDepartamento().getTotalVentas())));
        ciudadNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad().getNombre()));
        ciudadVentas.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCiudad().getTotalVentas())));
        mejorVendedorCedula.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorVendedor().getCedula())));
        mejorVendedorVentas.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMejorVendedor().getVentas())));
        peorVendedorCedula.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPeorVendedor().getCedula())));
        peorVendedorVentas.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPeorVendedor().getVentas())));
    }
}
