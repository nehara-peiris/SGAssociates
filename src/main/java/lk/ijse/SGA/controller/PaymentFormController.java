package lk.ijse.SGA.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.Payment;
import lk.ijse.SGA.model.Salary;
import lk.ijse.SGA.model.tm.PaymentTm;
import lk.ijse.SGA.model.tm.SalaryTm;
import lk.ijse.SGA.repository.PaymentRepo;
import lk.ijse.SGA.repository.SalaryRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TableView<PaymentTm> tblPayment;
    @FXML
    private TableColumn<?, ?> colPayId2;
    @FXML
    private TableColumn<?, ?> colLawyerId2;
    @FXML
    private TableColumn<?, ?> colDate2;
    @FXML
    private TableColumn<?, ?> colAmount2;
    @FXML
    private TableView<SalaryTm>  tblSalary;
    @FXML
    private TableColumn<?, ?>  colLawyerId1;
    @FXML
    private TableColumn<?, ?>  colTotalSalary1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllPayment();
        loadTotalSalary();
    }

    private void setCellValueFactory() {
        colPayId2.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colLawyerId2.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
        colDate2.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount2.setCellValueFactory(new PropertyValueFactory<>("amount"));


        colLawyerId1.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
        colTotalSalary1.setCellValueFactory(new PropertyValueFactory<>("totalSalary"));
    }

    private void loadAllPayment() {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        try{
            List<Payment> paymentList = PaymentRepo.getAll();
            for (Payment payment : paymentList) {
                PaymentTm tm = new PaymentTm(
                        payment.getPaymentId(),
                        payment.getLawyerId(),
                        payment.getDate(),
                        payment.getAmount()
                );
                obList.add(tm);
            }
            tblPayment.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void loadTotalSalary() {
        ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

        try{
            List<Salary> salaryList = SalaryRepo.getAll();
            for (Salary salary : salaryList) {
                SalaryTm tm = new SalaryTm(
                        salary.getLawyerId(),
                        salary.getTotalSalary()
                );
                obList.add(tm);
            }

            tblSalary.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
