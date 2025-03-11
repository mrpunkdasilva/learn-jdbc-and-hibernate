package org.gustavojesus.models;


import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String isbn;
    private String titulo;
    private String autor;

    @Column(name = "ANO_PUBLICACAO")
    private Date anoPublicacao;

    @Column(name = "LIVRO_EM_ESTOQUE")
    private boolean livroEmEstoque;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Date anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public boolean isLivroEmEstoque() {
        return livroEmEstoque;
    }

    public void setLivroEmEstoque(boolean livroEmEstoque) {
        this.livroEmEstoque = livroEmEstoque;
    }
}
