package org.gustavojesus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LivroDAO {

    // Método para inserir um livro
    public void inserirLivro(String isbn, String titulo, String autor, String anoPublicacao) throws SQLException {
        String sql = "INSERT INTO LIVROS (ISBN, TITULO, AUTOR, ANO_PUBLICACAO) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            pstmt.setString(2, titulo);
            pstmt.setString(3, autor);
            pstmt.setDate(4, java.sql.Date.valueOf(anoPublicacao));
            pstmt.executeUpdate();
        }
    }

    // Método para listar todos os livros
    public void listarLivros() throws SQLException {
        String sql = "SELECT * FROM LIVROS";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") +
                        ", Título: " + rs.getString("TITULO") +
                        ", Autor: " + rs.getString("AUTOR") +
                        ", Ano de Publicação: " + rs.getDate("ANO_PUBLICACAO"));
            }
        }
    }

    // Método para atualizar o título de um livro
    public void atualizarLivro(int id, String novoTitulo) throws SQLException {
        String sql = "UPDATE LIVROS SET TITULO = ? WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, novoTitulo);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    // Método para excluir um livro
    public void excluirLivro(int id) throws SQLException {
        String sql = "DELETE FROM LIVROS WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Método para buscar um livro por ID
    public void buscarLivroPorId(int id) throws SQLException {
        String sql = "SELECT * FROM LIVROS WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") +
                        ", Título: " + rs.getString("TITULO") +
                        ", Autor: " + rs.getString("AUTOR") +
                        ", Ano de Publicação: " + rs.getDate("ANO_PUBLICACAO"));
            } else {
                System.out.println("Livro não encontrado.");
            }
        }
    }
}