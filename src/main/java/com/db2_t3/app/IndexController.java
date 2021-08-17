package com.db2_t3.app;

import com.db2_t3.app.App;
import com.db2_t3.models.ConexionOracle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    @FXML
    PasswordField contraInput;

    @FXML
    TextField puertoInput;

    @FXML
    TextField usuarioInput;

    @FXML
    private void irAlMenu() throws IOException {
        String nuevoUsuario = usuarioInput.getText().trim();
        String nuevaContra = contraInput.getText().trim();
        String nuevoPuerto = puertoInput.getText().trim();

        if (nuevoUsuario.equals("") || nuevaContra.equals("") || nuevoPuerto.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor ingresa los campos solicitados antes de continuar");
            alert.show();
        } else {
            ConexionOracle.usuario = nuevoUsuario;
            ConexionOracle.contra = nuevaContra;
            ConexionOracle.puerto = nuevoPuerto;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Datos agregados correctamente");
            alert.show();
            App.setRoot("menu");
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usuarioInput.setText(ConexionOracle.usuario);
        contraInput.setText(ConexionOracle.contra);
        puertoInput.setText(ConexionOracle.puerto);
    }
}
