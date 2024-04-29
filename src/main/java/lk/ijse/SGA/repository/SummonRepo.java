package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Summon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SummonRepo {
    public static List<Summon> getAll() throws SQLException {

        String sql = "SELECT * FROM summon";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Summon> summonList = new ArrayList<>();

        while (resultSet.next()) {
            String summonId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String defendant = resultSet.getString(3);
            String lawyerId = resultSet.getString(4);
            Date date = Date.valueOf(resultSet.getString(5));

            Summon summon = new Summon(summonId, description, defendant, lawyerId, date);
            summonList.add(summon);
        }
        return summonList;
    }

    public static boolean save(Summon summon) throws SQLException {
        String sql = "INSERT INTO summon VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, summon.getSummonId());
        pstm.setObject(2, summon.getDescription());
        pstm.setObject(3, summon.getDefendant());
        pstm.setObject(4, summon.getLawyerId());
        pstm.setObject(5, summon.getDate());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Summon summon) throws SQLException {
        String sql = "UPDATE summon SET description = ?, defendant = ?, lawyerId = ?, date = ? WHERE summonId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, summon.getDescription());
        pstm.setObject(2, summon.getDefendant());
        pstm.setObject(3, summon.getLawyerId());
        pstm.setObject(4, summon.getDate());
        pstm.setObject(5, summon.getSummonId());

        return pstm.executeUpdate() > 0;
    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM summon WHERE summonId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static Summon searchById(String id) throws SQLException {
        String sql = "SELECT * FROM summon WHERE summonId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String summonId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String defendant = resultSet.getString(4);
            String lawyerId = resultSet.getString(5);
            Date date = Date.valueOf(resultSet.getString(6));

            Summon summon = new Summon(summonId, description, defendant, lawyerId, date);

            return summon;
        }
        return null;
    }
}
