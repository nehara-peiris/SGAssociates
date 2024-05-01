package lk.ijse.SGA.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.tm.ClientTm;
import lk.ijse.SGA.model.tm.SalaryTm;

import java.net.URL;
import java.util.ResourceBundle;

public class SalaryFormController implements Initializable {

    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField txtSalaryId;
    @FXML
    private TextField txtLawyerID;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtBonus;
    @FXML
    private TableView<ClientTm> tblSalary;
    @FXML
    private TableColumn<?, ?> colLawyerId;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colAmount;
    @FXML
    private TableColumn<?, ?> colBonus;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllSalary();


        txtSalaryId.setOnKeyPressed(event -> {
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
    }

    private void setCellValueFactory() {
        colLawyerId.setCellValueFactory(new PropertyValueFactory<>("Lawyer ID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colBonus.setCellValueFactory(new PropertyValueFactory<>("Bonus"));
    }

    private void loadAllSalary() {
        ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

    }

    @FXML
    void btnSaveOnAction (ActionEvent event)  {

    }

    private void clearFields() {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event){

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }
}
