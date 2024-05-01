package lk.ijse.SGA.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.SGA.model.tm.DeedChargeTm;

import java.sql.SQLException;

public class DeedChargeFormController {
    @FXML
    private TextField txtDCPayId;
    @FXML
    private TextField txtDeedId;
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
    private TableView<DeedChargeTm> tblDeedCharge1;
    @FXML
    private TableColumn<?, ?> colDCPayId1;
    @FXML
    private TableColumn<?, ?> colDeedId1;
    @FXML
    private TableColumn<?, ?> colDate1;
    @FXML
    private TableColumn<?, ?> colTotalPay1;

    @FXML
    void btnSaveOnAction (ActionEvent event) throws SQLException {

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

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

}
