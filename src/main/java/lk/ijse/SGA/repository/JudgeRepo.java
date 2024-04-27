package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Judge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JudgeRepo {
    public static List<Judge> getAll() throws SQLException {
        String sql = "SELECT * FROM judge";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Judge> judgeList = new ArrayList<>();

        while (resultSet.next()){
            String judgeId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String courtId = resultSet.getString(3);
            int yrsOfExp = Integer.parseInt((resultSet.getString(4)));

            Judge judge = new Judge(judgeId, name, courtId, yrsOfExp);
            judgeList.add(judge);
        }
        return judgeList;
    }

    public static boolean save(Judge judge) throws SQLException {
        String sql = "INSERT INTO judge VALUES(?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, judge.getJudgeId());
        pstm.setObject(2, judge.getName());
        pstm.setObject(3, judge.getCourtId());
        pstm.setObject(4, judge.getYrsOfExp());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Judge judge) throws SQLException {
        String sql = "UPDATE judge SET name = ?, courtId = ?, yrsOfExp = ? WHERE judgeId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, judge.getJudgeId());
        pstm.setObject(2, judge.getName());
        pstm.setObject(3, judge.getCourtId());
        pstm.setObject(4, judge.getYrsOfExp());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM judge WHERE judgeId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
