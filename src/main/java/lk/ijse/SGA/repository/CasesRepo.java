package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Cases;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CasesRepo {
    public static List<Cases> getAll() throws SQLException {
        String sql = "SELECT * FROM cases";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Cases> caseList = new ArrayList<>();

        while (resultSet.next()){
            String caseId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String type = resultSet.getString(3);
            Date date = Date.valueOf(resultSet.getString(4));
            String clientId = resultSet.getString(5);

            Cases cases = new Cases(caseId, description, type, date, clientId);
            caseList.add(cases);
        }
        return caseList;
    }

    public static boolean save(Cases cases) throws SQLException {
        String sql = "INSERT INTO cases VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, cases.getCaseId());
        pstm.setObject(2, cases.getDescription());
        pstm.setObject(3, cases.getType());
        pstm.setObject(4, cases.getDate());
        pstm.setObject(5, cases.getClientId());

        return pstm.executeUpdate() > 0;
    }
    public static boolean update(Cases cases) throws SQLException {
        String sql = "UPDATE cases SET description = ?, type = ?, date = ?, clientId = ? WHERE caseId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, cases.getDescription());
        pstm.setObject(2, cases.getType());
        pstm.setObject(3, cases.getDate());
        pstm.setObject(4, cases.getClientId());
        pstm.setObject(5, cases.getCaseId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM cases WHERE caseId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static Map<String, Integer> getAllToChart() throws SQLException {
        String sql = "SELECT type, COUNT(*) AS count FROM cases WHERE type IS NOT NULL GROUP BY type";

        Map<String, Integer> caseTypeCounts = new HashMap<>();
        Connection connection = DbConnection.getInstance().getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery(sql)) {
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                int count = resultSet.getInt("count");
                caseTypeCounts.put(type, count);
            }
        }
        return caseTypeCounts;
    }
}
