# Atividade 2 - Parte 2: Uso de ORMs

## Passo 1: Configuração do Hibernate

### Adicionar Dependências no `pom.xml`
Adicione as dependências do Hibernate e do driver JDBC do PostgreSQL no arquivo `pom.xml` do seu projeto Maven:

```xml
<dependencies>
    <!-- Hibernate Core -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.3.1.Final</version>
    </dependency>

    <!-- Driver JDBC do PostgreSQL -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.6.0</version>
    </dependency>

    <!-- Java Persistence API (JPA) -->
    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
        <version>3.1.0</version>
    </dependency>
</dependencies>
```

---

## Passo 2: Configuração do `hibernate.cfg.xml`

Crie o arquivo `hibernate.cfg.xml` na pasta `src/main/resources`. Esse arquivo contém as configurações de conexão com o banco de dados e o mapeamento das entidades.

```xml
<hibernate-configuration>
    <session-factory>
        <!-- Configurações de conexão com o banco de dados -->
        <property name="hibernate.connection.driver_class">org.postgresql.Driver</property>
        <property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/biblioteca</property>
        <property name="hibernate.connection.username">usuario</property>
        <property name="hibernate.connection.password">senha</property>

        <!-- Dialeto do PostgreSQL -->
        <property name="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</property>

        <!-- Atualiza o schema do banco de dados automaticamente -->
        <property name="hibernate.hbm2ddl.auto">update</property>

        <!-- Exibe as consultas SQL no console -->
        <property name="show_sql">true</property>

        <!-- Mapeamento das entidades -->
        <mapping class="com.exemplo.model.Livro"/>
        <mapping class="com.exemplo.model.Membro"/>
    </session-factory>
</hibernate-configuration>
```

---

## Passo 3: Mapeamento de Entidades

Crie as classes de entidade que representam as tabelas do banco de dados. Essas classes devem ser anotadas com as anotações do JPA (Java Persistence API).

### Classe `Livro`
```java
package com.exemplo.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LIVROS")
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
```

### Classe `Membro`
```java
package com.exemplo.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MEMBROS")
public class Membro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    @Column(name = "DATA_CADASTRO")
    private Date dataCadastro;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
```

---

## Passo 4: Operações com Hibernate

Crie uma classe de serviço para realizar operações CRUD usando o Hibernate.

### Classe `LivroService`
```java
package com.exemplo.service;

import com.exemplo.model.Livro;
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
```

---

## Passo 5: Testando as Operações

Crie uma classe `Main` para testar as operações com Hibernate.

### Classe `Main`
```java
package com.exemplo;

import com.exemplo.model.Livro;
import com.exemplo.service.LivroService;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        LivroService livroService = new LivroService();

        // Criar um novo livro
        Livro livro = new Livro();
        livro.setIsbn("9788535914849");
        livro.setTitulo("1984");
        livro.setAutor("George Orwell");
        livro.setAnoPublicacao(new Date());
        livro.setLivroEmEstoque(true);

        // Inserir o livro no banco de dados
        livroService.inserirLivro(livro);

        // Buscar o livro por ID
        Livro livroEncontrado = livroService.buscarLivroPorId(1);
        if (livroEncontrado != null) {
            System.out.println("Livro encontrado: " + livroEncontrado.getTitulo());
        } else {
            System.out.println("Livro não encontrado.");
        }
    }
}
```

## Executando

![Exeucação do codigo com sucesso, o livro "1984" foi criado e achado dentro do banco](1741733428870.png)

![Conferindo Tabela LIVROS](Conferindo Tabela LIVROS)
