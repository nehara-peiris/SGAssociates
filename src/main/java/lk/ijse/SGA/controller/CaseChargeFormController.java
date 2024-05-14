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
import javafx.scene.input.KeyEvent;
import lk.ijse.SGA.model.CaseCharge;
import lk.ijse.SGA.model.tm.CaseChargeTm;
import lk.ijse.SGA.model.tm.TotalCaseChargeTm;
import lk.ijse.SGA.repository.CaseChargeRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public TableView<TotalCaseChargeTm> tblCaseCharge1;
    public TableColumn<?, ?> colCaseId1;
    public TableColumn<?, ?> colTotalPay1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllCaseChargeDetails();
        loadTotalCaseCharge();
        keyEventsHandling();

        Validations();
        addTextChangeListener(txtCCPayId);
        addTextChangeListener(txtCaseId);
        addTextChangeListener(txtLawyerId);
        addTextChangeListener(txtChargeId);
        addTextChangeListener(txtDate);
        addTextChangeListener(txtAmount);
        addTextChangeListener(txtClientId);

    }

    private void Validations() {
        txtCCPayId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtCCPayId.getText().isEmpty() && !event.getCharacter().equals("CCP")){
                event.consume();
            }
        });

        txtCaseId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtCaseId.getText().isEmpty() && !event.getCharacter().equals("CA")){
                event.consume();
            }
        });

        txtLawyerId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtLawyerId.getText().isEmpty() && !event.getCharacter().equals("L")){
                event.consume();
            }
        });

        txtChargeId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtChargeId.getText().isEmpty() && !event.getCharacter().equals("CH")){
                event.consume();
            }
        });

        txtClientId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtClientId.getText().isEmpty() && !event.getCharacter().equals("C")){
                event.consume();
            }
        });
    }
    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtCCPayId && !newValue.matches("^CCP.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with CCP").show();
            }

            if (textField == txtCaseId && !newValue.matches("^CA.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with CA").show();
            }

            if (textField == txtLawyerId && !newValue.matches("^L.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with L").show();
            }

            if (textField == txtChargeId && !newValue.matches("^CH.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with CH").show();
            }

            if (textField == txtDate && !newValue.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                new Alert(Alert.AlertType.ERROR, "Date format must be YYYY-MM-DD").show();
                textField.setText(oldValue != null ? oldValue : "");
            }

            if (textField == txtAmount && !newValue.matches("^-?\\d+(\\.\\d+)?$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid number format").show();

            }

            if (textField == txtClientId && !newValue.matches("^C.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with C").show();
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


        colCaseId1.setCellValueFactory(new PropertyValueFactory<>("caseId"));
        colTotalPay1.setCellValueFactory(new PropertyValueFactory<>("TotalCharge"));
    }

    private void keyEventsHandling() {
        txtCCPayId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCaseId.requestFocus();
            }
        });

        txtCaseId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtLawyerId.requestFocus();
            }
        });

        txtLawyerId.setOnKeyPressed(event -> {
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
        ObservableList<TotalCaseChargeTm> obList = FXCollections.observableArrayList();

        try {
            List<CaseCharge> caseChargeList = CaseChargeRepo.getAll();
            Map<String, Double> totalChargeMap = new HashMap<>();

            for (CaseCharge caseCharge : caseChargeList) {
                String caseId = caseCharge.getCaseId();
                double amount = caseCharge.getAmount();

                totalChargeMap.put(caseId, totalChargeMap.getOrDefault(caseId, 0.0) + amount);
            }
            for (Map.Entry<String, Double> entry : totalChargeMap.entrySet()) {
                String caseId = entry.getKey();
                double totalCharge = entry.getValue();

                TotalCaseChargeTm tm = new TotalCaseChargeTm(null, caseId, null, totalCharge);
                obList.add(tm);
            }
            tblCaseCharge1.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction (ActionEvent event) throws SQLException {
        String CCPayId = txtCCPayId.getText();
        String caseId = txtCaseId.getText();
        String lawyerId = txtLawyerId.getText();
        String chargeId = txtChargeId.getText();
        String dateOfCharge = txtDate.getText();
        String crgAmount = txtAmount.getText();
        String clientId = txtClientId.getText();

        if (CCPayId.isEmpty() || caseId.isEmpty() || lawyerId.isEmpty() || chargeId.isEmpty() || dateOfCharge.isEmpty() || crgAmount.isEmpty() || clientId.isEmpty()) {

            if (CCPayId.isEmpty()) {
                txtCCPayId.requestFocus();
                txtCCPayId.setStyle("-fx-border-color: red;");
            } else if (caseId.isEmpty()) {
                txtCaseId.requestFocus();
                txtCaseId.setStyle("-fx-border-color: red;");
            } else if (lawyerId.isEmpty()) {
                txtLawyerId.requestFocus();
                txtLawyerId.setStyle("-fx-border-color: red;");
            } else if (chargeId.isEmpty()) {
                txtChargeId.requestFocus();
                txtChargeId.setStyle("-fx-border-color: red;");
            } else if (dateOfCharge.isEmpty()) {
                txtDate.requestFocus();
                txtDate.setStyle("-fx-border-color: red;");
            } else if (crgAmount.isEmpty()) {
                txtAmount.requestFocus();
                txtAmount.setStyle("-fx-border-color: red;");
            } else {
                txtClientId.requestFocus();
                txtClientId.setStyle("-fx-border-color: red;");
            }
            return;
        }

        Date date = Date.valueOf(dateOfCharge);
        double amount = Double.parseDouble(crgAmount);

        CaseCharge caseCharge = new CaseCharge(CCPayId, caseId, lawyerId, chargeId, date, amount, clientId);

        try{
            boolean isSaved = CaseChargeRepo.save(caseCharge);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "saved successfully!").show();
                loadAllCaseChargeDetails();
                clearFields();
                loadTotalCaseCharge();
                txtCCPayId.requestFocus();
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
