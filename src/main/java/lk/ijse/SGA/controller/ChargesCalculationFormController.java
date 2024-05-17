package lk.ijse.SGA.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import lk.ijse.SGA.model.*;
import lk.ijse.SGA.model.tm.ChargeCalculationTm;
import lk.ijse.SGA.repository.*;


import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class ChargesCalculationFormController implements Initializable {
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblPayId;
    @FXML
    private Label lblDate;
    @FXML
    private TextField txtDeedId;
    @FXML
    private TextField txtLawyerId;
    @FXML
    private TextField txtClientId;
    @FXML
    private Label lblClientName;
    @FXML
    private TextField txtChargeId;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblAmount;
    @FXML
    private TableView<ChargeCalculationTm> tblPayCal;
    @FXML
    private TableColumn<?, ?> colChargeId;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colAmount;
    @FXML
    private TableColumn<?, ?> colAction;


    private ObservableList<ChargeCalculationTm> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        getCurrentPayemntID();
        setCellValueFactory();
    }


    private void setCellValueFactory() {
        colChargeId.setCellValueFactory(new PropertyValueFactory<>("chargeId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }
    private void getCurrentPayemntID() {
        try {
            String currentId = PaymentRepo.getCurrentId();

            String nextOrderId = generateNextPayId(currentId);
            lblPayId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextPayId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("P");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return String.format("P%03d",idNum);
        }
        return "P001";
    }

    public void btnAddOnAction(ActionEvent event) {
        String chargeId = txtChargeId.getText();
        String description = lblDescription.getText();
        double amount = Double.parseDouble(lblAmount.getText());

        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblPayCal.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblPayCal.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblPayCal.getItems().size(); i++) {
            if (txtChargeId.equals(colChargeId.getCellData(i))) {


                ChargeCalculationTm tm = obList.get(i);
                tblPayCal.refresh();

                calculateNetTotal();
                return;

            }
        }

        ChargeCalculationTm tm = new ChargeCalculationTm(chargeId, description, amount, btnRemove);
        obList.add(tm);

        tblPayCal.setItems(obList);
        System.out.println(obList);
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblPayCal.getItems().size(); i++) {
            netTotal += (double) colAmount.getCellData(i);
        }
        lblTotal.setText(String.valueOf(netTotal));
    }

    public void clientIDOnAction(ActionEvent event) throws SQLException {
        String cid = txtClientId.getText();

        String name = ClientRepo.getName(cid);

        txtClientId.setOnKeyPressed(events -> {
            if (events.getCode() == KeyCode.ENTER) {
                lblClientName.setText(name);
            }
        });
    }

    public void chargetxtOnAction(ActionEvent event) throws SQLException {
        String chargeID = txtChargeId.getText();

        try {
            Charge data = ChargeRepo.getData(chargeID);
            System.out.println(data);

            txtChargeId.setOnKeyPressed(events -> {
                if (events.getCode() == KeyCode.ENTER) {
                    lblDescription.setText(data.getDescription());
                    lblAmount.setText(String.valueOf(data.getAmount()));
                }
            });
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void btnAddToDBOnAction(ActionEvent event) {
        String paymentID = lblPayId.getText();
        Date date = Date.valueOf(lblDate.getText());
        String deedID = txtDeedId.getText();
        String lawyerID = txtLawyerId.getText();
        String clientID = txtClientId.getText();
        String clientName = lblClientName.getText();
        String chargeId = txtChargeId.getText();
        String description = lblDescription.getText();
        double amount = Double.parseDouble(lblAmount.getText());
        double total = Double.parseDouble(lblTotal.getText());

        var pay = new Payment(paymentID, lawyerID, date, total);

        try {
            boolean isSaved = PaymentRepo.save(pay);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment saved successfully!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        List<DeedCharge> deedChargeList = new ArrayList<>();

        for (int i = 0; i < tblPayCal.getItems().size(); i++){
            ChargeCalculationTm tm = obList.get(i);

            DeedCharge deedCharge = new DeedCharge(paymentID, deedID, lawyerID, chargeId, date, amount, clientID);

            try {
                boolean isSaved = DeedChargeRepo.save(deedCharge);
                if (isSaved) {
                    deedChargeList.add(deedCharge);
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }
        ChargeCalculation chargeCalculation = new ChargeCalculation(pay, deedChargeList);

    }
}

