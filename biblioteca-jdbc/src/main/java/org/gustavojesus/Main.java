package org.gustavojesus;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        LivroDAO livroDAO = new LivroDAO();

        // Inserir um livro
        livroDAO.inserirLivro("123456789155", "1984", "George Orwell", "1949-06-08");

        // Listar livros
        livroDAO.listarLivros();

        // Atualizar um livro
        livroDAO.atualizarLivro(5, "1984 - Edição Especial");

        // Excluir um livro
        livroDAO.excluirLivro(7);

        // Realizar empréstimo
        EmprestimoService emprestimoService = new EmprestimoService();
        emprestimoService.realizarEmprestimo(8, 1, "2023-10-20");
    }
}