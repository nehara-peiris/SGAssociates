package lk.ijse.SGA.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.LawCase;
import lk.ijse.SGA.model.Lawyer;
import lk.ijse.SGA.model.tm.LawCaseTm;
import lk.ijse.SGA.model.tm.LawyerTm;
import lk.ijse.SGA.repository.LawyerRepo;

import javax.swing.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LawyerFormController implements Initializable {
    @FXML
    private TextField txtLawyerId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtYrsOfExp;
    @FXML
    private TextField txtEmail;
    @FXML
    private TableView<LawyerTm> tblLawyer;
    @FXML
    private TableColumn<?,?> colLawyerID;
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
    @FXML
    private TableView<LawCaseTm> tblAssignedWork;
    @FXML
    private TableColumn<?, ?> colLawyerId;
    @FXML
    private  TableColumn<?, ?> colCaseId;
    @FXML
    private  TableColumn<?, ?> colDate;


    public static void lawCaseUpdate(String caseId, String lawyerId, Date dateOfCase) {

       Date date = dateOfCase;

       LawCase lawCase = new LawCase(lawyerId, caseId, date);

        try{
            boolean isSaved = LawyerRepo.saveLawcase(lawCase);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "LawCase saved!").show();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colLawyerId.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
        colCaseId.setCellValueFactory(new PropertyValueFactory<>("CaseId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

        setCellValueFactory();
        loadAllLawyers();
        loadAssignedCases();
        keyEventsHandling();

        Validations();
        addTextChangeListener(txtLawyerId);
        addTextChangeListener(txtName);
        addTextChangeListener(txtAddress);
        addTextChangeListener(txtContact);
        addTextChangeListener(txtEmail);
        addTextChangeListener(txtYrsOfExp);
    }

    private void Validations() {
        txtLawyerId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtLawyerId.getText().isEmpty() && !event.getCharacter().equals("L")){
                event.consume();
            }
        });

    }

    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtLawyerId && !newValue.matches("^L.*$")) {
            }

            if (textField == txtName) {
                if (!newValue.isEmpty()) {
                    if (!Character.isUpperCase(newValue.charAt(0))) {
                        textField.setText(oldValue != null ? oldValue : "");
                    } else {
                        String correctedValue = Character.toUpperCase(newValue.charAt(0)) + newValue.substring(1);
                        if (!newValue.equals(correctedValue)) {
                            textField.setText(correctedValue);
                        }
                    }
                }
            }

            if (textField == txtAddress) {
                if (!newValue.isEmpty()) {
                    if (!Character.isUpperCase(newValue.charAt(0))) {
                        textField.setText(oldValue != null ? oldValue : "");
                    } else {
                        String correctedValue = Character.toUpperCase(newValue.charAt(0)) + newValue.substring(1);
                        if (!newValue.equals(correctedValue)) {
                            textField.setText(correctedValue);
                        }
                    }
                }
            }

            if (textField == txtContact) {
                // Check if the new value contains only digits and has a length of 10
                if (!newValue.matches("\\d{0,10}")) {
                    // If it contains non-digits or its length is not 10, prevent typing and display an error message
                    textField.setText(oldValue != null ? oldValue : "");
                    JOptionPane.showMessageDialog(null, "Please enter 10 integer numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }




            if (textField == txtEmail && !newValue.matches("^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$")) {
            }

            if (textField == txtYrsOfExp) {
                // Check if the new value contains only digits and has a length of 10
                if (!newValue.matches("\\d{0,10}")) {
                    // If it contains non-digits or its length is not 10, prevent typing and display an error message
                    textField.setText(oldValue != null ? oldValue : "");
                    JOptionPane.showMessageDialog(null, "Only numbers are allowed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void keyEventsHandling() {
        txtLawyerId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus();
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtYrsOfExp.requestFocus();
            }
        });

        txtYrsOfExp.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAddress.requestFocus();
            }
        });

        txtAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtEmail.requestFocus();
            }
        });

        txtEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtContact.requestFocus();
            }
        });
    }

    private void setCellValueFactory() {
        colLawyerID.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
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
                        lawyer.getLawyerId(),
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

    private void loadAssignedCases() {
        ObservableList<LawCaseTm> obList = FXCollections.observableArrayList();

        try{
            List<LawCase> lawCaseList = LawyerRepo.assignedCases();
            for (LawCase lawCase : lawCaseList) {
                LawCaseTm tm = new LawCaseTm(
                        lawCase.getLawyerId(),
                        lawCase.getCaseId(),
                        lawCase.getDate()
                );
                obList.add(tm);
            }
            tblAssignedWork.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction (ActionEvent event) {
        String lawyerId = txtLawyerId.getText();
        String name = txtName.getText();
        String yearsOfExp = txtYrsOfExp.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contactNo = txtContact.getText();

        if (lawyerId.isEmpty() || name.isEmpty() || yearsOfExp.isEmpty() || address.isEmpty() || email.isEmpty() || contactNo.isEmpty()) {
            if (lawyerId.isEmpty()) {
                txtLawyerId.requestFocus();
                txtLawyerId.setStyle("-fx-border-color: red;");
            } else if (name.isEmpty()) {
                txtName.requestFocus();
                txtName.setStyle("-fx-border-color: red;");
            } else if (yearsOfExp.isEmpty()) {
                txtYrsOfExp.requestFocus();
                txtYrsOfExp.setStyle("-fx-border-color: red;");
            } else if (address.isEmpty()) {
                txtAddress.requestFocus();
                txtAddress.setStyle("-fx-border-color: red;");
            } else if (email.isEmpty()) {
                txtEmail.requestFocus();
                txtEmail.setStyle("-fx-border-color: red;");
            } else {
                txtContact.requestFocus();
                txtContact.setStyle("-fx-border-color: red;");
            }
            return;
        }

        int yrsOfExp = Integer.parseInt(yearsOfExp);
        int contact = Integer.parseInt(contactNo);

        Lawyer lawyer = new Lawyer(lawyerId, name, yrsOfExp, address, email, contact);

        try {
            boolean isSaved = LawyerRepo.save(lawyer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Lawyer saved!").show();
                loadAllLawyers();
                clearFields();
                loadAssignedCases();
                txtLawyerId.requestFocus();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
                loadAllLawyers();
                loadAssignedCases();
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
                loadAllLawyers();
                clearFields();
                loadAssignedCases();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void TableOnClick(MouseEvent mouseEvent) {
        LawyerTm lawyerTm  = tblLawyer.getSelectionModel().getSelectedItem();
        txtLawyerId.setText(lawyerTm.getLawyerId());
        txtName.setText(lawyerTm.getName());
        txtYrsOfExp.setText(String.valueOf(lawyerTm.getYrsOfExp()));
        txtAddress.setText(lawyerTm.getAddress());
        txtEmail.setText(lawyerTm.getEmail());
        txtContact.setText(String.valueOf(lawyerTm.getContact()));
    }
}
