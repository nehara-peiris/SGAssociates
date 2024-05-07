package lk.ijse.SGA.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import lk.ijse.SGA.model.CaseCharge;
import lk.ijse.SGA.model.Client;
import lk.ijse.SGA.model.tm.CaseChargeTm;
import lk.ijse.SGA.model.tm.ClientTm;
import lk.ijse.SGA.repository.CaseChargeRepo;
import lk.ijse.SGA.repository.ClientRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CaseChargeFormController implements Initializable {
    public TextField txtCCPayId;
    public TextField txtCaseId;
    public TextField txtLawyerId;
    public TextField txtChargeId;
    public TextField txtDate;
    public TextField txtAmount;
    public TextField txtClientId;
    public TableView<CaseChargeTm> tblCaseCharge2;
    public TableColumn<?, ?> colCCPayId2;
    public TableColumn<?, ?> colCaseId2;
    public TableColumn<?, ?> colChargeId2;
    public TableColumn<?, ?> colDate2;
    public TableColumn<?, ?> colAmount2;
    public TableColumn<?, ?> colClientId2;
    public TableView<CaseChargeTm> tblCaseCharge1;
    public TableColumn<?, ?> colCCPayId1;
    public TableColumn<?, ?> colCaseId1;
    public TableColumn<?, ?> colDate1;
    public TableColumn<?, ?> colTotalPay1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllCaseChargeDetails();
        loadTotalCaseCharge();

        txtCCPayId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCaseId.requestFocus();
            }
        });

        txtCaseId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtChargeId.requestFocus();
            }
        });

        txtChargeId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDate.requestFocus();
            }
        });

        txtDate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAmount.requestFocus();
            }
        });

        txtAmount.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtClientId.requestFocus();
            }
        });

    }

    private void setCellValueFactory() {
        colCCPayId2.setCellValueFactory(new PropertyValueFactory<>("CCPayId"));
        colCaseId2.setCellValueFactory(new PropertyValueFactory<>("caseId"));
        colChargeId2.setCellValueFactory(new PropertyValueFactory<>("chargeId"));
        colDate2.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount2.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colClientId2.setCellValueFactory(new PropertyValueFactory<>("clientId"));


        colCCPayId1.setCellValueFactory(new PropertyValueFactory<>("CCPayId"));
        colCaseId1.setCellValueFactory(new PropertyValueFactory<>("caseId"));
        colDate1.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotalPay1.setCellValueFactory(new PropertyValueFactory<>("Total pay"));
    }

    private void loadAllCaseChargeDetails() {
        ObservableList<CaseChargeTm> obList = FXCollections.observableArrayList();

        try{
            List<CaseCharge> caseChargeList = CaseChargeRepo.getAll();
            for (CaseCharge caseCharge : caseChargeList){
                CaseChargeTm tm = new CaseChargeTm(
                        caseCharge.getCCPayId(),
                        caseCharge.getCaseId(),
                        caseCharge.getLawyerId(),
                        caseCharge.getChargeId(),
                        caseCharge.getDate(),
                        caseCharge.getAmount(),
                        caseCharge.getClientId()
                );
                obList.add(tm);
            }
            tblCaseCharge2.setItems(obList);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTotalCaseCharge() {

    }

    @FXML
    void btnSaveOnAction (ActionEvent event) throws SQLException {
        String CCPayId = txtCCPayId.getText();
        String caseId = txtCaseId.getText();
        String lawyerId = txtLawyerId.getText();
        String chargeId = txtChargeId.getText();
        Date date = Date.valueOf(txtDate.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        String clientId = txtClientId.getText();

        CaseCharge caseCharge = new CaseCharge(CCPayId, caseId, lawyerId, chargeId, date, amount, clientId);

        try{
            boolean isSaved = CaseChargeRepo.save(caseCharge);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "saved successfully!").show();
                loadAllCaseChargeDetails();
                clearFields();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtCCPayId.clear();
        txtCaseId.clear();
        txtChargeId.clear();
        txtDate.clear();
        txtAmount.clear();
        txtClientId.clear();
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String CCPayId = txtCCPayId.getText();
        String caseId = txtCaseId.getText();
        String lawyerId = txtLawyerId.getText();
        String chargeId = txtChargeId.getText();
        Date date = Date.valueOf(txtDate.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        String clientId = txtClientId.getText();

        CaseCharge caseCharge = new CaseCharge(CCPayId, caseId, lawyerId, chargeId, date, amount, clientId);

        try{
            boolean isUpdated = CaseChargeRepo.update(caseCharge);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "updated successfully!").show();
                loadAllCaseChargeDetails();
                clearFields();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCCPayId.getText();

        try {
            boolean isDeleted = CaseChargeRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
