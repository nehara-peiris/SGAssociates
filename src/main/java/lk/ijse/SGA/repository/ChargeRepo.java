package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Charge;
import lk.ijse.SGA.model.Deed;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChargeRepo {
    public static List<Charge> getAll() throws SQLException {
        String sql = "SELECT * FROM charge";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Charge> chargeList = new ArrayList<>();

        while (resultSet.next()){
            String chargeId = resultSet.getString(1);
            String description = resultSet.getString(2);
            double amount = Double.parseDouble(resultSet.getString(3));
            Date date = Date.valueOf(resultSet.getString(4));

            Charge charge = new Charge(chargeId, description, amount, date);
            chargeList.add(charge);
        }
        return chargeList;
    }

    public static boolean save(Charge charge) throws SQLException {
        String sql = "INSERT INTO charge VALUES(?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, charge.getChargeId());
        pstm.setObject(2, charge.getDescription());
        pstm.setObject(3, charge.getAmount());
        pstm.setObject(4, charge.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Charge charge) throws SQLException {
        String sql = "UPDATE charge SET description = ?, amount = ?, date = ? WHERE chargeId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, charge.getDescription());
        pstm.setObject(2, charge.getAmount());
        pstm.setObject(3, charge.getDate());
        pstm.setObject(4, charge.getChargeId());

        return pstm.executeUpdate() > 0;
    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM charge WHERE chargeId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
