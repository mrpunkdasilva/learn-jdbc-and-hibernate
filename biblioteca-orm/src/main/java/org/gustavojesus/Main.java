package org.gustavojesus;

import org.gustavojesus.models.Livro;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        LivroService livroService = new LivroService();

        // Criar um novo livro
        Livro livro = new Livro();
        livro.setIsbn("1239969999999");
        livro.setTitulo("1984");
        livro.setAutor("George Orwell");
        livro.setAnoPublicacao(new Date());
        livro.setLivroEmEstoque(true);

        // Inserir o livro no banco de dados
        livroService.inserirLivro(livro);

        // Buscar o livro por ID
        Livro livroEncontrado = livroService.buscarLivroPorId(21);
        if (livroEncontrado != null) {
            System.out.println("Livro encontrado: " + livroEncontrado.getTitulo());
        } else {
            System.out.println("Livro não encontrado.");
        }
    }
}