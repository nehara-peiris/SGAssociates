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
import lk.ijse.SGA.model.Charge;

import lk.ijse.SGA.model.tm.ChargeTm;
import lk.ijse.SGA.repository.ChargeRepo;

import java.util.List;

public class ChargeFormController {
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
    private TableColumn<?, ?> colChargeId;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colAmount;

    public void initialize(){
        setCellValueFactory();
        loadAllCharges();
    }

    private void setCellValueFactory() {
        colChargeId.setCellValueFactory(new PropertyValueFactory<>("ChargeId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
    }

    private void loadAllCharges() {
        ObservableList<ChargeTm> obList = FXCollections.observableArrayList();

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
    }

    @FXML
    void btnSaveOnAction (ActionEvent event){
        String chargeId = txtChargeId.getText();
        String description = txtDescription.getText();
        String amount = txtAmount.getText();

        Charge charge = new Charge(chargeId, description, amount);

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
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event){

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

}
