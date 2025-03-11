# Laboratory 2 

### Requisitos do Sistema:

1. **Cadastro de Livros:**
   - Precisamos de uma funcionalidade para cadastrar novos livros, incluindo detalhes como título, autor, ano de publicação e se o livro está disponível para empréstimo.

2. **Cadastro de Membros:**
   - Queremos poder registrar os membros da biblioteca, incluindo nome e data de cadastro.

3. **Empréstimos:**
   - O sistema deve permitir que um membro possa emprestar um livro. Quando um livro é emprestado, sua disponibilidade deve ser atualizada automaticamente.
   - Precisamos de uma verificação para garantir que o livro escolhido está disponível no momento do empréstimo.

4. **Devoluções de Livros:**
   - Os membros devem poder devolver os livros, atualizando o status de disponibilidade do livro.

5. **Visualização de Livros Disponíveis:**
   - Uma funcionalidade que permita visualizar apenas os livros que estão disponíveis para empréstimo.

6. **Histórico de Empréstimos:**
   - O sistema deve manter um histórico de empréstimos e devoluções, incluindo informações sobre quais livros foram emprestados e por quem.

### Funcionalidades Técnicas:

- **Views:**
  - Criar uma view para facilitar a visualização dos livros disponíveis para empréstimo.

- **Procedimentos Armazenados:**
  - Desenvolver procedimentos armazenados para gerenciar o processo de empréstimo e devolução dos livros.
  
- **Triggers:**
  - Implementar triggers para registrar automaticamente as ações de empréstimos e devoluções no histórico.

    