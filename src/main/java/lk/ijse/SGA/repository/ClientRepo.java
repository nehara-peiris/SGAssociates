package lk.ijse.SGA.repository;

import lk.ijse.SGA.db.DbConnection;
import lk.ijse.SGA.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepo {
    public static List<Client> getAll() throws SQLException {
        String sql = "SELECT * FROM client";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Client> clientList = new ArrayList<>();

        while (resultSet.next()) {
            String clientId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String email = resultSet.getString(4);
            int contact = Integer.parseInt(resultSet.getString(5));
            String lawyerId = resultSet.getString(6);

            Client client = new Client(clientId, name, address, email, contact, lawyerId);
            clientList.add(client);
        }
        return clientList;
    }

    public static Client searchById(String id) throws SQLException {
        String sql = "SELECT * FROM client WHERE clientId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String clientId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String email = resultSet.getString(4);
            int contact = Integer.parseInt(resultSet.getString(5));
            String lawyerId = resultSet.getString(6);

            Client client = new Client(clientId, name, address, email, contact, lawyerId);

            return client;
        }
        return null;
    }

    public static boolean save(Client client) throws SQLException {
        String sql = "INSERT INTO client VALUES(?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, client.getClientId());
        pstm.setObject(2, client.getName());
        pstm.setObject(3, client.getAddress());
        pstm.setObject(4, client.getEmail());
        pstm.setObject(5, client.getContact());
        pstm.setObject(6, client.getLawyerId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Client client) throws SQLException {
        String sql = "UPDATE client SET name = ?, address = ?, email = ?, contact = ?, lawyerId = ? WHERE clientId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, client.getName());
        pstm.setObject(2, client.getAddress());
        pstm.setObject(3, client.getEmail());
        pstm.setObject(4, client.getContact());
        pstm.setObject(5, client.getLawyerId());
        pstm.setObject(6, client.getClientId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM client WHERE clientId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
