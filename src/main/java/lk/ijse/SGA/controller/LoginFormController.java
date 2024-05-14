package lk.ijse.SGA.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.SGA.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane rootNode;
    public AnchorPane leafNode;
    public TextField txtUserId;
    public TextField txtPassword;
    public CheckBox checkBoxPw;

    @FXML
    void initialize() {
        //getUserDetails();
        keyHandling();
    }

    private void keyHandling() {
        txtUserId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPassword.requestFocus();
            }
        });

        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLoginOnAction(null);
            }
        });
    }

    /*
    private void getUserDetails() {
        String userId = txtUserId.getText();
        try{
            boolean isUsenameRecieved = UserRepo.userDetails(userId);
        }
    }
     */
    public void btnLoginOnAction(ActionEvent actionEvent) {
        String userId = txtUserId.getText();
        String password = txtPassword.getText();

        // Check if text fields are empty
        if (userId.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter both user ID and password").show();
            return;
        }

        try {
            checkCredential(userId, password);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void checkCredential(String userId, String password) throws SQLException, IOException {
        String sql = "SELECT userId, password FROM user WHERE userId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString("password");

            if(password.equals(dbPw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!").show();
        }
    }

    private void navigateToTheDashboard() throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/DashboardForm.fxml"));
            Parent rootNode = loader.load();
            Scene scene = new Scene(rootNode);

            DashboardFormController controller = loader.getController();
            controller.setUserDetail();

            Stage stage = (Stage) this.rootNode.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Dashboard Form");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void hypRegOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/RegisterForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Registration Form");

        stage.show();
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
