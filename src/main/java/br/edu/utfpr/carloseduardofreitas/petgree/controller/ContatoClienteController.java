/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import static br.edu.utfpr.carloseduardofreitas.petgree.controller.CadastroClientesController.ALTERAR;
import br.edu.utfpr.carloseduardofreitas.petgree.dao.ClienteDao;
import br.edu.utfpr.carloseduardofreitas.petgree.dao.ContatoDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Cliente;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class ContatoClienteController implements Initializable {

    private ContatoDao contatoDao = new ContatoDao();
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField TextFieldEmail;
    @FXML
    private TextField TextFieldTelefoneC;
    @FXML
    private TextField TextFieldTelefoneF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarDados();
    }

    @FXML
    private void salvar(ActionEvent event) throws IOException {
        if(TextFieldEmail.getText().trim().isEmpty() && TextFieldTelefoneC.getText().trim().isEmpty() && TextFieldTelefoneF.getText().trim().isEmpty()){
            Notifications.create().title("Contato").text("Por favor preencha ao menos um contato").showError();

        }else {
            if (CadastroClientesController.ALTERAR == 1) {

                ClientesController.clienteSelecionado.getContato().setEmail(TextFieldEmail.getText());
                ClientesController.clienteSelecionado.getContato().setTelefoneCelular(TextFieldTelefoneC.getText());
                ClientesController.clienteSelecionado.getContato().setTelefoneFixo(TextFieldTelefoneF.getText());
                contatoDao.salvar(ClientesController.clienteSelecionado.getContato());

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Contato").text("Contato atualizado com sucesso").show();

            } else {
                ClientesController.novoCliente.getContato().setEmail(TextFieldEmail.getText());
                ClientesController.novoCliente.getContato().setTelefoneCelular(TextFieldTelefoneC.getText());
                ClientesController.novoCliente.getContato().setTelefoneFixo(TextFieldTelefoneF.getText());

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Contato").text("Salve o cliente para que as alterações \n do contato tenham efeito.").show();
            }

        }
    }

    private void carregarDados() {
        if (CadastroClientesController.ALTERAR == 1) {
            Cliente cliente = ClientesController.clienteSelecionado;
            TextFieldEmail.setText(cliente.getContato().getEmail());
            TextFieldTelefoneC.setText(cliente.getContato().getTelefoneCelular());
            TextFieldTelefoneF.setText(cliente.getContato().getTelefoneFixo());
        }
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        Stage thisStage = (Stage) btnCancelar.getScene().getWindow();
        thisStage.close();
    }

}
