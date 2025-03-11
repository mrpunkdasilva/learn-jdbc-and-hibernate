# Atividade 2 - Parte 1: Conexão com Banco de Dados usando JDBC

## Passo 1: Configuração do Projeto Java
- Adicione a dependência do driver JDBC do PostgreSQL no seu projeto. Se estiver usando Maven, adicione o seguinte ao seu `pom.xml`:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
</dependency>
```

## Passo 2: Estabelecer uma Conexão
Aqui está um exemplo de como estabelecer uma conexão com o banco de dados PostgreSQL:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/nome_do_banco";
    private static final String USER = "usuario";
    private static final String PASSWORD = "senha";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

## Passo 3: Executar Operações CRUD
Aqui está um exemplo de como executar operações CRUD usando JDBC:

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivroDAO {

    public void inserirLivro(String isbn, String titulo, String autor, String anoPublicacao) {
        String sql = "INSERT INTO LIVROS (ISBN, TITULO, AUTOR, ANO_PUBLICACAO) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            pstmt.setString(2, titulo);
            pstmt.setString(3, autor);
            pstmt.setDate(4, java.sql.Date.valueOf(anoPublicacao));
            pstmt.executeUpdate();

            System.out.println("Livro inserido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarLivros() {
        String sql = "SELECT * FROM LIVROS";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") +
                        ", Título: " + rs.getString("TITULO") +
                        ", Autor: " + rs.getString("AUTOR"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

## Passo 4: Chamar Procedimentos Armazenados
Aqui está um exemplo de como chamar o procedimento armazenado `realizar_emprestimo`:

```java
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class EmprestimoService {

    public void realizarEmprestimo(int idLivro, int idMembro, String dataDevolucaoPrevista) {
        String sql = "{call realizar_emprestimo(?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setInt(1, idLivro);
            cstmt.setInt(2, idMembro);
            cstmt.setDate(3, java.sql.Date.valueOf(dataDevolucaoPrevista));
            cstmt.execute();

            System.out.println("Empréstimo realizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

## Passo 5: Usando asclasses

```java
public class Main {
    public static void main(String[] args) throws SQLException {
        LivroDAO livroDAO = new LivroDAO();

        // Inserir um livro
        livroDAO.inserirLivro("9788535914849", "1984", "George Orwell", "1949-06-08");

        // Listar livros
        livroDAO.listarLivros();

        // Atualizar um livro
        livroDAO.atualizarLivro(1, "1984 - Edição Especial");

        // Excluir um livro
        livroDAO.excluirLivro(1);

        // Realizar empréstimo
        EmprestimoService emprestimoService = new EmprestimoService();
        emprestimoService.realizarEmprestimo(2, 1, "2023-10-20");
    }
}
```

---

## Executando

![Exibindo tabela MEMBROS](1741729095311.png)

![Exibindo tabela LIVROS](1741728915235.png)

![Exibindo tabela HISTORICO EMPRESTIMOS](1741729540021.png)

![Execução bem sucedida com JDBC](1741729155800.png)
