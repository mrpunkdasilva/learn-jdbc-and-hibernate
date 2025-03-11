# Atividade 1

## Views

### 1. **View para Visualização de Livros Disponíveis**
Essa view retorna apenas os livros que estão disponíveis para empréstimo (`LIVRO_EM_ESTOQUE = TRUE`).

```sql
CREATE OR REPLACE VIEW view_livros_disponiveis AS
SELECT 
    ID,
    ISBN,
    TITULO,
    AUTOR,
    ANO_PUBLICACAO
FROM 
    LIVROS
WHERE 
    LIVRO_EM_ESTOQUE = TRUE;
```

![Resultado da execução da view de livros disponíveis](1741718377221.png)

---

### 2. **Procedimento Armazenado para Empréstimo de Livros**
Esse procedimento armazenado gerencia o empréstimo de um livro, verificando se o livro está disponível e atualizando o status do livro.

```sql
CREATE OR REPLACE PROCEDURE realizar_emprestimo(
    p_id_livro INTEGER,
    p_id_membro INTEGER,
    p_data_devolucao_prevista DATE
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Verifica se o livro está disponível
    IF NOT EXISTS (SELECT 1 FROM LIVROS WHERE ID = p_id_livro AND LIVRO_EM_ESTOQUE = TRUE) THEN
        RAISE EXCEPTION 'Livro não está disponível para empréstimo.';
    END IF;

    -- Insere o empréstimo na tabela EMPRESTIMOS
    INSERT INTO EMPRESTIMOS (ID_LIVRO, ID_MEMBRO, DATA_DEVOLUCAO_PREVISTA)
    VALUES (p_id_livro, p_id_membro, p_data_devolucao_prevista);

    -- Atualiza o status do livro para indisponível
    UPDATE LIVROS
    SET LIVRO_EM_ESTOQUE = FALSE
    WHERE ID = p_id_livro;

    RAISE NOTICE 'Empréstimo realizado com sucesso.';
END;
$$;
```

![Confirmação da criação do procedimento de empréstimo](1741718550638.png)

---

### 3. **Procedimento Armazenado para Devolução de Livros**
Esse procedimento armazenado gerencia a devolução de um livro, atualizando o status do livro e registrando a devolução no histórico.

```sql
CREATE OR REPLACE PROCEDURE realizar_devolucao(
    p_id_emprestimo INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Verifica se o empréstimo existe e se o livro ainda não foi devolvido
    IF NOT EXISTS (SELECT 1 FROM EMPRESTIMOS WHERE ID = p_id_emprestimo AND DEVOLVIDO = FALSE) THEN
        RAISE EXCEPTION 'Empréstimo não encontrado ou livro já devolvido.';
    END IF;

    -- Atualiza o status do livro para disponível
    UPDATE LIVROS
    SET LIVRO_EM_ESTOQUE = TRUE
    WHERE ID = (SELECT ID_LIVRO FROM EMPRESTIMOS WHERE ID = p_id_emprestimo);

    -- Marca o empréstimo como devolvido
    UPDATE EMPRESTIMOS
    SET DEVOLVIDO = TRUE
    WHERE ID = p_id_emprestimo;

    -- Insere o registro no histórico de empréstimos
    INSERT INTO HISTORICO_EMPRESTIMOS (DATA_DEVOLUCAO, DATA_EMPRESTIMO, ID_EMPRESTIMO, ID_LIVRO, ID_MEMBRO)
    SELECT 
        CURRENT_DATE, 
        DATA_EMPRESTIMO, 
        ID, 
        ID_LIVRO, 
        ID_MEMBRO
    FROM 
        EMPRESTIMOS
    WHERE 
        ID = p_id_emprestimo;

    RAISE NOTICE 'Devolução realizada com sucesso.';
END;
$$;
```

![Confirmação da criação do procedimento de devolução](1741719794874.png)

---

### 4. **Trigger para Registrar Empréstimos no Histórico**
Esse trigger é acionado automaticamente após a inserção de um novo empréstimo na tabela `EMPRESTIMOS`, registrando o empréstimo no histórico.

```sql
CREATE OR REPLACE FUNCTION registrar_historico_emprestimo()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    -- Insere o registro no histórico de empréstimos
    INSERT INTO HISTORICO_EMPRESTIMOS (DATA_EMPRESTIMO, ID_EMPRESTIMO, ID_LIVRO, ID_MEMBRO)
    VALUES (NEW.DATA_EMPRESTIMO, NEW.ID, NEW.ID_LIVRO, NEW.ID_MEMBRO);

    RETURN NEW;
END;
$$;

CREATE TRIGGER trigger_registrar_historico_emprestimo
AFTER INSERT ON EMPRESTIMOS
FOR EACH ROW
EXECUTE FUNCTION registrar_historico_emprestimo();
```

![Confirmação da criação do trigger para registro de empréstimos](1741720081505.png)

---

### 5. **View para Visualização do Histórico de Empréstimos**
Essa view retorna o histórico completo de empréstimos, incluindo informações sobre os livros e membros.

```sql
CREATE OR REPLACE VIEW view_historico_emprestimos AS
SELECT 
    HE.ID AS HISTORICO_ID,
    HE.DATA_EMPRESTIMO,
    HE.DATA_DEVOLUCAO,
    L.TITULO AS LIVRO,
    M.NOME AS MEMBRO
FROM 
    HISTORICO_EMPRESTIMOS HE
JOIN 
    LIVROS L ON HE.ID_LIVRO = L.ID
JOIN 
    MEMBROS M ON HE.ID_MEMBRO = M.ID;
```

![Resultado da execução da view de histórico de empréstimos](1741720286243.png)

