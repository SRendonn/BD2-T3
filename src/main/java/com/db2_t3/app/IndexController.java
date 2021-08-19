package com.db2_t3.app;

import com.db2_t3.models.ConexionMongoDB;
import com.db2_t3.models.ConexionOracle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    @FXML
    PasswordField contraOracleInput;

    @FXML
    TextField puertoOracleInput;

    @FXML
    TextField usuarioOracleInput;

    @FXML
    TextField nombreBaseDeDatosMongoDBInput;

    @FXML
    TextField puertoMongoDBInput;

    @FXML
    private void irAlMenu() throws IOException {
        String nuevoUsuarioOracle = usuarioOracleInput.getText().trim();
        String nuevaContraOracle = contraOracleInput.getText().trim();
        String nuevoPuertoOracle = puertoOracleInput.getText().trim();
        String nuevoNombreMongoDB = nombreBaseDeDatosMongoDBInput.getText().trim();
        String nuevoPuertoMongoDB = puertoMongoDBInput.getText().trim();

        if (nuevoUsuarioOracle.equals("") || nuevaContraOracle.equals("") || nuevoPuertoOracle.equals("")
        || nuevoNombreMongoDB.equals("") || nuevoPuertoMongoDB.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor ingresa los campos solicitados antes de continuar");
            alert.show();
        } else {
            ConexionOracle.usuario = nuevoUsuarioOracle;
            ConexionOracle.contra = nuevaContraOracle;
            ConexionOracle.puerto = nuevoPuertoOracle;
            ConexionMongoDB.puerto = nuevoPuertoMongoDB;
            ConexionMongoDB.dbName = nuevoNombreMongoDB;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Datos agregados correctamente");
            alert.show();
            try{
                if(!ConexionOracle.conn.isClosed()){
                    ConexionOracle.desconectarOracle();
                }
            } catch (Exception e){

            }
            ConexionOracle.conectarOracle();

            App.setRoot("menu");
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usuarioOracleInput.setText(ConexionOracle.usuario);
        contraOracleInput.setText(ConexionOracle.contra);
        puertoOracleInput.setText(ConexionOracle.puerto);
        nombreBaseDeDatosMongoDBInput.setText(ConexionMongoDB.dbName);
        puertoMongoDBInput.setText(ConexionMongoDB.puerto);
    }
}
