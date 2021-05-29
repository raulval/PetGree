/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import br.edu.utfpr.carloseduardofreitas.petgree.dao.*;
import br.edu.utfpr.carloseduardofreitas.petgree.model.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.utfpr.carloseduardofreitas.petgree.utils.ComboBoxLists;
import com.jfoenix.controls.JFXTimePicker;
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

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author kadu1
 */
public class CadastroAgendamentoController implements Initializable {
    private AgendamentoDao agendamentooDao = new AgendamentoDao();
    private AnimalDao animalDao = new AnimalDao();
    private UsuarioDao usuarioDao = new UsuarioDao();
    private ServicoDao servicoDao = new ServicoDao();
    public static int ALTERAR = 0;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;
    @FXML
    private DatePicker datePickerData;

    @FXML
    private ComboBox<Animal> comboBoxAnimal;
    @FXML
    private ComboBox<Usuario> comboBoxFuncionario;
    @FXML
    private ComboBox<Servico> comboBoxServico;
    @FXML
    private ComboBox<String> comboBoxPago;
    @FXML
    private JFXTimePicker timePickerHora;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarAnimais();
        carregarUsuarios();
        carregarServico();
        carregarDados();
        carregarPago();

    }
    private void carregarPago(){
        comboBoxPago.setItems(ComboBoxLists.gerarPago());
    }
    private void carregarAnimais(){
        List<Animal> animais = animalDao.consultar("");
        ObservableList<Animal> animaisObs = FXCollections.observableArrayList();
        for(Animal a:animais){
            animaisObs.add(a);
        }
        comboBoxAnimal.setItems(animaisObs);
    }
    private void carregarUsuarios(){
        List<Usuario> usuarios = usuarioDao.consultar("");
        ObservableList<Usuario> usuariosObs = FXCollections.observableArrayList();
        for(Usuario u:usuarios){
            usuariosObs.add(u);
        }
        comboBoxFuncionario.setItems(usuariosObs);
    }
    private void carregarServico(){
        List<Servico> servicos = servicoDao.consultar("");
        ObservableList<Servico> servicosObs = FXCollections.observableArrayList();
        for(Servico s:servicos){
            servicosObs.add(s);
        }
        comboBoxServico.setItems(servicosObs);
    }
    private void carregarDados() {
        if (ALTERAR == 1) {
            Agendamento agendamento = AgendamentoController.agendamentoSelecionado;
            datePickerData.setValue(agendamento.getData());
            timePickerHora.setValue(agendamento.getHora());
            comboBoxAnimal.getSelectionModel().select(agendamento.getAnimal());
            comboBoxFuncionario.getSelectionModel().select(agendamento.getUsuario());
            comboBoxServico.getSelectionModel().select(agendamento.getServico());
            boolean res = agendamento.isPago();
            if (!res) {
                comboBoxPago.getSelectionModel().select("Não");
            } else {
                comboBoxPago.getSelectionModel().select("Sim");
            }

        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        if ((datePickerData.getValue() == null) || (comboBoxAnimal.getValue() == null) || (comboBoxFuncionario.getValue() == null) ||
                (comboBoxServico.getValue() == null) || (comboBoxPago.getValue() == null) || (timePickerHora.getValue() == null)){
            Notifications.create().title("Agendamento").text("Por favor preencha todos os campos").showError();
        }else {
            if (ALTERAR == 1) {
                AgendamentoController.agendamentoSelecionado.setUsuario(comboBoxFuncionario.getValue());
                AgendamentoController.agendamentoSelecionado.setAnimal(comboBoxAnimal.getValue());
                AgendamentoController.agendamentoSelecionado.setServico(comboBoxServico.getValue());
                AgendamentoController.agendamentoSelecionado.setData(datePickerData.getValue());
                AgendamentoController.agendamentoSelecionado.setHora(timePickerHora.getValue());

                String res = comboBoxPago.getValue();
                switch (res){
                    case "Sim":
                        AgendamentoController.agendamentoSelecionado.setPago(true);
                        break;
                    case "Não":
                        AgendamentoController.agendamentoSelecionado.setPago(false);
                        break;
                }
                agendamentooDao.salvar(AgendamentoController.agendamentoSelecionado);

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Agendamento").text("Agendamento atualizado com sucesso").show();

            }else{


                AgendamentoController.novoAgendamento.setUsuario(comboBoxFuncionario.getValue());
                AgendamentoController.novoAgendamento.setAnimal(comboBoxAnimal.getValue());
                AgendamentoController.novoAgendamento.setServico(comboBoxServico.getValue());
                AgendamentoController.novoAgendamento.setData(datePickerData.getValue());
                AgendamentoController.novoAgendamento.setHora(timePickerHora.getValue());
                String res = comboBoxPago.getValue();
                switch (res){
                    case "Sim":
                        AgendamentoController.novoAgendamento.setPago(true);
                        break;
                    case "Não":
                        AgendamentoController.novoAgendamento.setPago(false);
                        break;
                }
                AgendamentoController.novoAgendamento.setDataCadastro(LocalDate.now());
                agendamentooDao.salvar(AgendamentoController.novoAgendamento);

                Stage thisStage = (Stage) btnSalvar.getScene().getWindow();
                thisStage.close();
                Image img = new Image("/images/confirm.png");
                Notifications.create().graphic(new ImageView(img)).title("Agendamento").text("Agendamento criado com sucesso").show();


            }
        }


    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage thisStage = (Stage) btnCancelar.getScene().getWindow();
        thisStage.close();
    }

}
