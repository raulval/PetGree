/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import br.edu.utfpr.carloseduardofreitas.petgree.dao.ClienteDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Cliente;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Contato;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Endereco;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
public class CadastroClientesController implements Initializable {

    
    private ClienteDao clienteDao = new ClienteDao();
    public static int ALTERAR = 0;
    @FXML
    private Button btnContato;
    @FXML
    private Button btnEndereco;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField textFieldNome;
    @FXML
    private DatePicker datePickerData;
    @FXML
    private TextField textFieldCpf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarDados();
    }

    private void carregarDados() {
        if (ALTERAR == 1) {
            Cliente cliente = ClientesController.clienteSelecionado;
            textFieldNome.setText(cliente.getNome());
            textFieldCpf.setText(cliente.getCpf());
            datePickerData.setValue(cliente.getDataNascimento());
        }
    }

    @FXML
    private void contato(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ContatoCliente.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Contato");
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.show();

    }

    @FXML
    private void endereco(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/EnderecoCliente.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Endereço");
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.show();
    }

    @FXML
    private void salvar(ActionEvent event) {
        if (textFieldNome.getText().trim().isEmpty() || textFieldCpf.getText().trim().isEmpty()){
            if (textFieldNome.getText().trim().isEmpty()){
                textFieldNome.requestFocus();
                Notifications.create().title("Cliente").text("O campo nome é obrigatório").showError();
            }else {
                textFieldCpf.requestFocus();
                Notifications.create().title("Cliente").text("O campo CPF é obrigatório").showError();
            }
        }else {
            if (ALTERAR == 1) {

                ClientesController.clienteSelecionado.setNome(textFieldNome.getText());
                ClientesController.clienteSelecionado.setCpf(textFieldCpf.getText());
                ClientesController.clienteSelecionado.setDataNascimento(datePickerData.getValue());
                clienteDao.salvar(ClientesController.clienteSelecionado);

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Cliente").text("Cliente atualizado com sucesso").show();

            }else{


                ClientesController.novoCliente.setNome(textFieldNome.getText());
                ClientesController.novoCliente.setCpf(textFieldCpf.getText());
                ClientesController.novoCliente.setDataNascimento(datePickerData.getValue());
                LocalDate date = LocalDate.now();
                ClientesController.novoCliente.setDataCadastro(date);
                clienteDao.salvar(ClientesController.novoCliente);

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Cliente").text("Cliente criado com sucesso").show();

            }
        }


    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage thisStage = (Stage) btnCancelar.getScene().getWindow();
        thisStage.close();
    }

}
