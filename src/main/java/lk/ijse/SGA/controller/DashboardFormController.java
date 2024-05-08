package lk.ijse.SGA.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.repository.CasesRepo;
import lk.ijse.SGA.repository.DeedRepo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    @FXML
    private Label lblUserId;
    @FXML
    private Label lblUsername;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private AnchorPane mainNode;
    @FXML
    private BarChart<String, Number> chartCase;
    @FXML
    private CategoryAxis axisCases;
    @FXML
    private NumberAxis axisNoOfCases;
    @FXML
    private BarChart<String, Number> chartDeeds;
    @FXML
    private CategoryAxis axisDeeds;
    @FXML
    private NumberAxis axisNoOfDeeds;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCasesBarchart();
        setDeedBarchart();
        setUserDetail();
    }

    public void setUserDetail(){

    }

    private void setCasesBarchart() {
        axisCases.setLabel("Case Type");
        axisNoOfCases.setLabel("Number of Cases");

        try {
            populateCaseBarChart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void populateCaseBarChart() throws SQLException {
        chartCase.getData().clear();

        Map<String, Integer> caseTypeCounts = CasesRepo.getAllToChart();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        caseTypeCounts.forEach((type, count) -> {
            series.getData().add(new XYChart.Data<>(type, count));
        });

        chartCase.getData().add(series);
    }

    private void setDeedBarchart() {
        axisDeeds.setLabel("Deed Type");
        axisNoOfDeeds.setLabel("Number of Deeds");

        try {
            populateDeedBarChart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void populateDeedBarChart() throws SQLException {
        chartDeeds.getData().clear();

        Map<String, Integer> deedTypeCounts = DeedRepo.getAllToChart();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        deedTypeCounts.forEach((type, count) -> {
            series.getData().add(new XYChart.Data<>(type, count));
        });

        chartDeeds.getData().add(series);
    }

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
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardForm = FXMLLoader.load(this.getClass().getResource("/view/DashboardForm.fxml"));

        mainNode.getChildren().add(dashboardForm);
    }

    @FXML
    void btnReportsOnAction(ActionEvent event) throws IOException {

    }
}
