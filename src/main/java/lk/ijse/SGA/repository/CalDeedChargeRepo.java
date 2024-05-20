package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.CalDeedCharge;

import java.sql.Connection;
import java.sql.SQLException;

public class CalDeedChargeRepo {
    public static boolean calDeedCharge(CalDeedCharge dc) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isPaymentSaved = PaymentRepo.save(dc.getPayment());
            if (isPaymentSaved) {
                for (int i = 0; i < dc.getDeedChargeList().size(); i++) {
                    boolean save = DeedChargeRepo.save(dc.getDeedChargeList().get(i));
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


/*
        try {
            boolean isPaymentSaved = PaymentRepo.save(cc.getPayment());
            if (isPaymentSaved) {
                boolean isDeedChargeSaved = DeedChargeRepo.save((DeedCharge) cc.getDeedChargeList());
                if (isDeedChargeSaved) {
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

 */
    }
}
