package com.db2_t3.app;

import com.db2_t3.models.*;
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
        System.out.println( "Seleccionando..." );
        ConexionOracle.consultarOracle("CREATE TABLE historicoventas(cc NUMBER(15) PRIMARY KEY, totalacumuladoventas NUMBER(30) NOT NULL)");
        ConexionOracle.consultarOracle("INSERT INTO historicoventas (cc, totalacumuladoventas) SELECT cedula, total FROM(SELECT e.cc as cedula, SUM(v.nro_unidades*v.miprod.precio_unitario) AS total FROM empleado e, TABLE(e.ventas) v GROUP BY e.cc ORDER BY total DESC)");
        ConexionOracle.consultarOracle("UPDATE empleado SET ventas=NULL");
        System.out.println("Consulta finalizada.");
        textoInformativo.setText("Arreglos de ventas vaciados en la DB de Oracle");
    }

    public void irAIndex() throws IOException {
        App.setRoot("index");
    }

}

