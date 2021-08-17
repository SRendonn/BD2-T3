package com.db2_t3.app;

import com.db2_t3.models.CiudadOracle;
import com.db2_t3.models.ConexionMongoDB;
import com.db2_t3.models.DepartamentoOracle;
import com.db2_t3.models.EstadisticaMongoDB;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;

public class MenuController {

    @FXML
    Text textoInformativo;

    public void generarEstadisticas(){
        ArrayList<DepartamentoOracle> departamentos = DepartamentoOracle.obtenerDepartamentos();
        EstadisticaMongoDB.addEstadisticas(departamentos);
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
