package lk.ijse.SGA.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.SGA.model.DeedCharge;
import lk.ijse.SGA.model.tm.DeedChargeTm;
import lk.ijse.SGA.model.tm.TotalDeedChargeTm;
import lk.ijse.SGA.repository.DeedChargeRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DeedChargeFormController implements Initializable {
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
    private TableColumn<?, ?> colDeedId1;
    @FXML
    private TableColumn<?, ?> colTotalPay1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllDeedChargeDetails();
        loadTotalDeedCharge();
    }

    private void setCellValueFactory() {
        colDCPayId2.setCellValueFactory(new PropertyValueFactory<>("DCPayId"));
        colDeedId2.setCellValueFactory(new PropertyValueFactory<>("DeedId"));
        colChargeId2.setCellValueFactory(new PropertyValueFactory<>("chargeId"));
        colDate2.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount2.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colClientId2.setCellValueFactory(new PropertyValueFactory<>("clientId"));


        colDeedId1.setCellValueFactory(new PropertyValueFactory<>("deedId"));
        colTotalPay1.setCellValueFactory(new PropertyValueFactory<>("TotalCharge"));
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

    private void loadTotalDeedCharge() {
        ObservableList<TotalDeedChargeTm> obList = FXCollections.observableArrayList();

        try{
            List<DeedCharge> deedChargeList = DeedChargeRepo.getAll();
            Map<String, Double> totalChargeMap = new HashMap<>();

            for (DeedCharge deedCharge : deedChargeList) {
                String deedId = deedCharge.getDeedId();
                double amount = deedCharge.getAmount();

                totalChargeMap.put(deedId, totalChargeMap.getOrDefault(deedId, 0.0) + amount);
            }

            for (Map.Entry<String, Double> entry : totalChargeMap.entrySet()) {
                String deedId = entry.getKey();
                double totalCharge = entry.getValue();

                TotalDeedChargeTm tm = new TotalDeedChargeTm(deedId, totalCharge);
                obList.add(tm);
            }
            tblDeedCharge1.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
