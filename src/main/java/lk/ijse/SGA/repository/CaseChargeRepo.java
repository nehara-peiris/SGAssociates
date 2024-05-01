package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.CaseCharge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CaseChargeRepo {

    public static List<CaseCharge> getAll() throws SQLException {
        String sql = "SELECT * FROM caseCharge";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<CaseCharge> caseChargeList = new ArrayList<>();

        while (resultSet.next()) {
            String CCPayId = resultSet.getString(1);
            String caseId = resultSet.getString(2);
            String chargeId = resultSet.getString(3);
            Date date = Date.valueOf(resultSet.getString(4));
            double amount = Double.parseDouble(resultSet.getString(5));
            String clientId = resultSet.getString(6);

            CaseCharge caseCharge = new CaseCharge(CCPayId, caseId, chargeId, date, amount, clientId);
            caseChargeList.add(caseCharge);
        }
        return caseChargeList;
    }

    public static boolean save(CaseCharge caseCharge) throws SQLException {
        String sql = "INSERT INTO caseCharge VALUES(?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, caseCharge.getCCPayId());
        pstm.setObject(2, caseCharge.getCaseId());
        pstm.setObject(3, caseCharge.getChargeId());
        pstm.setObject(4, caseCharge.getDate());
        pstm.setObject(5, caseCharge.getAmount());
        pstm.setObject(6, caseCharge.getClientId());

        return pstm.executeUpdate() > 0;
    }


    public static boolean update(CaseCharge caseCharge) throws SQLException {
        String sql = "UPDATE caseCharge SET caseId = ?, chargeId = ?, date = ?, amount = ?, clientId = ? WHERE CCPayId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, caseCharge.getCaseId());
        pstm.setObject(2, caseCharge.getChargeId());
        pstm.setObject(3, caseCharge.getDate());
        pstm.setObject(4, caseCharge.getAmount());
        pstm.setObject(5, caseCharge.getClientId());
        pstm.setObject(6, caseCharge.getCCPayId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM client WHERE CCPayId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
