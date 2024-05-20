package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.CalCaseCharge;
import lk.ijse.SGA.model.CaseCharge;

import java.sql.Connection;
import java.sql.SQLException;

public class CalCaseChargeRepo {
    public static boolean calCaseCharge(CalCaseCharge cc) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isPaymentSaved = PaymentRepo.save(cc.getPayment());
            if (isPaymentSaved) {
                for (int i = 0; i < cc.getCaseChargeList().size(); i++) {
                    boolean save = CaseChargeRepo.save(cc.getCaseChargeList().get(i));
                    if (!save) return false;
                }
                connection.commit();
                return true;
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}



