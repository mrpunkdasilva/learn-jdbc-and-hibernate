# Pre-requisito

> Link para o repositorio com os códigos: [http://github.com/mrpunkdasilva/learn-jdbc-and-hibernate](http://github.com/mrpunkdasilva/learn-jdbc-and-hibernate)

Para o laboratório, foi escolhido um sistema de biblioteca para a continuidade do projeto.

## Iniciar container

![Imagem ilustrando o início do container](Iniciando Container)


## Modelo Entidade-Relacionamento

```mermaid
erDiagram
    livros {
        integer id PK
        varchar(255) titulo
        varchar(255) autor
        integer ano_publicacao
        boolean disponivel
    }

    membros {
        integer id PK
        varchar(255) nome
        date data_cadastro
    }

    emprestimos {
        integer id PK
        integer livro_id FK
        integer membro_id FK
        date data_emprestimo
        date data_devolucao_prevista
        boolean devolvido
    }

    historico_emprestimos {
        integer id PK
        integer emprestimo_id FK
        date data_emprestimo
        date data_devolucao
        integer livro_id FK
        integer membro_id FK
    }

    livros ||--o{ emprestimos : "1 livro pode ter muitos empréstimos"
    membros ||--o{ emprestimos : "1 membro pode ter muitos empréstimos"
    emprestimos ||--o{ historico_emprestimos : "1 empréstimo pode ter 1 registro no histórico"
    livros ||--o{ historico_emprestimos : "1 livro pode ter muitos registros no histórico"
    membros ||--o{ historico_emprestimos : "1 membro pode ter muitos registros no histórico"
```



## Criação do banco de dados

```sql
CREATE DATABASE BIBLIOTECA;
```

![Imagem mostrando a criação do banco de dados da biblioteca](Banco criado da blioteca)



## Criando as tabelas

- **Livros:**

```sql
CREATE TABLE LIVROS (
    ID SERIAL PRIMARY KEY,
    ISBN CHAR(13) UNIQUE NOT NULL,
    TITULO CHARACTER VARYING(100) NOT NULL,
    AUTOR CHARACTER VARYING(100) NOT NULL,
    ANO_PUBLICACAO DATE NOT NULL,
    LIVRO_EM_ESTOQUE BOOLEAN NOT NULL DEFAULT TRUE
);
```

![Imagem da tabela de livros criada](image.png)

- **Membros:**

```sql
CREATE TABLE MEMBROS (
    ID SERIAL PRIMARY KEY,
    NOME CHARACTER VARYING(120) NOT NULL,
    DATA_CADASTRO DATE NOT NULL
);
```

![Imagem da tabela de membros criada](Tabela de Membros criado)



- **Empréstimos:**

```sql
CREATE TABLE EMPRESTIMOS (
    ID SERIAL PRIMARY KEY,
    ID_LIVRO INTEGER,
    CONSTRAINT FK_LIVRO FOREIGN KEY (ID_LIVRO) REFERENCES LIVROS (ID),
    ID_MEMBRO INTEGER,
    CONSTRAINT FK_MEMBRO FOREIGN KEY (ID_MEMBRO) REFERENCES MEMBROS (ID),
    DATA_EMPRESTIMO DATE DEFAULT CURRENT_DATE,
    DATA_DEVOLUCAO_PREVISTA DATE,
    DEVOLVIDO BOOLEAN DEFAULT FALSE
);
```

![Imagem da tabela de empréstimos criada](1741705281493.png)


- **Histórico de Empréstimos:**
 
```sql
CREATE TABLE HISTORICO_EMPRESTIMOS (
    ID SERIAL PRIMARY KEY,
    DATA_DEVOLUCAO DATE,
    DATA_EMPRESTIMO DATE,

    ID_EMPRESTIMO INTEGER,
    CONSTRAINT FK_EMPRESTIMO FOREIGN KEY (ID_EMPRESTIMO) REFERENCES LIVROS (ID),
    ID_LIVRO INTEGER,
    CONSTRAINT FK_EMPRESTIMO_LIVRO FOREIGN KEY (ID_LIVRO) REFERENCES LIVROS (ID),
    ID_MEMBRO INTEGER,
    CONSTRAINT FK_EMPRESTIMO_MEMBRO FOREIGN KEY (ID_MEMBRO) REFERENCES MEMBROS (ID)
);
```

![Imagem da tabela de histórico de empréstimos criada](1741705528002.png)




## Inserindo alguns dados iniciais na tabela

- **Livros:**

```sql
INSERT INTO LIVROS (ISBN, TITULO, AUTOR, ANO_PUBLICACAO, LIVRO_EM_ESTOQUE)
VALUES
('9788535914849', '1984', 'George Orwell', '1949-06-08', TRUE),
('9788535914863', 'A Revolução dos Bichos', 'George Orwell', '1945-08-17', TRUE),
('9788535914870', 'O Senhor dos Anéis', 'J.R.R. Tolkien', '1954-07-29', TRUE),
('9788535914887', 'O Hobbit', 'J.R.R. Tolkien', '1937-09-21', FALSE),
('9788535914894', 'Cem Anos de Solidão', 'Gabriel García Márquez', '1967-05-30', TRUE);
```

![Imagem mostrando dados de livros inseridos](Dados inseridos)



- **Membros:**

```sql
INSERT INTO MEMBROS (NOME, DATA_CADASTRO) 
VALUES 
('João Silva', '2023-01-15'),
('Maria Oliveira', '2023-02-20'),
('Carlos Souza', '2023-03-10'),
('Ana Costa', '2023-04-05'),
('Pedro Rocha', '2023-05-12');
```

![Imagem mostrando dados de membros inseridos](Inserindo dados)




- **Empréstimos:**

```sql
INSERT INTO EMPRESTIMOS (ID_LIVRO, ID_MEMBRO, DATA_EMPRESTIMO, DATA_DEVOLUCAO_PREVISTA, DEVOLVIDO) 
VALUES 
(1, 1, '2023-10-01', '2023-10-15', TRUE),
(2, 2, '2023-10-02', '2023-10-16', FALSE),
(3, 3, '2023-10-03', '2023-10-17', TRUE),
(4, 4, '2023-10-04', '2023-10-18', FALSE),
(5, 5, '2023-10-05', '2023-10-19', TRUE);
```

![Imagem mostrando dados de empréstimos inseridos](1741707150573_2.png)



- **Histórico de Empréstimos:**
 
```sql
INSERT INTO HISTORICO_EMPRESTIMOS (DATA_DEVOLUCAO, DATA_EMPRESTIMO, ID_EMPRESTIMO, ID_LIVRO, ID_MEMBRO) 
VALUES 
('2023-10-15', '2023-10-01', 1, 1, 1),
('2023-10-16', '2023-10-02', 2, 2, 2),
('2023-10-17', '2023-10-03', 3, 3, 3),
('2023-10-18', '2023-10-04', 4, 4, 4),
('2023-10-19', '2023-10-05', 5, 5, 5);
```

![Imagem mostrando dados de histórico de empréstimos inseridos](a)
