package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo {
    public static List<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM payment";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Payment> paymentList = new ArrayList<>();
        while (resultSet.next()) {
            String paymentId = resultSet.getString(1);
            String lawyerID = resultSet.getString(2);
            Date date = Date.valueOf(resultSet.getString(3));
            double amount = Double.parseDouble(resultSet.getString(4));
            double bonus = Double.parseDouble(resultSet.getString(5));


            Payment payment = new Payment(paymentId, lawyerID, date, amount, bonus);
            paymentList.add(payment);
        }

        return paymentList;
    }

    public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, payment.getPaymentId());
        pstm.setObject(2, payment.getLawyerId());
        pstm.setObject(3, payment.getDate());
        pstm.setObject(4, payment.getAmount());
        pstm.setObject(5, payment.getBonus());

        return pstm.executeUpdate() > 0;
    }

    public static String getCurrentId() {
        return null;
    }

    public static boolean update(Payment payment) throws SQLException {
        String sql = "UPDATE payment SET lawyerId = ?, date = ?, amount = ?, bonus = ? WHERE paymentId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, payment.getLawyerId());
        pstm.setObject(2, payment.getDate());
        pstm.setObject(3, payment.getAmount());
        pstm.setObject(4, payment.getBonus());
        pstm.setObject(5, payment.getPaymentId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM payment WHERE paymentId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
