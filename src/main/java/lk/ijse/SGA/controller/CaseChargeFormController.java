package lk.ijse.SGA.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.SGA.model.CaseCharge;
import lk.ijse.SGA.model.tm.CaseChargeTm;
import lk.ijse.SGA.model.tm.TotalCaseChargeTm;
import lk.ijse.SGA.repository.CaseChargeRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CaseChargeFormController implements Initializable {
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

                TotalCaseChargeTm tm = new TotalCaseChargeTm(caseId, totalCharge);
                obList.add(tm);
            }
            tblCaseCharge1.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
