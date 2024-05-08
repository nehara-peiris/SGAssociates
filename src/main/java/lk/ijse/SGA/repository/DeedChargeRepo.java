package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.DeedCharge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeedChargeRepo {
    public static List<DeedCharge> getAll() throws SQLException {
        String sql = "SELECT * FROM deedCharge";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<DeedCharge> deedChargeList = new ArrayList<>();

        while (resultSet.next()) {
            String DCPayId = resultSet.getString(1);
            String caseId = resultSet.getString(2);
            String lawyerId = resultSet.getString(3);
            String chargeId = resultSet.getString(4);
            Date date = Date.valueOf(resultSet.getString(5));
            double amount = Double.parseDouble(resultSet.getString(6));
            String clientId = resultSet.getString(7);

            DeedCharge deedCharge = new DeedCharge(DCPayId, caseId, lawyerId, chargeId, date, amount, clientId);
            deedChargeList.add(deedCharge);
        }
        return deedChargeList;

    }

    public static boolean save(DeedCharge deedCharge) throws SQLException {
        String sql = "INSERT INTO deedCharge VALUES(?,?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, deedCharge.getDCPayId());
        pstm.setObject(2, deedCharge.getDeedId());
        pstm.setObject(3, deedCharge.getLawyerId());
        pstm.setObject(4, deedCharge.getChargeId());
        pstm.setObject(5, deedCharge.getDate());
        pstm.setObject(6, deedCharge.getAmount());
        pstm.setObject(7, deedCharge.getClientId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(DeedCharge deedCharge) throws SQLException {
        String sql = "UPDATE deedCharge SET deedId = ?, lawyerId = ?, chargeId = ?, date = ?, amount = ?, clientId = ? WHERE DCPayId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, deedCharge.getDeedId());
        pstm.setObject(2, deedCharge.getLawyerId());
        pstm.setObject(3, deedCharge.getChargeId());
        pstm.setObject(4, deedCharge.getDate());
        pstm.setObject(5, deedCharge.getAmount());
        pstm.setObject(6, deedCharge.getClientId());
        pstm.setObject(7, deedCharge.getDCPayId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM deedCharge WHERE DCPayId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
