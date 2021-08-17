package com.db2_t3.app;

import com.db2_t3.models.CiudadOracle;
import com.db2_t3.models.DepartamentoOracle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("index"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        /*ArrayList<DepartamentoOracle> deps= DepartamentoOracle.obtenerDepartamentos();
        for(int i = 0; i<deps.size(); i++){
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println(deps.get(i).getNombre());
            ArrayList<CiudadOracle> cities = deps.get(i).getVentasPorCiudad();
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
        }*/
        launch();

        /*deps= DepartamentoOracle.obtenerDepartamentos();
        for(int i = 0; i<deps.size(); i++){
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println(deps.get(i).getNombre());
            ArrayList<CiudadOracle> cities = deps.get(i).getVentasPorCiudad();
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
        }*/
    }

}