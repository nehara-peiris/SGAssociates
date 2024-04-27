package lk.ijse.SGA.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.Cases;
import lk.ijse.SGA.model.tm.CasesTm;
import lk.ijse.SGA.repository.CasesRepo;
import lk.ijse.SGA.repository.ClientRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CasesFormController implements Initializable {
    @FXML
    private NumberAxis cNoAxis;
    @FXML
    private CategoryAxis cTypeAxis;
    @FXML
    private BarChart<?,?> BarChartCase;
    @FXML
    private TextField txtCaseId;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtClientId;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtLawyerId;
    @FXML
    private TableView<CasesTm> tblCase;
    @FXML
    private TableColumn<?,?> colClientId;
    @FXML
    private TableColumn<?,?> colDescription;
    @FXML
    private TableColumn<?,?> colDate;
    @FXML
    private TableColumn<?,?> colLawyerId;
    @FXML
    private TableColumn<?,?> colType;
    @FXML
    private AnchorPane rootNode;


    public void initialize(){
        setCellValueFactory();
        loadAllCases();
    }

    private void setCellValueFactory() {
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLawyerId.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

    }

    private void loadAllCases() {
        ObservableList<CasesTm> obList = FXCollections.observableArrayList();

        try{
            List<Cases> casesList = CasesRepo.getAll();
            for (Cases cases : casesList) {
                CasesTm tm = new CasesTm(
                        cases.getDescription(),
                        cases.getDate(),
                        cases.getLawyerId(),
                        cases.getClientId()
                );

                obList.add(tm);
            }

            tblCase.setItems(obList);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String caseId = txtCaseId.getText();
        String clientId = txtClientId.getText();
        String description = txtDescription.getText();
        String type = txtType.getText();
        Date date = Date.valueOf(txtDate.getText());
        String lawyerId = txtLawyerId.getText();

        Cases cases = new Cases(caseId, description, type, date, lawyerId, clientId);

        try{
            boolean isSaved = CasesRepo.save(cases);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "case saved!").show();
                clearFields();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtCaseId.setText("");
        txtDescription.setText("");
        txtDate.setText("");
        txtClientId.setText("");
        txtLawyerId.setText("");
        txtType.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String caseId = txtCaseId.getText();
        String clientId = txtClientId.getText();
        String description = txtDescription.getText();
        String type = txtType.getText();
        Date date = Date.valueOf(txtDate.getText());
        String lawyerId = txtLawyerId.getText();

        Cases cases = new Cases(caseId, description, type, date, lawyerId, clientId);

        try{
            boolean isUpdated = CasesRepo.update(cases);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "case updated!").show();
                clearFields();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCaseId.getText();

        try {
            boolean isDeleted = CasesRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Client deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        XYChart.Series series1 = new XYChart.Series<>();
        series1.getData().add(new XYChart.Data("Criminal","1200"));
        BarChartCase.getData().addAll(series1);
    }
}
