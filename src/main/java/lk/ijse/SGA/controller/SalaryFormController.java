package lk.ijse.SGA.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.tm.SalaryTm;

import java.net.URL;
import java.util.ResourceBundle;

public class SalaryFormController implements Initializable {

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
        //getCurrentId();


        Validations();
        addTextChangeListener(txtPaymentId);

    }

    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtPaymentId && !newValue.matches("^P.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with P").show();
            }

            if (textField == txtLawyerID && !newValue.matches("^L.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with L").show();
            }

            if (textField == txtDate && !newValue.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                new Alert(Alert.AlertType.ERROR, "Date format must be YYYY-MM-DD").show();
            }

            if (textField == txtAmount && !newValue.matches("^-?\\d+(\\.\\d+)?$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid number format").show();
            }

            if (textField == txtBonus && !newValue.matches("^-?\\d+(\\.\\d+)?$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid number format").show();
            }
        });
    }

    private void Validations() {
        txtPaymentId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtPaymentId.getText().isEmpty() && !event.getCharacter().equals("P")){
                event.consume();
            }
        });
    }

}
