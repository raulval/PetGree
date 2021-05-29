package br.edu.utfpr.carloseduardofreitas.petgree.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Agendamento")
public class Agendamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "age_data",nullable = false)
    private LocalDate data;
    @Column(name = "age_hora",nullable = false)
    private LocalTime hora;
    @Column(name = "age_pago", nullable = false)
    private boolean pago;
    @OneToOne
    @JoinColumn(name = "ani_id",nullable = false)
    private Animal animal;
    @OneToOne
    @JoinColumn(name = "usu_id",nullable = false)
    private Usuario usuario;
    @OneToOne
    @JoinColumn(name = "ser_id",nullable = false)
    private Servico servico;
    @Column(name = "age_data_cadastro", nullable = true)
    private LocalDate dataCadastro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
