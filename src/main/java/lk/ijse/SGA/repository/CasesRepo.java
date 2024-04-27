package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Cases;
import lk.ijse.SGA.model.Deed;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            String lawyerId = resultSet.getString(5);
            String clientId = resultSet.getString(6);

            Cases cases = new Cases(caseId, description, type, date, lawyerId, clientId);
            caseList.add(cases);
        }
        return caseList;
    }

    public static boolean save(Cases cases) throws SQLException {
        String sql = "INSERT INTO cases VALUES(?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, cases.getCaseId());
        pstm.setObject(2, cases.getDescription());
        pstm.setObject(3, cases.getType());
        pstm.setObject(4, cases.getDate());
        pstm.setObject(5, cases.getLawyerId());
        pstm.setObject(6, cases.getClientId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Cases cases) throws SQLException {
        String sql = "UPDATE cases SET description = ?, type = ?, date = ?, lawyerId = ?, clientId = ? WHERE caseId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, cases.getDescription());
        pstm.setObject(2, cases.getType());
        pstm.setObject(3, cases.getDate());
        pstm.setObject(4, cases.getLawyerId());
        pstm.setObject(5, cases.getClientId());
        pstm.setObject(6, cases.getCaseId());

        return pstm.executeUpdate() > 0;
    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM cases WHERE caseId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
