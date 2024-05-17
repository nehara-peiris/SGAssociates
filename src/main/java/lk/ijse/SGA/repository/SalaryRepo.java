package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Salary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryRepo {
    public static List<Salary> getAll() throws SQLException {
        String sql = "SELECT * FROM salary";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Salary> salaryList = new ArrayList<>();
        while (resultSet.next()) {
            String lawyerID = resultSet.getString(1);
            double totalSalary = Double.parseDouble(resultSet.getString(2));


            Salary salary = new Salary(lawyerID, totalSalary);
            salaryList.add(salary);
        }

        return salaryList;
    }
}
