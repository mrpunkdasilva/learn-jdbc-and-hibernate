package org.gustavojesus;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class EmprestimoService {

    public void realizarEmprestimo(int idLivro, int idMembro, String dataDevolucaoPrevista) {
        String sql = "call realizar_emprestimo(?, ?, ?)";

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