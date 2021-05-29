/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import br.edu.utfpr.carloseduardofreitas.petgree.dao.ClienteDao;
import br.edu.utfpr.carloseduardofreitas.petgree.dao.ContatoDao;
import br.edu.utfpr.carloseduardofreitas.petgree.dao.EnderecoDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Cliente;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Contato;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Endereco;
import br.edu.utfpr.carloseduardofreitas.petgree.utils.ColumnFormatter;
import br.edu.utfpr.carloseduardofreitas.petgree.utils.ComboBoxLists;
import java.io.IOException;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author kadu1
 */
public class ClientesController implements Initializable, ICadastro {

    private ClienteDao clienteDao = new ClienteDao();
    private ContatoDao contatoDao = new ContatoDao();
    private EnderecoDao enderecoDao = new EnderecoDao();
    private ObservableList<Cliente> observableList = FXCollections.observableArrayList();
    private List<Cliente> listaClientes;
    public static Cliente clienteSelecionado = new Cliente();
    public static Cliente novoCliente = new Cliente();
    private int pesquisaSelecionada = 0;
    @FXML
    private Text txtData;
    @FXML
    private TableView<Cliente> tableView;
    @FXML
    private TextField textFieldPesquisa;
    @FXML
    private ComboBox<String> cbExibicao;
    @FXML
    private ComboBox<?> cbPesquisa;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
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
        cbExibicao.setItems(ComboBoxLists.gerarExibicaoCliente());
        cbPesquisa.setItems(ComboBoxLists.gerarPesquisaCliente());
        cbExibicao.getSelectionModel().selectFirst();
        cbPesquisa.getSelectionModel().selectFirst();
        criarColunasTabela();
        atualizarTabela();
    }

    private void atualizarQtd(){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH:mm");
        textQtd.setText(String.valueOf(listaClientes.toArray().length));
        textDia.setText(LocalDate.now().format(formatterDate).toString());
        textHoras.setText(LocalTime.now().format(formatterHour).toString());
        txtData.setText(LocalDate.now().format(formatterDate).toString());
    }
    @Override
    public void criarColunasTabela() {
        tableView.getColumns().clear();
        TableColumn<Cliente, Long> colunaId = new TableColumn<>("Id");
        TableColumn<Cliente, String> colunaNome = new TableColumn<>("Nome");
        TableColumn<Cliente, String> colunaCpf = new TableColumn<>("Cpf");
        TableColumn colunaDataNascimento = new TableColumn<>("Data de Nascimento");

        TableColumn<Cliente, String> colunaCidade = new TableColumn<>("Cidade");
        TableColumn<Cliente, String> colunaEstado = new TableColumn<>("Estado");
        TableColumn<Cliente, String> colunaCodigoPostal = new TableColumn<>("Código Postal");
        TableColumn<Cliente, String> colunaRua = new TableColumn<>("Rua");
        TableColumn<Cliente, String> colunaNumero = new TableColumn<>("Número");
        TableColumn<Cliente, String> colunaBairro = new TableColumn<>("Bairro");
        TableColumn<Cliente, String> colunaComplemento = new TableColumn<>("Complemento");

        TableColumn<Cliente, String> colunaEmail = new TableColumn<>("Email");
        TableColumn<Cliente, String> colunaTelefoneCelular = new TableColumn<>("Telefone celular");
        TableColumn<Cliente, String> colunaTelefoneFixo = new TableColumn<>("Telefone fixo");

        if (cbExibicao.getSelectionModel().getSelectedIndex() == 0) {
            tableView.getColumns().addAll(colunaId, colunaNome, colunaCpf, colunaDataNascimento, colunaCidade, colunaEstado, colunaCodigoPostal, colunaRua,
                    colunaNumero, colunaBairro, colunaComplemento, colunaEmail, colunaTelefoneCelular, colunaTelefoneFixo);

            colunaId.setCellValueFactory(new PropertyValueFactory("id"));
            colunaNome.setCellValueFactory(new PropertyValueFactory("nome"));
            colunaCpf.setCellValueFactory(new PropertyValueFactory("cpf"));
            colunaDataNascimento.setCellValueFactory(new PropertyValueFactory("dataNascimento"));
            colunaDataNascimento.setCellFactory(new ColumnFormatter<>(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            colunaCidade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndereco().getCidade()));
            colunaEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndereco().getEstado()));
            colunaCodigoPostal.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEndereco().getCodigoPostal()).asString());
            colunaRua.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndereco().getRua()));
            colunaNumero.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEndereco().getNumero()).asString());
            colunaBairro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndereco().getBairro()));
            colunaComplemento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndereco().getComplemento()));

            colunaEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContato().getEmail()));
            colunaTelefoneCelular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContato().getTelefoneCelular()));
            colunaTelefoneFixo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContato().getTelefoneFixo()));

        }
        if (cbExibicao.getSelectionModel().getSelectedIndex() == 1) {
            tableView.getColumns().addAll(colunaId, colunaNome, colunaCpf, colunaDataNascimento);
            colunaId.setCellValueFactory(new PropertyValueFactory("id"));
            colunaNome.setCellValueFactory(new PropertyValueFactory("nome"));
            colunaCpf.setCellValueFactory(new PropertyValueFactory("cpf"));
            colunaDataNascimento.setCellValueFactory(new PropertyValueFactory("dataNascimento"));
            colunaDataNascimento.setCellFactory(new ColumnFormatter<>(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        }
        if (cbExibicao.getSelectionModel().getSelectedIndex() == 2) {
            tableView.getColumns().addAll(colunaCidade, colunaEstado, colunaCodigoPostal, colunaRua,
                    colunaNumero, colunaBairro, colunaComplemento);
            colunaCidade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndereco().getCidade()));
            colunaEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndereco().getEstado()));
            colunaCodigoPostal.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEndereco().getCodigoPostal()).asString());
            colunaRua.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndereco().getRua()));
            colunaNumero.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEndereco().getNumero()).asString());
            colunaBairro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndereco().getBairro()));
            colunaComplemento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndereco().getComplemento()));

        }
        if (cbExibicao.getSelectionModel().getSelectedIndex() == 3) {
            tableView.getColumns().addAll(colunaEmail, colunaTelefoneCelular, colunaTelefoneFixo);
            colunaEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContato().getEmail()));
            colunaTelefoneCelular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContato().getTelefoneCelular()));
            colunaTelefoneFixo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContato().getTelefoneFixo()));
        }

    }

    @FXML
    public void comboExibicao(ActionEvent event) {
        criarColunasTabela();
    }

    @FXML
    private void mudarPesquisa(){
        String res = cbPesquisa.getSelectionModel().getSelectedItem().toString();
        switch (res){
            case "Nome":
                pesquisaSelecionada = 0;
                break;
            case "CPF":
                pesquisaSelecionada = 1;
                break;
        }
    }
    @Override
    @FXML
    public void atualizarTabela() {
        if (pesquisaSelecionada == 0){
            observableList.clear();
            listaClientes = clienteDao.consultar(textFieldPesquisa.getText());

            for (Cliente c : listaClientes) {
                observableList.add(c);
            }
            tableView.getItems().setAll(observableList);
            tableView.getSelectionModel().selectFirst();
            clienteSelecionado = tableView.getSelectionModel().getSelectedItem();
            atualizarQtd();
        }else if(pesquisaSelecionada == 1){
            observableList.clear();
            listaClientes = clienteDao.consultarCpf(textFieldPesquisa.getText());

            for (Cliente c : listaClientes) {
                observableList.add(c);
            }
            tableView.getItems().setAll(observableList);
            tableView.getSelectionModel().selectFirst();
            clienteSelecionado = tableView.getSelectionModel().getSelectedItem();
            atualizarQtd();
        }


    }

    @Override
    @FXML
    public void setCamposFormulario() {
        clienteSelecionado = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
    }

    @Override
    public void limparCamposFormulario() {
    }

    @FXML
    private void adicionar(ActionEvent event) throws IOException {

        Endereco end = new Endereco();
        end.setCidade("");
        end.setEstado("");
        end.setCodigoPostal(0);
        end.setRua("");
        end.setNumero(0);
        end.setBairro("");
        end.setComplemento("");

        Contato con = new Contato();
        con.setEmail("");
        con.setTelefoneCelular("");
        con.setTelefoneFixo("");

        novoCliente.setNome("");
        novoCliente.setCpf("");
        LocalDate date = LocalDate.of(1900, Month.JANUARY, 1);
        novoCliente.setDataNascimento(date);
        novoCliente.setEndereco(end);
        novoCliente.setContato(con);

        CadastroClientesController.ALTERAR = 0;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroClientes.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Adicionar Cliente");
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.showAndWait();
        atualizarTabela();

    }

    @FXML
    private void alterar(ActionEvent event) throws IOException {

        CadastroClientesController.ALTERAR = 1;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroClientes.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Alterar Cliente");
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.showAndWait();
        atualizarTabela();
    }

    @FXML
    private void excluir(ActionEvent event) {
        clienteDao.excluir(clienteSelecionado);
        Image img = new Image("/images/confirm.png");
        Notifications.create().graphic(new ImageView(img)).title("Cliente").text("Cliente excluído com sucesso!").show();
        atualizarTabela();
    }

}
