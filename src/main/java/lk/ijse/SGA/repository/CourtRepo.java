package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Court;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourtRepo {
    public static List<Court> getAll() throws SQLException {
        String sql = "SELECT * FROM court";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Court> courtList = new ArrayList<>();

        while (resultSet.next()) {
            String courtId = resultSet.getString(1);
            String location = resultSet.getString(2);

            Court court = new Court(courtId, location);
            courtList.add(court);
        }
        return courtList;
    }

    public static boolean save(Court court) throws SQLException {
        String sql = "INSERT INTO court VALUES(?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, court.getCourtId());
        pstm.setObject(2, court.getLocation());

        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Court court) throws SQLException {
        String sql = "UPDATE court SET location = ? WHERE courtId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, court.getLocation());
        pstm.setObject(2, court.getCourtId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM court WHERE courtId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
