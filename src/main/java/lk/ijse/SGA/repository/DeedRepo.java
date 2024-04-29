package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Deed;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeedRepo {
    public static List<Deed> getAll() throws SQLException {
        String sql = "SELECT * FROM deed";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Deed> deedList = new ArrayList<>();

        while (resultSet.next()){
            String deedId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String type = resultSet.getString(3);
            Date date = Date.valueOf(resultSet.getString(4));
            String lawyerId = resultSet.getString(5);
            String clientId = resultSet.getString(6);

            Deed deed = new Deed(deedId, description, type, date, lawyerId, clientId);
            deedList.add(deed);
        }
        return deedList;
    }

    public static boolean save(Deed deed) throws SQLException {
        String sql = "INSERT INTO deed VALUES(?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, deed.getDeedId());
        pstm.setObject(2, deed.getDescription());
        pstm.setObject(3, deed.getType());
        pstm.setObject(4, deed.getDate());
        pstm.setObject(5, deed.getLawyerId());
        pstm.setObject(6, deed.getClientId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Deed deed) throws SQLException {
        String sql = "UPDATE deed SET description = ?, type = ?, date = ?, lawyerId = ?, clientId = ? WHERE deedId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, deed.getDescription());
        pstm.setObject(2, deed.getType());
        pstm.setObject(3, deed.getDate());
        pstm.setObject(4, deed.getLawyerId());
        pstm.setObject(5, deed.getClientId());
        pstm.setObject(6, deed.getDeedId());

        return pstm.executeUpdate() > 0;
    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM deed WHERE deedId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static Deed searchById(String id) throws SQLException {
        String sql = "SELECT * FROM deed WHERE deedId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String deedId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String type = resultSet.getString(3);
            Date date = Date.valueOf(resultSet.getString(4));
            String lawyerId = resultSet.getString(5);
            String clientId = resultSet.getString(6);

            Deed deed = new Deed(deedId, description, type, date, lawyerId, clientId);

            return deed;
        }

        return null;
    }
}
