/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.dao;

import br.edu.utfpr.carloseduardofreitas.petgree.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author kadu1
 */
public class ConexaoBanco {

    private static SessionFactory conexao = null;
    private static Configuration configuracao;

    private static SessionFactory buildSessionFactory() {
        configuracao = new Configuration().configure();

        configuracao.setProperty("hibernate.connection.username", "root");
        configuracao.setProperty("hibernate.connection.password", "");

        configuracao.addPackage("br.edu.utfpr.carloseduardofreitas.petgree.model").addAnnotatedClass(Cliente.class);
        configuracao.addPackage("br.edu.utfpr.carloseduardofreitas.petgree.model").addAnnotatedClass(Endereco.class);
        configuracao.addPackage("br.edu.utfpr.carloseduardofreitas.petgree.model").addAnnotatedClass(Contato.class);
        configuracao.addPackage("br.edu.utfpr.carloseduardofreitas.petgree.model").addAnnotatedClass(Servico.class);
        configuracao.addPackage("br.edu.utfpr.carloseduardofreitas.petgree.model").addAnnotatedClass(Usuario.class);
        configuracao.addPackage("br.edu.utfpr.carloseduardofreitas.petgree.model").addAnnotatedClass(Animal.class);
        configuracao.addPackage("br.edu.utfpr.carloseduardofreitas.petgree.model").addAnnotatedClass(Agendamento.class);

        conexao = configuracao.buildSessionFactory();

        return conexao;
    }

    public static SessionFactory getSessionFactory() {
        if (conexao == null) {
            conexao = buildSessionFactory();
        }
        return conexao;
    }
}
