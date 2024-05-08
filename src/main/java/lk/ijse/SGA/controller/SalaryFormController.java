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
import lk.ijse.SGA.model.Salary;
import lk.ijse.SGA.model.TotalSalary;
import lk.ijse.SGA.model.tm.SalaryTm;
import lk.ijse.SGA.model.tm.TotalSalaryTm;
import lk.ijse.SGA.repository.LawyerRepo;
import lk.ijse.SGA.repository.SalaryRepo;
import lk.ijse.SGA.repository.TotalSalaryRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
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
    private TableView<SalaryTm> tblSalary;
    @FXML
    private TableColumn<?, ?> colSalaryId;
    @FXML
    private TableColumn<?, ?> colLawyerId;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colAmount;
    @FXML
    private TableColumn<?, ?> colBonus;
    @FXML
    private TableView<TotalSalaryTm>  tblTotSalary;
    @FXML
    private TableColumn<?, ?>  colLawyerId2;
    @FXML
    private TableColumn<?, ?>  colDate2;
    @FXML
    private TableColumn<?, ?>  colTotalSalary2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        getCurrentSalaryId();
        getLawyerIds();
        getChargeCodes();
        setCellValueFactory();

        loadAllSalary();
        loadTotalSalary();

        keyEventsHandling();

        Validations();
        addTextChangeListener(txtSalaryId);

    }

    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtSalaryId && !newValue.matches("^SA.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with SA").show();
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
        txtSalaryId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtSalaryId.getText().isEmpty() && !event.getCharacter().equals("L")){
                event.consume();
            }
        });
    }
    private void keyEventsHandling() {
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

            txtBonus.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    btnSaveOnAction(null);
                }
            });
    }

    private void getChargeCodes() {

    }

    private void getLawyerIds() {

    }

    private void getCurrentSalaryId() {
        try {
            String currentId = SalaryRepo.getCurrentId();

            String nextSalaryId = generateNextSalaryId(currentId);
            txtSalaryId.setText(nextSalaryId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextSalaryId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("SA");
            int idNum = Integer.parseInt(split[1]);
            return "SA" + ++idNum;
        }
        return "SA1";
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    private void setCellValueFactory() {
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryID"));
        colLawyerId.setCellValueFactory(new PropertyValueFactory<>("lawyerID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colBonus.setCellValueFactory(new PropertyValueFactory<>("bonus"));


        colLawyerId2.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
        colDate2.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotalSalary2.setCellValueFactory(new PropertyValueFactory<>("totSalary"));
    }

    private void loadAllSalary() {
     ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

        try{
            List<Salary> salaryList = SalaryRepo.getAll();
            for (Salary salary : salaryList) {
                SalaryTm tm = new SalaryTm(
                        salary.getSalaryId(),
                        salary.getLawyerId(),
                        salary.getDate(),
                        salary.getAmount(),
                        salary.getBonus()
                );
                obList.add(tm);
            }
            tblSalary.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void loadTotalSalary() {
        ObservableList<TotalSalaryTm> obList = FXCollections.observableArrayList();

        try{
            List<Salary> salaryList = SalaryRepo.getAll();
            for (Salary salary : salaryList) {
                TotalSalaryTm tm = new TotalSalaryTm(
                        salary.getLawyerId(),
                        salary.getDate(),
                        salary.getBonus()+salary.getAmount()
                );
                obList.add(tm);
            }

            //System.out.println(obList);

            tblTotSalary.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction (ActionEvent event)  {
        String salaryId = txtSalaryId.getText();
        String lawyerId = txtLawyerID.getText();
        Date date = Date.valueOf(txtDate.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        double bonus = Double.parseDouble(txtBonus.getText());

        Salary salary = new Salary(salaryId, lawyerId, date, amount, bonus);

        try{
            boolean isSaved = SalaryRepo.save(salary);
            if (isSaved){
                loadAllSalary();
                new Alert(Alert.AlertType.CONFIRMATION, "Salary saved successfully!").show();
                clearFields();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtSalaryId.clear();
        txtLawyerID.clear();
        txtDate.clear();
        txtAmount.clear();
        txtBonus.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event){
        String salaryId = txtSalaryId.getText();
        String lawyerId = txtLawyerID.getText();
        Date date = Date.valueOf(txtDate.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        double bonus = Double.parseDouble(txtBonus.getText());

        Salary salary = new Salary(salaryId, lawyerId, date, amount, bonus);

        try{
            boolean isSaved = SalaryRepo.update(salary);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Salary updated successfully!").show();
                clearFields();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String id = txtSalaryId.getText();

        try {
            boolean isDeleted = SalaryRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "salary deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
