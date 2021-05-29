/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.dao;


import br.edu.utfpr.carloseduardofreitas.petgree.model.Animal;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kadu1
 */
public class AnimalDao {
     public void salvar(Animal animal) {
        try {
            Session session = ConexaoBanco.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(animal);
            session.getTransaction().commit();
            session.close();
            System.out.println("Registro gravado/alterado com sucesso");
        } catch (Exception erro) {
            System.out.println("Ocorreu um erro:" + erro);
        }
    }
    
    public List<Animal> consultar(String nome) {
        List<Animal> lista = new ArrayList<>();
        Session session = ConexaoBanco.getSessionFactory().openSession();
        session.beginTransaction();
        if (nome.length() == 0) {
            lista = session.createQuery("from Animal").getResultList();
        } else {
            lista = session.createQuery("from Animal where ani_nome like" + "'" + nome + "%'").getResultList();
        }
        session.getTransaction().commit();
        session.close();
        return lista;
    }
    
    public void excluir(Animal animal){
        try (Session session = ConexaoBanco.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(animal);
            session.getTransaction().commit();
            session.close();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    public long consultarNRegistros(int mes, int ano) {
        int mesfim = mes+1;
        long qtd = 0;
        Session session = ConexaoBanco.getSessionFactory().openSession();
        session.beginTransaction();
        if(mes == 12){
            qtd = (long) session.createQuery("select count(id) from Animal WHERE ani_data_cadastro BETWEEN '" + ano + "-" + mes + "-01' and '" + ano + "-" + mes + "31' ").uniqueResult();
        }else{
            qtd = (long) session.createQuery("select count(id) from Animal WHERE ani_data_cadastro BETWEEN '" + ano + "-" + mes + "-01' and '" + ano + "-" + mesfim + "-01' ").uniqueResult();
        }

        session.getTransaction().commit();
        session.close();
        return qtd;
    }
    public long consultarTodosNRegistros() {
        long qtd = 0;
        Session session = ConexaoBanco.getSessionFactory().openSession();
        session.beginTransaction();

        qtd = (long) session.createQuery("select count(id) from Animal").uniqueResult();


        session.getTransaction().commit();
        session.close();
        return qtd;
    }
}
