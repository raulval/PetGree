/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import br.edu.utfpr.carloseduardofreitas.petgree.dao.UsuarioDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Usuario;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import br.edu.utfpr.carloseduardofreitas.petgree.utils.ComboBoxLists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author kadu1
 */
public class CadastrarUsuariosController implements Initializable {
    private UsuarioDao servicoDao = new UsuarioDao();
    public static int ALTERAR = 0;
    private  File img;
    private  String toFilePath;
    private  String toBd;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldSenha;
    @FXML
    private ComboBox<String> comboBoxCargo;
    @FXML
    private TextField textFieldFuncao;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField textFieldFoto;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxCargo.setItems(ComboBoxLists.gerarTipoFunc());
        carregarDados();
    }    
    
    private void carregarDados() {


        if (ALTERAR == 1) {
            Usuario usuario = UsuariosController.UsuarioSelecionado;
            textFieldNome.setText(usuario.getNome());
            textFieldEmail.setText(usuario.getEmail());
            textFieldSenha.setText(usuario.getSenha());
            switch (usuario.getCargo()){
                case 0:
                    comboBoxCargo.getSelectionModel().select("Funcionário");
                    break;
                case 1:
                    comboBoxCargo.getSelectionModel().select("Administrador");
                    break;
            }
            textFieldFuncao.setText(usuario.getFuncao());
            textFieldFoto.setText(usuario.getImg());
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        if (textFieldNome.getText().trim().isEmpty() || textFieldEmail.getText().trim().isEmpty()){
            if (textFieldNome.getText().trim().isEmpty()){
                textFieldNome.requestFocus();
                Notifications.create().title("Funcionário").text("O campo nome é obrigatório").showError();
            }else {
                textFieldEmail.requestFocus();
                Notifications.create().title("Funcionário").text("O campo email é obrigatório").showError();
            }

        }else {
            if (ALTERAR == 1) {
                UsuariosController.UsuarioSelecionado.setNome(textFieldNome.getText());
                UsuariosController.UsuarioSelecionado.setEmail(textFieldEmail.getText());
                UsuariosController.UsuarioSelecionado.setSenha(textFieldSenha.getText());
                switch (comboBoxCargo.getValue()){
                    case "Funcionário":
                        UsuariosController.UsuarioSelecionado.setCargo(0);
                        break;
                    case "Administrador":
                        UsuariosController.UsuarioSelecionado.setCargo(1);
                        break;
                }
                UsuariosController.UsuarioSelecionado.setFuncao(textFieldFuncao.getText());
                if(img != null){
                    salvarFoto();
                    UsuariosController.UsuarioSelecionado.setImg(toBd);
                }

                servicoDao.salvar(UsuariosController.UsuarioSelecionado);

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Funcionário").text("Funcionário atualizado com sucesso").show();

            }else{


                UsuariosController.novoUsuario.setNome(textFieldNome.getText());
                UsuariosController.novoUsuario.setEmail(textFieldEmail.getText());
                UsuariosController.novoUsuario.setSenha(textFieldSenha.getText());
                switch (comboBoxCargo.getValue()){
                    case "Funcionário":
                        UsuariosController.novoUsuario.setCargo(0);
                        break;
                    case "Administrador":
                        UsuariosController.novoUsuario.setCargo(1);
                        break;
                }
                UsuariosController.novoUsuario.setFuncao(textFieldFuncao.getText());
                if(img != null){
                    salvarFoto();
                    UsuariosController.novoUsuario.setImg(toBd);
                }
                servicoDao.salvar(UsuariosController.novoUsuario);

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Funcionário").text("Funcionário criado com sucesso").show();



            }
        }


    }
    @FXML
    private void selecionarFoto() throws IOException {
        FileChooser fc = new FileChooser();
        Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Img file","*png;*jpg;*jpeg;*bmp"));
        File file = fc.showOpenDialog(thisStage);
        textFieldFoto.setText(file.getAbsolutePath());
        img = file;


    }
    private void salvarFoto(){
        Date date = new Date();
        String userDirectory = workingDirectory();
        toBd = "\\profile_img\\"  + date.getTime() + img.getName();
        toFilePath = userDirectory+  "/profile_img/"  + date.getTime() + img.getName();
        File dirTo = new File(toFilePath);
        try {
            copyFile(img, dirTo);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    public static String workingDirectory() {
        String userDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
        return  userDirectory;
    }


    @FXML
    private void cancelar(ActionEvent event) {
        Stage thisStage = (Stage) btnCancelar.getScene().getWindow();
        thisStage.close();
    }

    public static void copyFile( File from, File to ) throws IOException {
        Files.copy( from.toPath(), to.toPath() );
    }
}
