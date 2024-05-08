package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Salary;
import lk.ijse.SGA.model.TotalSalary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryRepo {
    public static List<Salary> getAll() throws SQLException {
      //  String sql = "SELECT c.lawyerId ,c.caseId, c.chargeId AS case_chargeId, c.date AS case_date, c.amount AS case_amount, d.deedId, d.chargeId AS deed_chargeId, d.date AS deed_date, d.amount AS deed_amount FROM caseCharge c JOIN deedCharge d ON c.lawyerId = d.lawyerId WHERE c.lawyerId = ?";

        String sql = "SELECT * FROM salary";
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

        return salaryList;
    }
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
    }*/

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

    public static boolean save(Salary salary) throws SQLException {
        String sql = "INSERT INTO salary VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, salary.getSalaryId());
        pstm.setObject(2, salary.getLawyerId());
        pstm.setObject(3, salary.getDate());
        pstm.setObject(4, salary.getAmount());
        pstm.setObject(5, salary.getBonus());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Salary salary) throws SQLException {
        String sql = "UPDATE salary SET lawyerId = ?, date = ?, amount = ?, bonus = ? WHERE salaryId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, salary.getLawyerId());
        pstm.setObject(2, salary.getDate());
        pstm.setObject(3, salary.getAmount());
        pstm.setObject(4, salary.getBonus());
        pstm.setObject(5, salary.getSalaryId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM salary WHERE salaryId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
