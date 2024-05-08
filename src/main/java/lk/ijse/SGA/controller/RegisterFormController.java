package lk.ijse.SGA.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.db.DbConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {

    public AnchorPane rootNode;
    public CheckBox checkBoxPw;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtUserId;
    @FXML
    private TextField txtUsername;


    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String name = txtUsername.getText();
        String password = txtPassword.getText();

        try {
            boolean isSaved = saveUser(userId, name, password);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean saveUser(String userId, String name, String password) throws SQLException {
        String sql = "INSERT INTO user VALUES(?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);
        pstm.setObject(2, name);
        pstm.setObject(3, password);

        return pstm.executeUpdate() > 0;
    }

    public void chkPwOnAction(ActionEvent event) {
        if (checkBoxPw.isSelected()) {
            txtPassword.setPromptText(txtPassword.getText());
            txtPassword.setText("");
        } else {
            txtPassword.setText(txtPassword.getPromptText());
            txtPassword.setPromptText("");
        }
    }
}
