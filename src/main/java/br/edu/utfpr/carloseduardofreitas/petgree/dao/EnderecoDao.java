package br.edu.utfpr.carloseduardofreitas.petgree.dao;

import br.edu.utfpr.carloseduardofreitas.petgree.model.Endereco;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class EnderecoDao {
 public void salvar(Endereco data) {
        try {
            Session session = ConexaoBanco.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(data);
            session.getTransaction().commit();
            session.close();
            System.out.println("Registro gravado/alterado com sucesso");
        } catch (Exception erro) {
            System.out.println("Ocorreu um erro:" + erro);
        }
    }
}
