package com.db2_t3.app;

import com.db2_t3.models.CiudadOracle;
import com.db2_t3.models.DepartamentoOracle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class MenuController {

    @FXML
    Text textoInformativo;

    public void generarEstadisticas(){
        ArrayList<DepartamentoOracle> departamentos = DepartamentoOracle.obtenerDepartamentos();

        // Esto es solo para visualizar los resultados por consola
        //Tener cuidado con los Departamentos sin Ciudad, Ciudades sin Empleados y Empleados sin Ventas

        for(int i = 0; i<departamentos.size(); i++){
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println(departamentos.get(i).getNombre());
            ArrayList<CiudadOracle> cities = departamentos.get(i).getVentasPorCiudad();
            for(int j = 0; j<cities.size(); j++){
                System.out.println(cities.get(j).getNombre());
                System.out.println(cities.get(j).getTotalVentas());
                try {
                    System.out.println(cities.get(j).getMejorVendedor().getCedula());
                    System.out.println(cities.get(j).getMejorVendedor().getVentas());

                }
                catch (Exception e){
                    System.out.println("No tiene mejor vendedor");
                }

            }
        }

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
