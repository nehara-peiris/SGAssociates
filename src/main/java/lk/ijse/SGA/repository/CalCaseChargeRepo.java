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
                boolean isCaseChargeSaved = CaseChargeRepo.save((CaseCharge) cc.getCaseChargeList());
                if (isCaseChargeSaved) {
                    connection.commit();
                    return true;
                }
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



