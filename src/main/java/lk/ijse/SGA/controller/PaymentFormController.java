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
import lk.ijse.SGA.model.Payment;
import lk.ijse.SGA.model.Salary;
import lk.ijse.SGA.model.tm.PaymentTm;
import lk.ijse.SGA.model.tm.SalaryTm;
import lk.ijse.SGA.repository.PaymentRepo;
import lk.ijse.SGA.repository.SalaryRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField txtPaymentId;
    @FXML
    private TextField txtLawyerID;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtBonus;
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
    private TableColumn<?, ?> colBonus2;
    @FXML
    private TableView<SalaryTm>  tblSalary;
    @FXML
    private TableColumn<?, ?>  colSalaryId1;
    @FXML
    private TableColumn<?, ?>  colLawyerId1;
    @FXML
    private TableColumn<?, ?>  colDate1;
    @FXML
    private TableColumn<?, ?>  colTotalSalary1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        //getCurrentId();
        setCellValueFactory();
        loadAllPayment();
        loadTotalSalary();

        keyEventsHandling();
    }
    private void keyEventsHandling() {
        txtPaymentId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtLawyerID.requestFocus();
            }
        });

        txtLawyerID.setOnKeyPressed(event -> {
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
                txtBonus.requestFocus();
            }
        });

        txtBonus.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSaveOnAction(null);
            }
        });
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    private void setCellValueFactory() {
        colPayId2.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colLawyerId2.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
        colDate2.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount2.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colBonus2.setCellValueFactory(new PropertyValueFactory<>("bonus"));


        colSalaryId1.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colLawyerId1.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
        colDate1.setCellValueFactory(new PropertyValueFactory<>("date"));
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
                        payment.getAmount(),
                        payment.getBonus()
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
                        salary.getSalaryId(),
                        salary.getLawyerId(),
                        salary.getDate(),
                        salary.getTotalSalary()
                );
                obList.add(tm);
            }

            tblSalary.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String paymentId = txtPaymentId.getText();
        String lawyerId = txtLawyerID.getText();
        Date date = Date.valueOf(txtDate.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        double bonus = Double.parseDouble(txtBonus.getText());

        Payment payment = new Payment(paymentId, lawyerId, date, amount, bonus);

        try {
            boolean isSaved = PaymentRepo.save(payment);
            if (isSaved) {
                String generatedSalaryId = PaymentRepo.getCurrentId();
                if (generatedSalaryId != null) {
                    txtPaymentId.setText(generatedSalaryId);
                }
                loadAllPayment();
                SalaryRepo.save(new Salary(null,lawyerId,date,amount+bonus));
                loadTotalSalary();
                new Alert(Alert.AlertType.CONFIRMATION, "Payment saved successfully!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void clearFields() {
        txtPaymentId.clear();
        txtLawyerID.clear();
        txtDate.clear();
        txtAmount.clear();
        txtBonus.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event){
        String paymentId = txtPaymentId.getText();
        String lawyerId = txtLawyerID.getText();
        Date date = Date.valueOf(txtDate.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        double bonus = Double.parseDouble(txtBonus.getText());

        Payment payment = new Payment(paymentId, lawyerId, date, amount, bonus);

        try{
            boolean isSaved = PaymentRepo.update(payment);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Payment updated successfully!").show();
                clearFields();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String id = txtPaymentId.getText();

        try {
            boolean isDeleted = PaymentRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "payment deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
