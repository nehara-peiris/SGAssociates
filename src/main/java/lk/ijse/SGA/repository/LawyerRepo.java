package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.LawCase;
import lk.ijse.SGA.model.Lawyer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LawyerRepo {
    public static List<Lawyer> getAll() throws SQLException {
        String sql = "SELECT * FROM lawyer";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Lawyer> lawyerList = new ArrayList<>();

        while (resultSet.next()) {
            String lawyerId = resultSet.getString(1);
            String name = resultSet.getString(2);
            int yrsOfExp = Integer.parseInt(resultSet.getString(3));
            String address = resultSet.getString(4);
            String email = resultSet.getString(5);
            int contact = Integer.parseInt(resultSet.getString(6));

            Lawyer lawyer = new Lawyer(lawyerId, name, yrsOfExp, address, email, contact);
            lawyerList.add(lawyer);
        }
        return lawyerList;
    }

    public static List<LawCase> assignedCases() throws SQLException {
        String sql = "SELECT * FROM lawCase";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<LawCase> lawCaseList = new ArrayList<>();

        while (resultSet.next()) {
            String lawyerId = resultSet.getString(1);
            String caseId = resultSet.getString(2);
            Date date = Date.valueOf(resultSet.getString(3));

            LawCase lawCase = new LawCase(lawyerId, caseId, date);
            lawCaseList.add(lawCase);
        }
        return lawCaseList;
    }

    public static boolean save(Lawyer lawyer) throws SQLException {
        String sql = "INSERT INTO lawyer VALUES(?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, lawyer.getLawyerId());
        pstm.setObject(2, lawyer.getName());
        pstm.setObject(3, lawyer.getYrsOfExp());
        pstm.setObject(4, lawyer.getAddress());
        pstm.setObject(5, lawyer.getEmail());
        pstm.setObject(6, lawyer.getContact());

        return pstm.executeUpdate() > 0;

    }
    public static boolean update(Lawyer lawyer) throws SQLException {
        String sql = "UPDATE lawyer SET name = ?, yrsOfExp = ?, address = ?, email = ?, contact = ? WHERE lawyerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, lawyer.getName());
        pstm.setObject(2, lawyer.getYrsOfExp());
        pstm.setObject(3, lawyer.getAddress());
        pstm.setObject(4, lawyer.getEmail());
        pstm.setObject(5, lawyer.getContact());
        pstm.setObject(6, lawyer.getLawyerId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM lawyer WHERE lawyerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static Lawyer searchById(String id) throws SQLException {
        String sql = "SELECT * FROM lawyer WHERE lawyerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String lawyerId = resultSet.getString(1);
            String name = resultSet.getString(2);
            int yrsOfExp = Integer.parseInt(resultSet.getString(3));
            String address = resultSet.getString(4);
            String email = resultSet.getString(5);
            int contact = Integer.parseInt(resultSet.getString(6));

            Lawyer lawyer = new Lawyer(lawyerId, name, yrsOfExp, address, email, contact);

            return lawyer;
        }
        return null;
    }

    public static boolean saveLawcase(LawCase lawCase) throws SQLException {
        String sql = "INSERT INTO lawCase VALUES(?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, lawCase.getLawyerId());
        pstm.setObject(2, lawCase.getCaseId());
        pstm.setObject(3, lawCase.getDate());

        return pstm.executeUpdate() > 0;
    }
}
