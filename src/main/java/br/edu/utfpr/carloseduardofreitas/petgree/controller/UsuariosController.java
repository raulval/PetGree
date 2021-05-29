/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import br.edu.utfpr.carloseduardofreitas.petgree.dao.UsuarioDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Usuario;
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
public class UsuariosController implements Initializable,ICadastro {
    
    private UsuarioDao usuarioDao = new UsuarioDao();
    private ObservableList<Usuario> observableList = FXCollections.observableArrayList();
    private List<Usuario> listaUsuarios;
    public static Usuario UsuarioSelecionado = new Usuario();
    public static Usuario novoUsuario = new Usuario();
    @FXML
    private Text txtData;
    @FXML
    private TableView<Usuario> tableView;
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

  
    @Override
    public void criarColunasTabela() {
        tableView.getColumns().clear();
        TableColumn<Usuario, Long> colunaId = new TableColumn<>("Id");
        TableColumn<Usuario, String> colunaNome = new TableColumn<>("Nome");
        TableColumn<Usuario, String> colunaEmail = new TableColumn<>("Email");
        TableColumn<Usuario, String> colunaSenha = new TableColumn<>("Senha");
        TableColumn<Usuario, Integer> colunaCargo = new TableColumn<>("Cargo");
        TableColumn<Usuario, Integer> colunaFuncao = new TableColumn<>("Função");

        tableView.getColumns().addAll(colunaId, colunaNome, colunaEmail,colunaSenha,colunaCargo,colunaFuncao);
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colunaSenha.setCellValueFactory(new PropertyValueFactory("senha"));
        colunaCargo.setCellValueFactory(new PropertyValueFactory("cargo"));
        colunaFuncao.setCellValueFactory(new PropertyValueFactory("funcao"));
        
    }

    @Override
    @FXML
    public void atualizarTabela() {   
        observableList.clear();
        listaUsuarios = usuarioDao.consultar(textFieldPesquisa.getText());

        for (Usuario s : listaUsuarios) {
            observableList.add(s);
        }
        tableView.getItems().setAll(observableList);
        tableView.getSelectionModel().selectFirst();
        UsuarioSelecionado = tableView.getSelectionModel().getSelectedItem();
        atualizarQtd();
    }
    @FXML
    private void alterar(ActionEvent event) throws IOException {
        CadastrarUsuariosController.ALTERAR = 1;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastrarUsuarios.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Alterar Usuário");
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.showAndWait();
        atualizarTabela();

    }

    @FXML
    private void excluir(ActionEvent event) {
        usuarioDao.excluir(UsuarioSelecionado);
        Image img = new Image("/images/confirm.png");
        Notifications.create().graphic(new ImageView(img)).title("Funcionário").text("Funcionário excluído com sucesso!").show();
        atualizarTabela();
    }

    @FXML
    private void adicionar(ActionEvent event) throws IOException {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("");
        novoUsuario.setEmail("");
        novoUsuario.setSenha("");
        novoUsuario.setCargo(0);
        novoUsuario.setFuncao("");

        CadastrarUsuariosController.ALTERAR = 0;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastrarUsuarios.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Adicionar Usuário");
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.showAndWait();
        atualizarTabela();
    }
    private void atualizarQtd(){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH:mm");
        textQtd.setText(String.valueOf(listaUsuarios.toArray().length));
        textDia.setText(LocalDate.now().format(formatterDate).toString());
        textHoras.setText(LocalTime.now().format(formatterHour).toString());
        txtData.setText(LocalDate.now().format(formatterDate).toString());
    }
    @FXML
    private void atualizarSelecionado(MouseEvent event) {
        UsuarioSelecionado = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
    }
    @Override
    public void setCamposFormulario() {    
    }

    @Override
    public void limparCamposFormulario() {    
    }
    
}
