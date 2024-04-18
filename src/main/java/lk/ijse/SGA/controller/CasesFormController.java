package lk.ijse.SGA.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.Cases;
import lk.ijse.SGA.model.tm.CasesTm;
import lk.ijse.SGA.repository.CasesRepo;

import java.util.List;

public class CasesFormController {
    @FXML
    private TextField txtCaseId;
    @FXML
    private TextField txtDescription;
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

    }

    private void loadAllCases() {
        ObservableList<CasesTm> obList = FXCollections.observableArrayList();

        List<Cases> casesList = CasesRepo.getAll();
        for (Cases cases : casesList) {
            CasesTm tm = new CasesTm(
                    cases.getClientId(),
                    cases.getDescription(),
                    cases.getDate(),
                    cases.getLawyerId()
            );

            obList.add(tm);
        }

        tblCase.setItems(obList);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String caseId = txtCaseId.getText();
        String clientId = txtClientId.getText();
        String description = txtDescription.getText();
        String date = txtDate.getText();
        String lawyerId = txtLawyerId.getText();

        Cases cases = new Cases(caseId, description, date, lawyerId, clientId);

        boolean isSaved = CasesRepo.save(cases);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "case saved!").show();
            clearFields();
        }
    }

    private void clearFields() {
        txtCaseId.setText("");
        txtDescription.setText("");
        txtDate.setText("");
        txtClientId.setText("");
        txtLawyerId.setText("");
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }
}
