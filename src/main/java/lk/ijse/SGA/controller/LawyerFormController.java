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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.LawCase;
import lk.ijse.SGA.model.Lawyer;
import lk.ijse.SGA.model.tm.LawCaseTm;
import lk.ijse.SGA.model.tm.LawyerTm;
import lk.ijse.SGA.repository.LawyerRepo;

import java.net.URL;
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
    private TableColumn<?, ?> colCaseId;
    @FXML
    private TableColumn<?, ?> colDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        /*
        txtName.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (txtName.getText().isEmpty() && Character.isUpperCase(event.getCharacter().charAt(0))) {
                event.consume();
            }
        });



        txtAddress.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtAddress.getText().isEmpty() && Character.isUpperCase(event.getCharacter().charAt(0))) {
                event.consume();
            }
        });

        txtContact.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (txtContact.getText().length() <= 10 || !Character.isDigit(event.getCharacter().charAt(0))) {
                event.consume();
            }
        });

        txtEmail.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (txtEmail.getText().isEmpty() && !Character.isLowerCase(event.getCharacter().charAt(0)) && !event.getCharacter().equals(".")) {
                event.consume();
            }
        });

        txtYrsOfExp.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (txtYrsOfExp.getText().isEmpty() && !Character.isDigit(event.getCharacter().charAt(0))) {
                event.consume();
            }
        });

         */

    }

    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtLawyerId && !newValue.matches("^L.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with L").show();
            }

            if (textField == txtName && !newValue.matches("[A-z].*$")) {
                new Alert(Alert.AlertType.ERROR ,"Name should start with Upper case").show();
            }

            if (textField == txtAddress && !newValue.matches("[A-z].*$")) {
            }

            if (textField == txtContact && !newValue.matches("^([0])([1-9]{2})([0-9]){7}$")) {
            }

            if (textField == txtEmail && !newValue.matches("^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$")) {
            }

            if (textField == txtYrsOfExp && !newValue.matches("^\\d{10}$")) {
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
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colYrsOfExp.setCellValueFactory(new PropertyValueFactory<>("yrsOfExp"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        colLawyerId.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
        colCaseId.setCellValueFactory(new PropertyValueFactory<>("CaseId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

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
        int yrsOfExp = Integer.parseInt(txtYrsOfExp.getText());
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        int contact = Integer.parseInt(txtContact.getText());

        Lawyer lawyer = new Lawyer(lawyerId, name, yrsOfExp, address, email, contact);

        try{
            boolean isSaved = LawyerRepo.save(lawyer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Lawyer saved!").show();
                loadAllLawyers();
                loadAssignedCases();
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

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String contact = txtContact.getText();

        Lawyer lawyer = LawyerRepo.searchById(contact);
        if (lawyer != null) {
            txtLawyerId.setText(lawyer.getLawyerId());
            txtName.setText(lawyer.getName());
            txtYrsOfExp.setText(String.valueOf(lawyer.getYrsOfExp()));
            txtAddress.setText(lawyer.getAddress());
            txtEmail.setText(lawyer.getEmail());
            txtContact.setText(String.valueOf(lawyer.getContact()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Lawyer not found!").show();
        }

    }

}
