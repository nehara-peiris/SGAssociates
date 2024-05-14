package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Cases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRepo {

    /*
    public static boolean userDetails(String userId) throws SQLException {
       String sql = "SELECT name = ? FROM user WHERE userId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String name = resultSet.getString(1);
            String userId = resultSet.getString(2);

            Cases cases = new Cases(caseId, description, type, date, clientId);
            caseList.add(cases);
        }
        return caseList;
    }
    */
}
