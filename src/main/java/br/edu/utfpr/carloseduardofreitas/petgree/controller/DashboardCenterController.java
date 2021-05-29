/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.utfpr.carloseduardofreitas.petgree.dao.AgendamentoDao;
import br.edu.utfpr.carloseduardofreitas.petgree.dao.AnimalDao;
import br.edu.utfpr.carloseduardofreitas.petgree.dao.ClienteDao;
import br.edu.utfpr.carloseduardofreitas.petgree.dao.UsuarioDao;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Agendamento;
import br.edu.utfpr.carloseduardofreitas.petgree.model.Cliente;
import br.edu.utfpr.carloseduardofreitas.petgree.utils.ComboBoxLists;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author kadu1
 */
public class DashboardCenterController implements Initializable {

    private ClienteDao clienteDao = new ClienteDao();
    private AnimalDao animalDao = new AnimalDao();
    private AgendamentoDao agendamentoDao = new AgendamentoDao();
    private UsuarioDao usuarioDao = new UsuarioDao();
    int ano = LocalDate.now().getYear();
    @FXML
    private Text textDia;
    @FXML
    private Text textHoras;
    @FXML
    private Text textUser;
    @FXML
    private BarChart<String, Double> barChartClientes;
    @FXML
    private BarChart<String, Double> barChartAnimais;
    @FXML
    private BarChart<String, Double> barChartAgendamentos;
    @FXML
    private ComboBox<Integer> comboBoxAno;
    @FXML
    private Text TextAgendamentos;
    @FXML
    private Text TextClientes;
    @FXML
    private Text TextAnimais;
    @FXML
    private Text TextFuncionarios;
    @FXML
    private Text TextAgendamentosHoje;
    @FXML
    private Text TextPagamentosPendentes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizarDados();
        carregarQtds();
        comboBoxAno.setItems(ComboBoxLists.gerarAnos());
        comboBoxAno.getSelectionModel().selectFirst();
    }
    private void carregarQtds(){
        TextAgendamentos.setText(String.valueOf(agendamentoDao.consultarTodosNRegistros()));
        TextClientes.setText(String.valueOf(clienteDao.consultarTodosNRegistros()));
        TextAnimais.setText(String.valueOf(animalDao.consultarTodosNRegistros()));
        TextFuncionarios.setText(String.valueOf(usuarioDao.consultarTodosNRegistros()));
        List<Agendamento> list = agendamentoDao.consultarData(LocalDate.now());
        int qtd = list.toArray().length;
        TextAgendamentosHoje.setText(String.valueOf(qtd));
        TextPagamentosPendentes.setText(String.valueOf(agendamentoDao.consultarNPagamentosPendentes()));
    }
    @FXML
    private void atualizarAno(){
        ano = comboBoxAno.getValue();
        atualizarDados();
    }
    private void atualizarDados(){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH:mm");
        textDia.setText(LocalDate.now().format(formatterDate).toString());
        textHoras.setText(LocalTime.now().format(formatterHour).toString());
        textUser.setText(LoginController.usuario.getNome());
        carregarDadosChartClientes();
        carregarDadosChartAnimais();
        carregarDadosChartAgendamentos();
    }
    private void carregarDadosChartClientes(){
        barChartClientes.getData().clear();
        barChartClientes.layout();
        XYChart.Series<String,Double> series_01 = new XYChart.Series();
        series_01.setName("Ano "+ano);
        series_01.getData().add(new XYChart.Data("Jan",clienteDao.consultarNRegistros(1,ano)));
        series_01.getData().add(new XYChart.Data("Fev",clienteDao.consultarNRegistros(2,ano)));
        series_01.getData().add(new XYChart.Data("Mar",clienteDao.consultarNRegistros(3,ano)));
        series_01.getData().add(new XYChart.Data("Abr",clienteDao.consultarNRegistros(4,ano)));
        series_01.getData().add(new XYChart.Data("Mai",clienteDao.consultarNRegistros(5,ano)));
        series_01.getData().add(new XYChart.Data("Jun",clienteDao.consultarNRegistros(6,ano)));
        series_01.getData().add(new XYChart.Data("Jul",clienteDao.consultarNRegistros(7,ano)));
        series_01.getData().add(new XYChart.Data("Ago",clienteDao.consultarNRegistros(8,ano)));
        series_01.getData().add(new XYChart.Data("Set",clienteDao.consultarNRegistros(9,ano)));
        series_01.getData().add(new XYChart.Data("Out",clienteDao.consultarNRegistros(10,ano)));
        series_01.getData().add(new XYChart.Data("Nov",clienteDao.consultarNRegistros(11,ano)));
        series_01.getData().add(new XYChart.Data("Dez",clienteDao.consultarNRegistros(12,ano)));
        barChartClientes.getData().add(series_01);
    }
    private void carregarDadosChartAnimais(){
        barChartAnimais.getData().clear();
        barChartAnimais.layout();
        XYChart.Series<String,Double> series_01 = new XYChart.Series();
        series_01.setName("Ano "+ano);
        series_01.getData().add(new XYChart.Data("Jan",animalDao.consultarNRegistros(1,ano)));
        series_01.getData().add(new XYChart.Data("Fev",animalDao.consultarNRegistros(2,ano)));
        series_01.getData().add(new XYChart.Data("Mar",animalDao.consultarNRegistros(3,ano)));
        series_01.getData().add(new XYChart.Data("Abr",animalDao.consultarNRegistros(4,ano)));
        series_01.getData().add(new XYChart.Data("Mai",animalDao.consultarNRegistros(5,ano)));
        series_01.getData().add(new XYChart.Data("Jun",animalDao.consultarNRegistros(6,ano)));
        series_01.getData().add(new XYChart.Data("Jul",animalDao.consultarNRegistros(7,ano)));
        series_01.getData().add(new XYChart.Data("Ago",animalDao.consultarNRegistros(8,ano)));
        series_01.getData().add(new XYChart.Data("Set",animalDao.consultarNRegistros(9,ano)));
        series_01.getData().add(new XYChart.Data("Out",animalDao.consultarNRegistros(10,ano)));
        series_01.getData().add(new XYChart.Data("Nov",animalDao.consultarNRegistros(11,ano)));
        series_01.getData().add(new XYChart.Data("Dez",animalDao.consultarNRegistros(12,ano)));
        barChartAnimais.getData().add(series_01);
    }
    private void carregarDadosChartAgendamentos(){
        barChartAgendamentos.getData().clear();
        barChartAgendamentos.layout();
        XYChart.Series<String,Double> series_01 = new XYChart.Series();
        series_01.setName("Ano "+ano);
        series_01.getData().add(new XYChart.Data("Jan",agendamentoDao.consultarNRegistros(1,ano)));
        series_01.getData().add(new XYChart.Data("Fev",agendamentoDao.consultarNRegistros(2,ano)));
        series_01.getData().add(new XYChart.Data("Mar",agendamentoDao.consultarNRegistros(3,ano)));
        series_01.getData().add(new XYChart.Data("Abr",agendamentoDao.consultarNRegistros(4,ano)));
        series_01.getData().add(new XYChart.Data("Mai",agendamentoDao.consultarNRegistros(5,ano)));
        series_01.getData().add(new XYChart.Data("Jun",agendamentoDao.consultarNRegistros(6,ano)));
        series_01.getData().add(new XYChart.Data("Jul",agendamentoDao.consultarNRegistros(7,ano)));
        series_01.getData().add(new XYChart.Data("Ago",agendamentoDao.consultarNRegistros(8,ano)));
        series_01.getData().add(new XYChart.Data("Set",agendamentoDao.consultarNRegistros(9,ano)));
        series_01.getData().add(new XYChart.Data("Out",agendamentoDao.consultarNRegistros(10,ano)));
        series_01.getData().add(new XYChart.Data("Nov",agendamentoDao.consultarNRegistros(11,ano)));
        series_01.getData().add(new XYChart.Data("Dez",agendamentoDao.consultarNRegistros(12,ano)));
        barChartAgendamentos.getData().add(series_01);
    }
    
}
