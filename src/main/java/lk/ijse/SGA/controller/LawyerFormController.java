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
import lk.ijse.SGA.model.Client;
import lk.ijse.SGA.model.Lawyer;
import lk.ijse.SGA.model.tm.ClientTm;
import lk.ijse.SGA.model.tm.LawyerTm;
import lk.ijse.SGA.repository.ClientRepo;
import lk.ijse.SGA.repository.LawyerRepo;

import java.sql.SQLException;
import java.util.List;

public class LawyerFormController {
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtLawyerId;
    @FXML
    private TextField txtYrsOfExp;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TableView<LawyerTm> tblLawyer;
    @FXML
    private TableColumn<?,?> colName;
    @FXML
    private TableColumn<?, ?> colYrsOfExp;
    @FXML
    private TableColumn<?, ?> colAddress;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colContact;
    @FXML
    private AnchorPane rootNode;

    public void initialize(){
        setCellValueFactory();
        loadAllLawyers();
    }

    private void setCellValueFactory() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colYrsOfExp.setCellValueFactory(new PropertyValueFactory<>("yrsOfExp"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

    }

    private void loadAllLawyers() {
        ObservableList<LawyerTm> obList = FXCollections.observableArrayList();

        List<Lawyer> lawyerList = LawyerRepo.getAll();
        for (Lawyer lawyer : lawyerList) {
            LawyerTm tm = new LawyerTm(
                    lawyer.getName(),
                    lawyer.getYrsOfExp(),
                    lawyer.getAddress(),
                    lawyer.getEmail(),
                    lawyer.getContact()
            );

            obList.add(tm);
        }
        tblLawyer.setItems(obList);
    }

    @FXML
    void btnSaveOnAction (ActionEvent event) throws SQLException {
        String lawyerId = txtLawyerId.getText();
        String name = txtName.getText();
        String yrsOfExp = txtYrsOfExp.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();

        Lawyer lawyer = new Lawyer(lawyerId, name, yrsOfExp, address, email, contact);

        boolean isSaved = LawyerRepo.save(lawyer);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Lawyer saved!").show();
            clearFields();
        }
    }

    private void clearFields() {
        txtLawyerId.setText("");
        txtName.setText("");
        txtYrsOfExp.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtContact.setText("");
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
