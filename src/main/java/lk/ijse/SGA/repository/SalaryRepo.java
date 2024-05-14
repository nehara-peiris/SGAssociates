package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Salary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryRepo {
/*
    public static List<Salary> getTotalSalary() throws SQLException {
        String sql = "select lawyerId, SUM(amount+bonus) from salary group by lawyerId";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Salary> salaryList = new ArrayList<>();
        while (resultSet.next()) {
            String salaryID = resultSet.getString(1);
            String lawyerID = resultSet.getString(2);
            Date date = Date.valueOf(resultSet.getString(3));
            double amount = Double.parseDouble(resultSet.getString(4));
            double bonus = Double.parseDouble(resultSet.getString(5));

            Salary salary = new Salary(salaryID, lawyerID, date, amount, bonus);
            salaryList.add(salary);
        }

        return null;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT salaryId FROM salary ORDER BY salaryId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT MAX(salaryId) FROM salary";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String maxSalaryId = resultSet.getString(1);
            return maxSalaryId;
        }
        return null;
    }
 */


    public static String getCurrentId() throws SQLException {
        String sql = "SELECT MAX(SUBSTRING(salaryId, 3)) FROM salary";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int maxNumericId = resultSet.getInt(1);
            int nextNumericId = maxNumericId + 1;
            String nextSalaryId = String.format("SA%03d", nextNumericId);
            return nextSalaryId;
        }
        return "SA001";
    }

    public static boolean save(Salary salary) throws SQLException {
        String sql = "INSERT INTO salary VALUES(?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, salary.getSalaryId());
        pstm.setObject(2, salary.getLawyerId());
        pstm.setObject(3, salary.getDate());
        pstm.setObject(4, salary.getTotalSalary());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Salary salary) throws SQLException {
        String sql = "UPDATE salary SET lawyerId = ?, date = ?, totalSalary = ? WHERE salaryId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, salary.getLawyerId());
        pstm.setObject(2, salary.getDate());
        pstm.setObject(3, salary.getTotalSalary());
        pstm.setObject(4, salary.getSalaryId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM salary WHERE salaryId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Salary> getAll() throws SQLException {
        String sql = "SELECT * FROM salary";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Salary> salaryList = new ArrayList<>();
        while (resultSet.next()) {
            String salaryId = resultSet.getString(1);
            String lawyerID = resultSet.getString(2);
            Date date = Date.valueOf(resultSet.getString(3));
            double totalSalary = Double.parseDouble(resultSet.getString(4));


            Salary salary = new Salary(salaryId, lawyerID, date, totalSalary);
            salaryList.add(salary);
        }

        return salaryList;
    }
}
