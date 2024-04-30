package lk.ijse.SGA.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.Charge;

import lk.ijse.SGA.model.tm.ChargeTm;
import lk.ijse.SGA.repository.ChargeRepo;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ChargeFormController implements Initializable {
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TableView<ChargeTm> tblCharge;
    @FXML
    private TextField txtChargeId;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtDate;
    @FXML
    private TableColumn<?, ?> colChargeId;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colAmount;
    @FXML
    private TableColumn<?, ?> colDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllCharges();


        txtChargeId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDescription.requestFocus();
            }
        });

        txtDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAmount.requestFocus();
            }
        });

    }

    private void setCellValueFactory() {
        colChargeId.setCellValueFactory(new PropertyValueFactory<>("ChargeId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
    }

    private void loadAllCharges() {
        ObservableList<ChargeTm> obList = FXCollections.observableArrayList();

        try {
            List<Charge> chargeList = ChargeRepo.getAll();
            for (Charge charge : chargeList) {
                ChargeTm tm = new ChargeTm(
                        charge.getChargeId(),
                        charge.getDescription(),
                        charge.getAmount()
                );

                obList.add(tm);
            }

            tblCharge.setItems(obList);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction (ActionEvent event) throws SQLException {
        String chargeId = txtChargeId.getText();
        String description = txtDescription.getText();
        double amount = Double.parseDouble(txtAmount.getText());

        Charge charge = new Charge(chargeId, description, amount);

        boolean isSaved = ChargeRepo.save(charge);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "charge saved!").show();
            clearFields();
        }
    }

    private void clearFields() {
        txtChargeId.clear();
        txtDescription.clear();
        txtAmount.clear();
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String chargeId = txtChargeId.getText();
        String description = txtDescription.getText();
        double amount = Double.parseDouble(txtAmount.getText());

        Charge charge = new Charge(chargeId, description, amount);

        try {
            boolean isSaved = ChargeRepo.update(charge);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "charge updated!").show();
                clearFields();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtChargeId.getText();

        try {
            boolean isDeleted = ChargeRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "charge deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnCasesOnAction(ActionEvent event) throws IOException {
        AnchorPane caseCharge = FXMLLoader.load(this.getClass().getResource("/view/CaseChargeForm.fxml"));

        rootNode.getChildren().add(caseCharge);
    }

    @FXML
    void btnDeedsOnAction(ActionEvent event) throws IOException {
        AnchorPane deedCharge = FXMLLoader.load(this.getClass().getResource("/view/DeedChargeForm.fxml"));

        rootNode.getChildren().add(deedCharge);
    }
}
