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
import lk.ijse.SGA.model.DeedCharge;
import lk.ijse.SGA.model.tm.DeedChargeTm;
import lk.ijse.SGA.model.tm.TotalDeedChargeTm;
import lk.ijse.SGA.repository.DeedChargeRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DeedChargeFormController implements Initializable {
    @FXML
    private TextField txtDCPayId;
    @FXML
    private TextField txtDeedId;
    @FXML
    private TextField txtLawyerId;
    @FXML
    private TextField txtChargeId;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtClientId;
    @FXML
    private TableView<DeedChargeTm> tblDeedCharge2;
    @FXML
    private TableColumn<?, ?> colDCPayId2;
    @FXML
    private TableColumn<?, ?> colDeedId2;
    @FXML
    private TableColumn<?, ?> colChargeId2;
    @FXML
    private TableColumn<?, ?> colDate2;
    @FXML
    private TableColumn<?, ?> colAmount2;
    @FXML
    private TableColumn<?, ?> colClientId2;
    @FXML
    private TableView<TotalDeedChargeTm> tblDeedCharge1;
    @FXML
    private TableColumn<?, ?> colDCPayId1;
    @FXML
    private TableColumn<?, ?> colDeedId1;
    @FXML
    private TableColumn<?, ?> colDate1;
    @FXML
    private TableColumn<?, ?> colTotalPay1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllDeedChargeDetails();
        loadTotalDeedCharge();
        keyEventsHandling();

        Validations();
        addTextChangeListener(txtDCPayId);
        addTextChangeListener(txtDeedId);
        addTextChangeListener(txtLawyerId);
        addTextChangeListener(txtChargeId);
        addTextChangeListener(txtDate);
        addTextChangeListener(txtAmount);
        addTextChangeListener(txtClientId);
    }

    private void Validations() {
        txtDCPayId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtDCPayId.getText().isEmpty() && !event.getCharacter().equals("DCP")){
                event.consume();
            }
        });

        txtDeedId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtDeedId.getText().isEmpty() && !event.getCharacter().equals("D")){
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

            if (textField == txtDCPayId && !newValue.matches("^DCP.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with CCP").show();
            }

            if (textField == txtDeedId && !newValue.matches("^D.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with D").show();
            }

            if (textField == txtLawyerId && !newValue.matches("^L.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with L").show();
            }

            if (textField == txtChargeId && !newValue.matches("^CH.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with CH").show();
            }

            if (textField == txtDate && !newValue.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                new Alert(Alert.AlertType.ERROR, "Date format must be YYYY-MM-DD").show();
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
        colDCPayId2.setCellValueFactory(new PropertyValueFactory<>("DCPayId"));
        colDeedId2.setCellValueFactory(new PropertyValueFactory<>("DeedId"));
        colChargeId2.setCellValueFactory(new PropertyValueFactory<>("chargeId"));
        colDate2.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount2.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colClientId2.setCellValueFactory(new PropertyValueFactory<>("clientId"));


        colDCPayId1.setCellValueFactory(new PropertyValueFactory<>("DCPayId"));
        colDeedId1.setCellValueFactory(new PropertyValueFactory<>("deedId"));
        colDate1.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotalPay1.setCellValueFactory(new PropertyValueFactory<>("TotalCharge"));
    }

    private void keyEventsHandling() {
        txtDCPayId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDeedId.requestFocus();
            }
        });

        txtDeedId.setOnKeyPressed(event -> {
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

    private void loadTotalDeedCharge() {
        ObservableList<TotalDeedChargeTm> obList = FXCollections.observableArrayList();

        try{
            List<DeedCharge> deedChargeList = DeedChargeRepo.getAll();
            for (DeedCharge deedCharge : deedChargeList) {
                TotalDeedChargeTm tm = new TotalDeedChargeTm(
                        deedCharge.getDCPayId(),
                        deedCharge.getDeedId(),
                        deedCharge.getDate(),
                        deedCharge.getAmount()+deedCharge.getAmount()
                );
                obList.add(tm);
            }

            //System.out.println(obList);

            tblDeedCharge1.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void loadAllDeedChargeDetails() {
        ObservableList<DeedChargeTm> obList = FXCollections.observableArrayList();

        try{
            List<DeedCharge> deedChargeList = DeedChargeRepo.getAll();
            for (DeedCharge deedCharge : deedChargeList){
                DeedChargeTm tm = new DeedChargeTm(
                        deedCharge.getDCPayId(),
                        deedCharge.getDeedId(),
                        deedCharge.getLawyerId(),
                        deedCharge.getChargeId(),
                        deedCharge.getDate(),
                        deedCharge.getAmount(),
                        deedCharge.getClientId()
                );
                obList.add(tm);
            }
            tblDeedCharge2.setItems(obList);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction (ActionEvent event) throws SQLException {
        String DCPayId = txtDCPayId.getText();
        String deedId = txtDeedId.getText();
        String lawyerId = txtLawyerId.getText();
        String chargeId = txtChargeId.getText();
        String dateOfDC = txtDate.getText();
        String dcAmount = txtAmount.getText();
        String clientId = txtClientId.getText();

        if (DCPayId.isEmpty() || deedId.isEmpty() || lawyerId.isEmpty() || chargeId.isEmpty() || dateOfDC.isEmpty() || dcAmount.isEmpty() || clientId.isEmpty()) {

            // Request focus on the unfilled TextField
            if (DCPayId.isEmpty()) {
                txtDCPayId.requestFocus();
                txtDCPayId.setStyle("-fx-border-color: red;");
            } else if (deedId.isEmpty()) {
                txtDeedId.requestFocus();
                txtDeedId.setStyle("-fx-border-color: red;");
            } else if (lawyerId.isEmpty()) {
                txtLawyerId.requestFocus();
                txtLawyerId.setStyle("-fx-border-color: red;");
            } else if (chargeId.isEmpty()) {
                txtChargeId.requestFocus();
                txtChargeId.setStyle("-fx-border-color: red;");
            } else if (dateOfDC.isEmpty()) {
                txtDate.requestFocus();
                txtDate.setStyle("-fx-border-color: red;");
            } else if (dcAmount.isEmpty()) {
                txtAmount.requestFocus();
                txtAmount.setStyle("-fx-border-color: red;");
            } else {
                txtClientId.requestFocus();
                txtClientId.setStyle("-fx-border-color: red;");
            }
            return;
        }

        Date date = Date.valueOf(dateOfDC);
        double amount = Double.parseDouble(dcAmount);

        DeedCharge deedCharge = new DeedCharge(DCPayId, deedId, lawyerId, chargeId, date, amount, clientId);

        try{
            boolean isSaved = DeedChargeRepo.save(deedCharge);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "saved successfully!").show();
                loadAllDeedChargeDetails();
                clearFields();
                loadTotalDeedCharge();
                txtDCPayId.requestFocus();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtDCPayId.clear();
        txtDeedId.clear();
        txtChargeId.clear();
        txtDate.clear();
        txtAmount.clear();
        txtClientId.clear();
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String DCPayId = txtDCPayId.getText();
        String deedId = txtDeedId.getText();
        String lawyerId = txtLawyerId.getText();
        String chargeId = txtChargeId.getText();
        Date date = Date.valueOf(txtDate.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        String clientId = txtClientId.getText();

        DeedCharge deedCharge = new DeedCharge(DCPayId, deedId, lawyerId, chargeId, date, amount, clientId);

        try{
            boolean isUpdated = DeedChargeRepo.update(deedCharge);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "updated successfully!").show();
                loadAllDeedChargeDetails();
                clearFields();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtDCPayId.getText();

        try {
            boolean isDeleted = DeedChargeRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
