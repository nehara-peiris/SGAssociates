package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Salary;
import lk.ijse.SGA.model.TotalSalary;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TotalSalaryRepo {
    public static List<TotalSalary> getTotalSalary() throws SQLException {
        String sql = "select lawyerId, SUM(amount+bonus) as totalSalary from salary group by lawyerId";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<TotalSalary> salaryList = new ArrayList<>();
        while (resultSet.next()) {
            String salaryID = resultSet.getString(1);
            String lawyerID = resultSet.getString(2);
            Date date = Date.valueOf(resultSet.getString(3));
            double amount = Double.parseDouble(resultSet.getString(4));
            double bonus = Double.parseDouble(resultSet.getString(5));

            double totSal = amount + bonus;

            TotalSalary totalSalary = new TotalSalary(lawyerID,date);
            salaryList.add(totalSalary);
        }

        return salaryList;
    }
}
