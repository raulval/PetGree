/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.edu.utfpr.carloseduardofreitas.petgree.dao.UsuarioDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author kadu1
 */
public class LoginController implements Initializable {
    public static Usuario usuario = new Usuario();
    UsuarioDao usuarioDao = new UsuarioDao();
    @FXML
    private Button btnLogin;
    @FXML
    private TextField textFieldLogin;
    @FXML
    private PasswordField passwordFieldSenha;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
        private void handleLogin(ActionEvent event) throws IOException {

            usuario = usuarioDao.consultarLogin(textFieldLogin.getText(), passwordFieldSenha.getText());

            if (usuario != null) {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("PetGree - Dashboard");
                stage.show();
                Stage thisStage = (Stage) btnLogin.getScene().getWindow();
                thisStage.close();

            }else{
                Notifications.create().title("Login").text("Email ou senha inválidos!").showError();
            }

        }

    }
    

