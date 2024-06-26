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
import javafx.scene.input.KeyEvent;
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

public class CalculateCaseChargeFormController implements Initializable {
    public JFXButton btnAddCart;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblPayId;
    @FXML
    private Label lblDate;
    @FXML
    private TextField txtCaseId;
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
        getCurrentPaymentID();
        setCellValueFactory();
        keyEventsHandling();

        Validations();
    }

    private void Validations() {
        txtCaseId.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[CA0-9]")) {
                event.consume();
            }
        });

        txtLawyerId.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[L0-9]")) {
                event.consume();
            }
        });

        txtClientId.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[C0-9]")) {
                event.consume();
            }
        });

        txtChargeId.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[CH0-9]")) {
                event.consume();
            }
        });
    }

    private void keyEventsHandling() {
        txtCaseId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtLawyerId.requestFocus();
            }
        });

        txtLawyerId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtClientId.requestFocus();
            }
        });

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

    private void getCurrentPaymentID() {
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

        txtChargeId.requestFocus();
    }

    private void calculateNetTotal() {
        double netTotal = 0;
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
                txtChargeId.requestFocus();
            }
        });

    }

    public void chargetxtOnAction(ActionEvent event)  {
        String chargeID = txtChargeId.getText();

        try {
            Charge data = ChargeRepo.getData(chargeID);
            System.out.println(data);

            txtChargeId.setOnKeyPressed(events -> {
                if (events.getCode() == KeyCode.ENTER) {
                    lblDescription.setText(data.getDescription());
                    lblAmount.setText(String.valueOf(data.getAmount()));
                    btnAddCart.requestFocus();
                }
            });
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void btnAddToDBOnAction(ActionEvent event) {
        String paymentID = lblPayId.getText();
        Date date = Date.valueOf(lblDate.getText());
        String caseId = txtCaseId.getText();
        String lawyerID = txtLawyerId.getText();
        String clientID = txtClientId.getText();
        String chargeId = txtChargeId.getText();
        double amount = Double.parseDouble(lblAmount.getText());
        double total = Double.parseDouble(lblTotal.getText());

        var pay = new Payment(paymentID, lawyerID, date, total);

        List<CaseCharge> caseChargeList = new ArrayList<>();
        ObservableList<ChargeCalculationTm> tblPayCalItems = tblPayCal.getItems();

        for (int i = 0; i < tblPayCal.getItems().size(); i++){
            CaseCharge caseCharge = new CaseCharge(
                    paymentID,
                    caseId,
                    lawyerID,
                    tblPayCalItems.get(i).getChargeId(),
                    date,
                    tblPayCalItems.get(i).getAmount(),
                    clientID
            );
            caseChargeList.add(caseCharge);
        }

        CalCaseCharge calCaseCharge = new CalCaseCharge();
        calCaseCharge.setPayment(pay);
        calCaseCharge.setCaseChargeList(caseChargeList);

        try {
            boolean isSaved = CalCaseChargeRepo.calCaseCharge(calCaseCharge);
            new Alert(Alert.AlertType.INFORMATION,isSaved?"saved":"error").show();
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }

    }

}

