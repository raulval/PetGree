/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.nashorn.internal.scripts.JO;
import org.controlsfx.control.Notifications;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author kadu1
 */
public class DashboardController implements Initializable {
    private String selecionado = "";
    @FXML
    private BorderPane mainPane;
    @FXML
    private HBox btnDashboard;
    @FXML
    private HBox btnClientes;
    @FXML
    private HBox btnAnimais;
    @FXML
    private HBox btnAgendamentos;
    @FXML
    private HBox btnServicos;
    @FXML
    private HBox btnUsuarios;
    @FXML
    private HBox btnSair;
    @FXML
    private Circle imgUser;
    @FXML
    private Text textNome;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarItensIniciais();
        loadUI("DashboardCenter");
    }
    private void carregarItensIniciais(){
        if(LoginController.usuario.getImg()!=null){
            File file = null;
            Image img = null;
            String directory = CadastrarUsuariosController.workingDirectory()+LoginController.usuario.getImg();


            file = new File(directory);
            if(file.exists()){
                img = new Image(file.toURI().toString());
                imgUser.setFill(new ImagePattern(img));
            }

        }

        String nome = LoginController.usuario.getNome();
        if(nome.length()>15){
            String[] parts = nome.split(" ");
            nome = parts[0]+" "+parts[1].substring(0,1)+".";
        }
        textNome.setText(nome);
    }
    
    @FXML   
    private void handleOpenDashboard(){
        loadUI("DashboardCenter");
    }
    @FXML
    private void handleOpenClientes(){ loadUI("Clientes"); }
    @FXML
    private void handleOpenServicos(MouseEvent event) {
         loadUI("Servicos");
    }
    @FXML
    private void handleOpenAnimais(MouseEvent event) {
        loadUI("Animal");
    }
    @FXML
    private void handleOpenAgendamentos(){
        loadUI("Agendamento");
    }
    @FXML
    private void handleOpenUsuarios(MouseEvent event) {
         switch (LoginController.usuario.getCargo()){
            case 0:
                Notifications.create().title("Permissão").text("Você não possui permissão para acessar este conteúdo").showError();
                break;
            case 1:
                loadUI("Usuarios");
                break;
        }

    }
    @FXML
    private void handleOpenBackup(){
        loadUI("Backup");
    }
    private void loadUI(String ui){
        
        Parent root = null;    
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/"+ui+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.setCenter(root);
        selecionado = ui;
        atualizarStyle();
    }
    private void atualizarStyle(){
        resetarStyle();
        switch (selecionado){
            case "DashboardCenter":
                btnDashboard.setStyle("-fx-background-color: #f5222d; -fx-border-width: 0px 4px 0px 0px;-fx-border-color: #fff;-fx-alignment: center");
                break;
            case "Clientes":
                btnClientes.setStyle("-fx-background-color: #f5222d; -fx-border-width: 0px 4px 0px 0px;-fx-border-color: #fff;-fx-alignment: center");
                break;
            case "Servicos":
                btnServicos.setStyle("-fx-background-color: #f5222d; -fx-border-width: 0px 4px 0px 0px;-fx-border-color: #fff;-fx-alignment: center");
                break;
            case "Animal":
                btnAnimais.setStyle("-fx-background-color: #f5222d; -fx-border-width: 0px 4px 0px 0px;-fx-border-color: #fff;-fx-alignment: center");
                break;
            case "Agendamento":
                btnAgendamentos.setStyle("-fx-background-color: #f5222d; -fx-border-width: 0px 4px 0px 0px;-fx-border-color: #fff;-fx-alignment: center");
                break;
            case "Usuarios":
                btnUsuarios.setStyle("-fx-background-color: #f5222d; -fx-border-width: 0px 4px 0px 0px;-fx-border-color: #fff;-fx-alignment: center");
                break;
        }
    }
    private void resetarStyle(){
        btnDashboard.setStyle("-fx-alignment: center");
        btnClientes.setStyle("-fx-alignment: center");
        btnServicos.setStyle("-fx-alignment: center");
        btnAnimais.setStyle("-fx-alignment: center");
        btnUsuarios.setStyle("-fx-alignment: center");
        btnAgendamentos.setStyle("-fx-alignment: center");

    }
    @FXML
    private void handleSignOut() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Login");
        stage.show();
        Stage thisStage = (Stage) btnSair.getScene().getWindow();
        thisStage.close();
    }

    


    
}
