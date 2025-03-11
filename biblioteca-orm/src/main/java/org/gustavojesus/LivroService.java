package org.gustavojesus;

import org.gustavojesus.models.Livro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class LivroService {

    private static final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    // Método para inserir um livro
    public void inserirLivro(Livro livro) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(livro);
            session.getTransaction().commit();
            System.out.println("Livro inserido com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para buscar um livro por ID
    public Livro buscarLivroPorId(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Livro.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
