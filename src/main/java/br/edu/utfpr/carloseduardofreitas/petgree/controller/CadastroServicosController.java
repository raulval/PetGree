/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import br.edu.utfpr.carloseduardofreitas.petgree.dao.ServicoDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Servico;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class CadastroServicosController implements Initializable {
    private ServicoDao servicoDao = new ServicoDao();
    public static int ALTERAR = 0;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldPreco;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       carregarDados();
    }    
    private void carregarDados() {
        if (ALTERAR == 1) {
            Servico Servico = ServicosController.servicoSelecionado;
            textFieldNome.setText(Servico.getNome());
            textFieldPreco.setText(String.valueOf(Servico.getPreco()));
        }
    }
    
    @FXML
    private void salvar(ActionEvent event) {
        if (textFieldNome.getText().trim().isEmpty() || textFieldPreco.getText().trim().isEmpty()){
            if (textFieldNome.getText().trim().isEmpty()){
                textFieldNome.requestFocus();
                Notifications.create().title("Serviço").text("O campo nome é obrigatório").showError();
            }else {
                textFieldPreco.requestFocus();
                Notifications.create().title("Serviço").text("O campo preço é obrigatório").showError();
            }

        }else{
            if (ALTERAR == 1) {

                ServicosController.servicoSelecionado.setNome(textFieldNome.getText());
                ServicosController.servicoSelecionado.setPreco(Double.parseDouble(textFieldPreco.getText()));
                servicoDao.salvar(ServicosController.servicoSelecionado);

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Serviço").text("Serviço atualizado com sucesso").show();

            }else{


                ServicosController.novoServico.setNome(textFieldNome.getText());
                ServicosController.novoServico.setPreco(Double.parseDouble(textFieldPreco.getText()));
                servicoDao.salvar(ServicosController.novoServico);

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Serviço").text("Serviço criado com sucesso").show();

            }
        }


    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage thisStage = (Stage) btnCancelar.getScene().getWindow();
        thisStage.close();
    }
    
}
