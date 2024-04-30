package lk.ijse.SGA.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class DashboardFormController {
    @FXML
    private Label lblUserId;
    @FXML
    private Label lblUsername;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private AnchorPane mainNode;


    @FXML
    void btnClientsOnAction(ActionEvent event) throws IOException {
        AnchorPane clientForm = FXMLLoader.load(this.getClass().getResource("/view/ClientForm.fxml"));

        rootNode.getChildren().add(clientForm);
    }

    @FXML
    void btnLawyersOnAction(ActionEvent event) throws IOException {
        AnchorPane lawyerForm = FXMLLoader.load(this.getClass().getResource("/view/LawyerForm.fxml"));

        rootNode.getChildren().add(lawyerForm);
    }

    @FXML
    void btnDeedsOnAction(ActionEvent event) throws IOException {
        AnchorPane deedForm = FXMLLoader.load(this.getClass().getResource("/view/DeedForm.fxml"));

        rootNode.getChildren().add(deedForm);
    }

    @FXML
    void btnJudgesOnAction(ActionEvent event) throws IOException {
        AnchorPane judgeForm = FXMLLoader.load(this.getClass().getResource("/view/JudgeForm.fxml"));

        rootNode.getChildren().add(judgeForm);
    }

    @FXML
    void btnCasesOnAction(ActionEvent event) throws IOException {
        AnchorPane casesForm = FXMLLoader.load(this.getClass().getResource("/view/CasesForm.fxml"));

        rootNode.getChildren().add(casesForm);
    }
    @FXML
    void btnCourtsOnAction(ActionEvent event) throws IOException {
        AnchorPane courtForm = FXMLLoader.load(this.getClass().getResource("/view/CourtForm.fxml"));

        rootNode.getChildren().add(courtForm);
    }
    @FXML
    void btnSummonsOnAction(ActionEvent event) throws IOException {
        AnchorPane summonForm = FXMLLoader.load(this.getClass().getResource("/view/SummonForm.fxml"));

        rootNode.getChildren().add(summonForm);
    }
    @FXML
    void btnChargesOnAction(ActionEvent event) throws IOException {
        AnchorPane chargeForm = FXMLLoader.load(this.getClass().getResource("/view/ChargeForm.fxml"));

        rootNode.getChildren().add(chargeForm);
    }

    @FXML
    void btnSalaryOnAction(ActionEvent event) throws IOException {
        AnchorPane salaryForm = FXMLLoader.load(this.getClass().getResource("/view/SalaryForm.fxml"));

        rootNode.getChildren().add(salaryForm);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        AnchorPane loginForm = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));

        mainNode.getChildren().add(loginForm);
    }

    @FXML
    void btnUpWkOnAction(ActionEvent event) throws IOException {

    }
    @FXML
    void btnInProgOnAction(ActionEvent event) throws IOException {

    }
}
