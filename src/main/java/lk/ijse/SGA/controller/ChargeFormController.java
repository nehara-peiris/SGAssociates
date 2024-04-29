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
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.Charge;

import lk.ijse.SGA.model.tm.ChargeTm;
import lk.ijse.SGA.repository.ChargeRepo;
import lk.ijse.SGA.repository.ClientRepo;

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

        txtAmount.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDate.requestFocus();
            }
        });
    }

    private void setCellValueFactory() {
        colChargeId.setCellValueFactory(new PropertyValueFactory<>("ChargeId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }

    private void loadAllCharges() {
        ObservableList<ChargeTm> obList = FXCollections.observableArrayList();

        try {
            List<Charge> chargeList = ChargeRepo.getAll();
            for (Charge charge : chargeList) {
                ChargeTm tm = new ChargeTm(
                        charge.getChargeId(),
                        charge.getDescription(),
                        charge.getAmount(),
                        charge.getDate()
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
        Date date = Date.valueOf(txtDate.getText());

        Charge charge = new Charge(chargeId, description, amount, date);

        boolean isSaved = ChargeRepo.save(charge);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "charge saved!").show();
            clearFields();
        }
    }

    private void clearFields() {
        txtChargeId.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
        txtDate.setText("");
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String chargeId = txtChargeId.getText();
        String description = txtDescription.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        Date date = Date.valueOf(txtDate.getText());

        Charge charge = new Charge(chargeId, description, amount, date);

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

    }

    @FXML
    void btnDeedsOnAction(ActionEvent event) throws IOException {

    }
}
