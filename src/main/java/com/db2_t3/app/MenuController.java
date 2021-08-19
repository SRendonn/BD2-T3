package com.db2_t3.app;

import com.db2_t3.models.*;
import com.db2_t3.models.DepartamentoOracle;
import com.db2_t3.models.EstadisticaMongoDB;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuController {

    @FXML
    Text textoInformativo;

    public void generarEstadisticas(){
        ArrayList<DepartamentoOracle> departamentos = DepartamentoOracle.obtenerDepartamentos();
        EstadisticaMongoDB.addEstadisticas(departamentos, true);
        textoInformativo.setText("Estadísticas generadas y cargadas en MongoDB");
    }

    public void visualizarEstadisticas() throws IOException {
        App.setRoot("estadisticas");
    }

    public void vaciarArreglos(){
        System.out.println( "Seleccionando..." );
        ConexionOracle.consultarOracle("CREATE TABLE aux(cc NUMBER(15) PRIMARY KEY, totalacumuladoventas NUMBER(30) NOT NULL, nuevototal NUMBER(30) NOT NULL)");
        ConexionOracle.consultarOracle("INSERT INTO aux (cc, nuevototal, totalacumuladoventas) SELECT cedula, total, 0 FROM(SELECT e.cc as cedula, SUM(v.nro_unidades*v.miprod.precio_unitario) AS total FROM empleado e, TABLE(e.ventas) v GROUP BY e.cc ORDER BY total DESC)");
        ConexionOracle.consultarOracle("UPDATE aux SET totalacumuladoventas = (SELECT historicoventas.totalacumuladoventas FROM historicoventas WHERE historicoventas.cc=aux.cc) WHERE EXISTS (SELECT historicoventas.totalacumuladoventas FROM historicoventas WHERE historicoventas.cc = aux.cc)");
        ConexionOracle.consultarOracle("DELETE FROM historicoventas");
        ConexionOracle.consultarOracle("INSERT INTO historicoventas (cc, totalacumuladoventas) SELECT cedula, total FROM(SELECT e.cc as cedula, SUM(v.nro_unidades*v.miprod.precio_unitario) AS total FROM empleado e, TABLE(e.ventas) v GROUP BY e.cc ORDER BY total DESC)");
        ConexionOracle.consultarOracle("UPDATE historicoventas SET totalacumuladoventas = (SELECT SUM(nuevototal+totalacumuladoventas) FROM aux WHERE historicoventas.cc = aux.cc)");
        ConexionOracle.consultarOracle("DROP TABLE aux");
        ConexionOracle.consultarOracle("UPDATE empleado SET ventas=NULL");
        System.out.println("Consulta finalizada.");
        textoInformativo.setText("Arreglos de ventas vaciados en la DB de Oracle");
    }

    public void irAIndex() throws IOException {
        App.setRoot("index");
    }

}

