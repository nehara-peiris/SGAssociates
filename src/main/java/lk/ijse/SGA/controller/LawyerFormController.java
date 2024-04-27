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
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.Lawyer;
import lk.ijse.SGA.model.tm.LawyerTm;
import lk.ijse.SGA.repository.LawyerRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LawyerFormController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        try{
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
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction (ActionEvent event) {
        String lawyerId = txtLawyerId.getText();
        String name = txtName.getText();
        int yrsOfExp = Integer.parseInt(txtYrsOfExp.getText());
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        int contact = Integer.parseInt(txtContact.getText());

        Lawyer lawyer = new Lawyer(lawyerId, name, yrsOfExp, address, email, contact);

        try{
            boolean isSaved = LawyerRepo.save(lawyer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Lawyer saved!").show();
                clearFields();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
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
        String lawyerId = txtLawyerId.getText();
        String name = txtName.getText();
        int yrsOfExp = Integer.parseInt(txtYrsOfExp.getText());
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        int contact = Integer.parseInt(txtContact.getText());

        Lawyer lawyer = new Lawyer(lawyerId, name, yrsOfExp, address, email, contact);

        try{
            boolean isUpdated = LawyerRepo.update(lawyer);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Lawyer updated!").show();
                clearFields();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtLawyerId.getText();

        try {
            boolean isDeleted = LawyerRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Lawyer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
