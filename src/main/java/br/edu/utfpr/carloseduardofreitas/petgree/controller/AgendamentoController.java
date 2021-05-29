package br.edu.utfpr.carloseduardofreitas.petgree.controller;


import br.edu.utfpr.carloseduardofreitas.petgree.dao.AgendamentoDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.*;
import br.edu.utfpr.carloseduardofreitas.petgree.utils.ColumnFormatter;
import br.edu.utfpr.carloseduardofreitas.petgree.utils.ComboBoxLists;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class AgendamentoController implements Initializable, ICadastro{
    private AgendamentoDao agendamentoDao = new AgendamentoDao();
    private ObservableList<Agendamento> observableList = FXCollections.observableArrayList();
    private List<Agendamento> listaAgendamentos;
    public static Agendamento agendamentoSelecionado = new Agendamento();
    public static Agendamento novoAgendamento = new Agendamento();
    private int pesquisaSelecionada = 0;
    @FXML
    private Text txtData;
    @FXML
    private TableView<Agendamento> tableView;
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
    @FXML
    private ComboBox<?> cbPesquisa;
    @FXML
    private HBox hBoxPesquisa;
    @FXML
    private HBox hBoxInicio;
    @FXML
    private HBox hBoxFim;
    @FXML
    private DatePicker datePickerAgendamento;
    @FXML
    private DatePicker datePickerInicio;
    @FXML
    private DatePicker datePickerFim;
    @FXML
    private Button btnLimpar1;
    @FXML
    private Button btnLimpar2;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
        gerarCb();
    }
    private void gerarCb(){
        cbPesquisa.setItems(ComboBoxLists.gerarPesquisaAgendamento());
        cbPesquisa.getSelectionModel().selectFirst();
    }
    @FXML
    private void atualizarAgendamentos(){
        String res;
        res = cbPesquisa.getSelectionModel().getSelectedItem().toString();
        switch (res){
            case "Data Específica":
                hBoxPesquisa.setVisible(true);
                hBoxInicio.setVisible(false);
                hBoxFim.setVisible(false);
                btnLimpar1.setVisible(true);
                btnLimpar2.setVisible(false);
                pesquisaSelecionada = 0;
                break;
            case "Intervalo de datas":
                hBoxPesquisa.setVisible(false);
                hBoxInicio.setVisible(true);
                hBoxFim.setVisible(true);
                btnLimpar1.setVisible(false);
                btnLimpar2.setVisible(true);
                pesquisaSelecionada = 1;
                break;
        }
    }

    @FXML
    private void adicionar(ActionEvent event) throws IOException {
        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setPago(false);
        LocalDate date = LocalDate.of(1900, Month.JANUARY, 1);
        novoAgendamento.setData(date);
        LocalTime hora = LocalTime.of(0,0);
        novoAgendamento.setHora(hora);
        Animal animal = new Animal();
        Servico servico = new Servico();
        Usuario usuario = new Usuario();
        novoAgendamento.setAnimal(animal);
        novoAgendamento.setUsuario(usuario);
        novoAgendamento.setServico(servico);

        CadastroAgendamentoController.ALTERAR = 0;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroAgendamento.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Adicionar Agendamento");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        atualizarTabela();
    }

    @FXML
    private void alterar(ActionEvent event) throws IOException {
        CadastroAgendamentoController.ALTERAR = 1;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroAgendamento.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PetGree - Alterar Agendamento");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        atualizarTabela();
    }

    @FXML
    private void excluir(ActionEvent event) {
        agendamentoDao.excluir(agendamentoSelecionado);
        Image img = new Image("/images/confirm.png");
        Notifications.create().graphic(new ImageView(img)).title("Serviço").text("Serviço excluído com sucesso!").show();
        atualizarTabela();
    }
    private void atualizarQtd(){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH:mm");
        textQtd.setText(String.valueOf(listaAgendamentos.toArray().length));
        textDia.setText(LocalDate.now().format(formatterDate).toString());
        textHoras.setText(LocalTime.now().format(formatterHour).toString());
        txtData.setText(LocalDate.now().format(formatterDate).toString());
    }

    @Override
    public void criarColunasTabela() {
        tableView.getColumns().clear();
        TableColumn<Agendamento, Long> colunaId = new TableColumn<>("Id");
        TableColumn<Agendamento,LocalDate> colunaData = new TableColumn<>("Data");
        TableColumn<Agendamento, String> colunaUsuario = new TableColumn<>("Funcionário");
        TableColumn<Agendamento, String> colunaAnimal = new TableColumn<>("Animal");
        TableColumn<Agendamento, String> colunaServico= new TableColumn<>("Serviço");
        TableColumn<Agendamento, Boolean> colunaPago = new TableColumn<>("Pago");
        TableColumn<Agendamento, String> colunaCliente = new TableColumn<>("Cliente");

        tableView.getColumns().addAll(colunaId, colunaData,colunaUsuario,colunaAnimal,colunaServico,colunaPago,colunaCliente);
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaData.setCellValueFactory(new PropertyValueFactory("data"));
        colunaData.setCellFactory(new ColumnFormatter<>(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        colunaPago.setCellValueFactory(new PropertyValueFactory("pago"));
        colunaUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getNome()));
        colunaAnimal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnimal().getNome()));
        colunaServico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServico().getNome()));
        colunaCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnimal().getCliente().getNome()));
    }

    @Override
    public void atualizarTabela() {
        observableList.clear();
        switch (pesquisaSelecionada){
            case 0:
                if(datePickerAgendamento.getValue() == null){
                    listaAgendamentos = agendamentoDao.consultar();
                }else{
                    listaAgendamentos = agendamentoDao.consultarData(datePickerAgendamento.getValue());
                }

                break;
            case 1:
                if(datePickerInicio.getValue() == null || datePickerFim.getValue() == null){
                    listaAgendamentos = agendamentoDao.consultar();
                }else{
                    listaAgendamentos = agendamentoDao.consultarIntervalosData(datePickerInicio.getValue(),datePickerFim.getValue());
                }
                break;
        }

        for (Agendamento a : listaAgendamentos) {
            observableList.add(a);
        }
        tableView.getItems().setAll(observableList);
        tableView.getSelectionModel().selectFirst();
        agendamentoSelecionado = tableView.getSelectionModel().getSelectedItem();
        atualizarQtd();
    }
    @FXML
    private void limpar(){
        datePickerAgendamento.setValue(null);
        datePickerInicio.setValue(null);
        datePickerFim.setValue(null);
    }
    @Override
    public void setCamposFormulario() {

    }

    @Override
    public void limparCamposFormulario() {

    }

    @FXML
    private void atualizarSelecionado(MouseEvent event) {
        agendamentoSelecionado = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
    }
}
