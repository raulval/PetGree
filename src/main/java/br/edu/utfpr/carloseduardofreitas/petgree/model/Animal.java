/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author kadu1
 */
@Entity
@Table(name = "animal")
public class Animal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "ani_nome", length = 100, nullable = false)
    private String nome;
    
    @Column(name = "ani_data_nascimento", nullable = true)
    private LocalDate dataNascimento;

    @Column(name = "ani_porte", length = 45, nullable = true)
    private String porte;

    @OneToOne
    @JoinColumn(name = "cli_id",nullable = false)
    private Cliente cliente;

    @Column(name = "ani_data_cadastro", nullable = true)
    private LocalDate dataCadastro;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        return  nome + ", Proprietário: "+getCliente().getNome();
    }
    
    
    
}
