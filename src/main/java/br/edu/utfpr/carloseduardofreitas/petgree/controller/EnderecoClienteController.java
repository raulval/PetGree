/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import static br.edu.utfpr.carloseduardofreitas.petgree.controller.CadastroClientesController.ALTERAR;
import br.edu.utfpr.carloseduardofreitas.petgree.dao.EnderecoDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Cliente;
import br.edu.utfpr.carloseduardofreitas.petgree.utils.ComboBoxLists;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author kadu1
 */
public class EnderecoClienteController implements Initializable {

    private EnderecoDao enderecoDao = new EnderecoDao();
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField TextFieldCidade;
    @FXML
    private ComboBox<String> comboBoxEstado;
    @FXML
    private TextField textFieldCodigoPostal;
    @FXML
    private TextField textFIeldRua;
    @FXML
    private TextField textFieldNumero;
    @FXML
    private TextField textFieldBairro;
    @FXML
    private TextField textFieldComplemento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxEstado.setItems(ComboBoxLists.gerarEstados());
        carregarDados();
    }

    private void carregarDados() {
        if (ALTERAR == 1) {
            Cliente cliente = ClientesController.clienteSelecionado;
            TextFieldCidade.setText(cliente.getEndereco().getCidade());
            comboBoxEstado.getSelectionModel().select(cliente.getEndereco().getEstado());
            textFieldCodigoPostal.setText(String.valueOf(cliente.getEndereco().getCodigoPostal()));
            textFIeldRua.setText(cliente.getEndereco().getRua());
            textFieldNumero.setText(String.valueOf(cliente.getEndereco().getNumero()));
            textFieldBairro.setText(cliente.getEndereco().getBairro());
            textFieldComplemento.setText(cliente.getEndereco().getComplemento());
        }
    }

    private void teste(ActionEvent event) {
        Image img = new Image("/images/confirm.png");
        Notifications.create().graphic(new ImageView(img)).title("teste").text("teste").show();
    }

    @FXML
    private void salvar(ActionEvent event) {
        if ((TextFieldCidade.getText().trim().isEmpty()) || (comboBoxEstado.getValue() == null) ||
                (textFieldCodigoPostal.getText().trim().isEmpty()) || (textFIeldRua.getText().trim().isEmpty()) ||
                (textFieldNumero.getText().trim().isEmpty()) || (textFieldBairro.getText().trim().isEmpty())){
            Notifications.create().title("Endereço").text("Por favor preencha todos os campos").showError();

        }else {

            if (CadastroClientesController.ALTERAR == 1) {

                ClientesController.clienteSelecionado.getEndereco().setCidade(TextFieldCidade.getText());
                ClientesController.clienteSelecionado.getEndereco().setEstado(comboBoxEstado.getValue());
                ClientesController.clienteSelecionado.getEndereco().setCodigoPostal(Integer.parseInt(textFieldCodigoPostal.getText()));
                ClientesController.clienteSelecionado.getEndereco().setRua(textFIeldRua.getText());
                ClientesController.clienteSelecionado.getEndereco().setNumero(Integer.parseInt(textFieldNumero.getText()));
                ClientesController.clienteSelecionado.getEndereco().setBairro(textFieldBairro.getText());
                ClientesController.clienteSelecionado.getEndereco().setComplemento(textFieldComplemento.getText());

                enderecoDao.salvar(ClientesController.clienteSelecionado.getEndereco());

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Endereço").text("Endereço atualizado com sucesso").show();

            } else {
                ClientesController.novoCliente.getEndereco().setCidade(TextFieldCidade.getText());
                ClientesController.novoCliente.getEndereco().setEstado(comboBoxEstado.getValue());
                ClientesController.novoCliente.getEndereco().setCodigoPostal(Integer.parseInt(textFieldCodigoPostal.getText()));
                ClientesController.novoCliente.getEndereco().setRua(textFIeldRua.getText());
                ClientesController.novoCliente.getEndereco().setNumero(Integer.parseInt(textFieldNumero.getText()));
                ClientesController.novoCliente.getEndereco().setBairro(textFieldBairro.getText());
                ClientesController.novoCliente.getEndereco().setComplemento(textFieldComplemento.getText());

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Endereço").text("Salve o cliente para que as alterações \n do contato tenham efeito.").show();
            }
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage thisStage = (Stage) btnCancelar.getScene().getWindow();
        thisStage.close();
    }

}
