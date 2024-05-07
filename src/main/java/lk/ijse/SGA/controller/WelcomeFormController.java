package lk.ijse.SGA.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class WelcomeFormController implements Initializable {
    public Label lblDate;
    public Label lblTime;
    @FXML
    private AnchorPane rootNode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        setTime();
    }
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

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    public void setTime() {

        new Thread(() -> {
            while (true) {
                try {
                    String formattedTime = java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
                    Platform.runLater(() -> lblTime.setText(formattedTime));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
