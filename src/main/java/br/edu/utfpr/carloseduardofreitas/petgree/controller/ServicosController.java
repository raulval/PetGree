/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import br.edu.utfpr.carloseduardofreitas.petgree.dao.ServicoDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Servico;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author kadu1
 */
public class ServicosController implements Initializable, ICadastro {

    private ServicoDao servicoDao = new ServicoDao();
    private ObservableList<Servico> observableList = FXCollections.observableArrayList();
    private List<Servico> listaServicos;
    public static Servico servicoSelecionado = new Servico();
    public static Servico novoServico = new Servico();
    @FXML
    private Text txtData;
    @FXML
    private TableView<Servico> tableView;
    @FXML
    private TextField textFieldPesquisa;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Text textQtd;
    @FXML
    private Text textDia;
    @FXML
    private Text textHoras;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
    }


    @FXML
    private void adicionar(ActionEvent event) throws IOException {
        Servico novoServico = new Servico();
        novoServico.setNome("");
        novoServico.setPreco(0.0);

        CadastroServicosController.ALTERAR = 0;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroServicos.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Adicionar Servico");
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.showAndWait();
        atualizarTabela();
    }

    @FXML
    private void alterar(ActionEvent event) throws IOException {
        CadastroServicosController.ALTERAR = 1;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroServicos.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Alterar Servico");
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.showAndWait();
        atualizarTabela();
    }

    @FXML
    private void excluir(ActionEvent event) {
        servicoDao.excluir(servicoSelecionado);
        Image img = new Image("/images/confirm.png");
        Notifications.create().graphic(new ImageView(img)).title("Serviço").text("Serviço excluído com sucesso!").show();
        atualizarTabela();
    }
    private void atualizarQtd(){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH:mm");
        textQtd.setText(String.valueOf(listaServicos.toArray().length));
        textDia.setText(LocalDate.now().format(formatterDate).toString());
        textHoras.setText(LocalTime.now().format(formatterHour).toString());
        txtData.setText(LocalDate.now().format(formatterDate).toString());
    }

    @Override
    public void criarColunasTabela() {
        tableView.getColumns().clear();
        TableColumn<Servico, Long> colunaId = new TableColumn<>("Id");
        TableColumn<Servico, String> colunaNome = new TableColumn<>("Nome");
        TableColumn<Servico, Double> colunaPreco = new TableColumn<>("Preço");

        tableView.getColumns().addAll(colunaId, colunaNome, colunaPreco);
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory("preco"));

    }

    @Override
    @FXML
    public void atualizarTabela() {
        observableList.clear();
        listaServicos = servicoDao.consultar(textFieldPesquisa.getText());

        for (Servico s : listaServicos) {
            observableList.add(s);
        }
        tableView.getItems().setAll(observableList);
        tableView.getSelectionModel().selectFirst();
        servicoSelecionado = tableView.getSelectionModel().getSelectedItem();
        atualizarQtd();
    }

    @Override
    public void setCamposFormulario() {
       
    }

    @Override
    public void limparCamposFormulario() {

    }

    @FXML
    private void atualizarSelecionado(MouseEvent event) {
        servicoSelecionado = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
    }

}
