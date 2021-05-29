/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import br.edu.utfpr.carloseduardofreitas.petgree.dao.AnimalDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Animal;
import br.edu.utfpr.carloseduardofreitas.petgree.utils.ColumnFormatter;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author kadu1
 */
public class AnimalController implements Initializable, ICadastro {

    private AnimalDao animalDao = new AnimalDao();
    private ObservableList<Animal> observableList = FXCollections.observableArrayList();
    private List<Animal> listaAnimal;
    public static Animal animalSelecionado = new Animal();
    public static Animal novoAnimal = new Animal();
    @FXML
    private Text txtData;
    @FXML
    private TableView<Animal> tableView;
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
        Animal novoAnimal = new Animal();
        novoAnimal.setNome("");
        LocalDate date = LocalDate.of(1900, Month.JANUARY, 1);
        novoAnimal.setDataNascimento(date);
        novoAnimal.setPorte("");

        CadastroAnimalController.ALTERAR = 0;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroAnimal.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Adicionar Animais");
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.showAndWait();
        atualizarTabela();
    }

    @FXML
    private void alterar(ActionEvent event) throws IOException {
        CadastroAnimalController.ALTERAR = 1;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroAnimal.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Alterar Animal");
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.showAndWait();
        atualizarTabela();
    }

    @FXML
    private void excluir(ActionEvent event) {
        animalDao.excluir(animalSelecionado);
        Image img = new Image("/images/confirm.png");
        Notifications.create().graphic(new ImageView(img)).title("Animal").text("Animal excluído com sucesso!").show();
        atualizarTabela();
    }
    private void atualizarQtd(){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH:mm");
        textQtd.setText(String.valueOf(listaAnimal.toArray().length));
        textDia.setText(LocalDate.now().format(formatterDate).toString());
        textHoras.setText(LocalTime.now().format(formatterHour).toString());
        txtData.setText(LocalDate.now().format(formatterDate).toString());
    }

    @Override
    public void criarColunasTabela() {
        tableView.getColumns().clear();
        TableColumn<Animal, Long> colunaId = new TableColumn<>("Id");
        TableColumn<Animal, String> colunaNome = new TableColumn<>("Nome");
        TableColumn<Animal, String> colunaPorte = new TableColumn<>("Porte");
        TableColumn colunaDataNascimento = new TableColumn<>("Data de Nascimento");
        TableColumn<Animal, String> colunaClienteAnimal = new TableColumn<>("Cliente");

        tableView.getColumns().addAll(colunaId,colunaNome,colunaPorte,colunaDataNascimento, colunaClienteAnimal);
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colunaDataNascimento.setCellValueFactory(new PropertyValueFactory("dataNascimento"));
        colunaDataNascimento.setCellFactory(new ColumnFormatter<>(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        colunaPorte.setCellValueFactory(new PropertyValueFactory("porte"));
        colunaClienteAnimal.setCellValueFactory(new PropertyValueFactory("Cliente"));

    }

    @Override
    @FXML
    public void atualizarTabela() {
        observableList.clear();
        listaAnimal = animalDao.consultar(textFieldPesquisa.getText());

        for (Animal s : listaAnimal) {
            observableList.add(s);
        }
        tableView.getItems().setAll(observableList);
        tableView.getSelectionModel().selectFirst();
        animalSelecionado = tableView.getSelectionModel().getSelectedItem();
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
        animalSelecionado = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
    }

}
