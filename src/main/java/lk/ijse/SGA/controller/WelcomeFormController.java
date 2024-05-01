package lk.ijse.SGA.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeFormController {
    @FXML
    private AnchorPane rootNode;

    public void btnStartOnAction(ActionEvent event) throws IOException {
        navigateToTheLogin();
    }
    private void navigateToTheLogin() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }
}
