/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.carloseduardofreitas.petgree.dao;

import br.edu.utfpr.carloseduardofreitas.petgree.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author kadu1
 */
public class UsuarioDao {
    public void salvar(Usuario usuario) {
        try {
            Session session = ConexaoBanco.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(usuario);
            session.getTransaction().commit();
            session.close();
            System.out.println("Registro gravado/alterado com sucesso");
        } catch (Exception erro) {
            System.out.println("Ocorreu um erro:" + erro);
        }
    }
    
    public List<Usuario> consultar(String nome) {
        List<Usuario> lista = new ArrayList<>();
        Session session = ConexaoBanco.getSessionFactory().openSession();
        session.beginTransaction();
        if (nome.length() == 0) {
            lista = session.createQuery("from Usuario").getResultList();
        } else {
            lista = session.createQuery("from Usuario where usu_nome like" + "'" + nome + "%'").getResultList();
        }
        session.getTransaction().commit();
        session.close();
        return lista;
    }

    public Usuario consultarLogin (String email, String senha) {
        Usuario user = new Usuario();
        Session session = ConexaoBanco.getSessionFactory().openSession();
        session.beginTransaction();
        user = ((Usuario) session.createQuery("from Usuario where usu_email = " + "'" + email + "'" + "and senha =" + "'" + senha + "'").uniqueResult());

        return user;
    }
    
    public void excluir(Usuario usuario){
        try (Session session = ConexaoBanco.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(usuario);
            session.getTransaction().commit();
            session.close();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    public long consultarTodosNRegistros() {
        long qtd = 0;
        Session session = ConexaoBanco.getSessionFactory().openSession();
        session.beginTransaction();

        qtd = (long) session.createQuery("select count(id) from Usuario").uniqueResult();


        session.getTransaction().commit();
        session.close();
        return qtd;
    }
}
