package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Lawyer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LawyerRepo {
    public static List<Lawyer> getAll() {
        return null;
    }

    public static boolean save(Lawyer lawyer) throws SQLException {
        String sql = "INSERT INTO lawyer VALUES(?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, lawyer.getLawyerId());
        pstm.setObject(2, lawyer.getName());
        pstm.setObject(3, lawyer.getYrsOfExp());
        pstm.setObject(4, lawyer.getAddress());
        pstm.setObject(5, lawyer.getEmail());
        pstm.setObject(6, lawyer.getContact());

        return pstm.executeUpdate() > 0;
    }
}
