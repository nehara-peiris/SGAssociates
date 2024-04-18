package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Deed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DeedRepo {
    public static List<Deed> getAll() {
        return null;
    }

    public static boolean save(Deed deed) throws SQLException {
        String sql = "INSERT INTO deed VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, deed.getDeedId());
        pstm.setObject(2, deed.getDescription());
        pstm.setObject(3, deed.getDate());
        pstm.setObject(4, deed.getLawyerId());
        pstm.setObject(5, deed.getClientId());

        return pstm.executeUpdate() > 0;

    }
}
