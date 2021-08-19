package com.db2_t3.app;

import com.db2_t3.models.*;
import com.db2_t3.models.DepartamentoOracle;
import com.db2_t3.models.EstadisticaMongoDB;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuController {

    @FXML
    Text textoInformativo;

    public void generarEstadisticas(){
        try{
            ArrayList<DepartamentoOracle> departamentos = DepartamentoOracle.obtenerDepartamentos();
            EstadisticaMongoDB.addEstadisticas(departamentos, true);
            textoInformativo.setText("Estadísticas generadas y cargadas en MongoDB");
        }catch (NullPointerException npe){
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "La conexión a la base de datos de Oracle ha sido estropeada por la conexión de múltiples cursores al mismo tiempo\nPara continuar, cierre estos cursores y reinicie la conexión con la base de datos");
            alert.show();
            textoInformativo.setText("Cierra otras conexiones y reinicia la conexión con la base de datos de Oracle para continuar");
        }

    }

    public void visualizarEstadisticas() throws IOException {
        App.setRoot("estadisticas");
    }

    public void vaciarArreglos(){
        System.out.println( "Seleccionando..." );
        ConexionOracle.consultarOracle("CREATE TABLE aux(cc NUMBER(15) PRIMARY KEY, totalacumuladoventas NUMBER(30) NOT NULL, nuevototal NUMBER(30) NOT NULL)");
        ConexionOracle.consultarOracle("INSERT INTO aux (cc, nuevototal, totalacumuladoventas) SELECT cc, 0, 0 FROM empleado");
        ConexionOracle.consultarOracle("UPDATE aux SET nuevototal = (SELECT historicoventas.totalacumuladoventas FROM historicoventas WHERE historicoventas.cc=aux.cc) WHERE EXISTS (SELECT historicoventas.totalacumuladoventas FROM historicoventas WHERE historicoventas.cc = aux.cc)");
        ConexionOracle.consultarOracle("DELETE FROM historicoventas");
        ConexionOracle.consultarOracle("INSERT INTO historicoventas (cc, totalacumuladoventas) SELECT cedula, total FROM(SELECT e.cc as cedula, SUM(v.nro_unidades*v.miprod.precio_unitario) AS total FROM empleado e, TABLE(e.ventas) v GROUP BY e.cc ORDER BY cc DESC)");
        ConexionOracle.consultarOracle("UPDATE aux SET totalacumuladoventas = (SELECT historicoventas.totalacumuladoventas FROM historicoventas WHERE historicoventas.cc=aux.cc) WHERE EXISTS (SELECT historicoventas.totalacumuladoventas FROM historicoventas WHERE historicoventas.cc = aux.cc)");
        ConexionOracle.consultarOracle("DELETE FROM historicoventas");
        ConexionOracle.consultarOracle("INSERT INTO historicoventas (cc, totalacumuladoventas) SELECT cedula, total FROM(SELECT e.cc as cedula, SUM(a.nuevototal+a.totalacumuladoventas) AS total FROM empleado e, aux a WHERE e.cc = a.cc GROUP BY e.cc ORDER BY e.cc DESC)");
        ConexionOracle.consultarOracle("DROP TABLE aux");
        ConexionOracle.consultarOracle("UPDATE empleado SET ventas=NULL");
        System.out.println("Consulta finalizada.");
        textoInformativo.setText("Arreglos de ventas vaciados en la DB de Oracle");
    }

    public void irAIndex() throws IOException {
        App.setRoot("index");
    }

}

