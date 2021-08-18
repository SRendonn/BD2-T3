package com.db2_t3.app;

import com.db2_t3.models.DepartamentoOracle;
import com.db2_t3.models.EstadisticaDeptoMongoDB;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class MenuController {

    @FXML
    Text textoInformativo;

    public void generarEstadisticas(){
        ArrayList<DepartamentoOracle> departamentos = DepartamentoOracle.obtenerDepartamentos();
        EstadisticaDeptoMongoDB.addEstadisticas(departamentos, true);
        textoInformativo.setText("Estadísticas generadas y cargadas en MongoDB");
    }

    public void visualizarEstadisticas() throws IOException {
        App.setRoot("estadisticas");
    }

    public void vaciarArreglos(){
        //Aquí va lo de Salo jeje
        textoInformativo.setText("Arreglos de ventas vaciados en la DB de Oracle");
    }

    public void irAIndex() throws IOException {
        App.setRoot("index");
    }

}
