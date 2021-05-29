/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import br.edu.utfpr.carloseduardofreitas.petgree.dao.AnimalDao;
import br.edu.utfpr.carloseduardofreitas.petgree.dao.ClienteDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Animal;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Cliente;
import br.edu.utfpr.carloseduardofreitas.petgree.utils.ComboBoxLists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author kadu1
 */
public class CadastroAnimalController implements Initializable {
    private AnimalDao animalDao = new AnimalDao();
    private ClienteDao clienteDao = new ClienteDao();
    public static int ALTERAR = 0;
    @FXML
    private TextField textFieldNome;
    @FXML
    private DatePicker datePickerData;
    @FXML
    private ComboBox<String> comboBoxPorte;
    @FXML
    private ComboBox<Cliente> comboBoxCliente;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxPorte.setItems(ComboBoxLists.gerarPorte());
        carregarDados();
        carregarClientes();
    }

    private void carregarClientes(){
        List<Cliente> clientes = clienteDao.consultar("");
        ObservableList<Cliente> clientesObs = FXCollections.observableArrayList();
        for(Cliente c:clientes){
            clientesObs.add(c);
        }
        comboBoxCliente.setItems(clientesObs);
    }

    private void carregarDados() {
        if (ALTERAR == 1) {
            Animal animal = AnimalController.animalSelecionado;
            textFieldNome.setText(animal.getNome());
            datePickerData.setValue(animal.getDataNascimento());
            comboBoxPorte.getSelectionModel().select(animal.getPorte());
            comboBoxCliente.getSelectionModel().select(animal.getCliente());
        }
    }
    
    @FXML
    private void salvar(ActionEvent event) {
        if((textFieldNome.getText().trim().isEmpty()) || (comboBoxCliente.getValue() == null)){
            if(textFieldNome.getText().trim().isEmpty()){
                Notifications.create().title("Animal").text("Campo nome é obrigatório").showError();
                textFieldNome.requestFocus();
            }else {
                Notifications.create().title("Animal").text("Campo do cliente é obrigatório").showError();
                comboBoxCliente.requestFocus();
            }
        }else {

            if (ALTERAR == 1) {

                AnimalController.animalSelecionado.setNome(textFieldNome.getText());
                AnimalController.animalSelecionado.setDataNascimento(datePickerData.getValue());
                AnimalController.animalSelecionado.setPorte(comboBoxPorte.getValue());
                animalDao.salvar(AnimalController.animalSelecionado);

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Animal").text("Animal atualizado com sucesso").show();

            } else {

                AnimalController.novoAnimal.setNome(textFieldNome.getText());
                AnimalController.novoAnimal.setDataNascimento(datePickerData.getValue());
                AnimalController.novoAnimal.setPorte(comboBoxPorte.getValue());
                AnimalController.novoAnimal.setCliente(comboBoxCliente.getValue());
                AnimalController.novoAnimal.setDataCadastro(LocalDate.now());
                animalDao.salvar(AnimalController.novoAnimal);

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Animal").text("Animal criado com sucesso").show();

            }
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage thisStage = (Stage) btnCancelar.getScene().getWindow();
        thisStage.close();
    }
    
}
