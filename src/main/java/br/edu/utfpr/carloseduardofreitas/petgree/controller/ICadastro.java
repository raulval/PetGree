/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.controller;

/**
 *
 * @author kadu1
 */
public interface ICadastro {
    public abstract void criarColunasTabela();
    public abstract void atualizarTabela();
    public abstract void setCamposFormulario();
    public abstract void limparCamposFormulario();
}
