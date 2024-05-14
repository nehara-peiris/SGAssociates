package lk.ijse.SGA.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.Cases;
import lk.ijse.SGA.model.tm.CasesTm;
import lk.ijse.SGA.repository.CasesRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CasesFormController implements Initializable {
    @FXML
    private TextField txtLawyerId;
    @FXML
    private TextField txtCaseId;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtClientId;
    @FXML
    private TextField txtDate;
    @FXML
    private TableView<CasesTm> tblCase;
    @FXML
    private TableColumn<?,?> colClientId;
    @FXML
    private TableColumn<?,?> colDescription;
    @FXML
    private TableColumn<?,?> colDate;
    @FXML
    private TableColumn<?,?> colType;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private BarChart<String, Number> chartCase;
    @FXML
    private CategoryAxis axisCases;
    @FXML
    private NumberAxis axisNoOfCases;
    public JFXButton btnSave;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllCases();

        keyEventsHandling();
        setCasesBarchart();

        Validations();
        addTextChangeListener(txtCaseId);
        addTextChangeListener(txtDescription);
        addTextChangeListener(txtType);
        addTextChangeListener(txtDate);
        addTextChangeListener(txtClientId);
        addTextChangeListener(txtLawyerId);

    }

    private void keyEventsHandling() {
        txtCaseId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtClientId.requestFocus();
            }
        });

        txtClientId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDescription.requestFocus();
            }
        });

        txtDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtType.requestFocus();
            }
        });

        txtType.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDate.requestFocus();
            }
        });

        txtDate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSave.requestFocus();
            }
        });

    }

    private void setCasesBarchart() {
        axisCases.setLabel("Case Type");
        axisNoOfCases.setLabel("Number of Cases");

        try {
            populateBarChart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

           /* if (textField == txtCaseId && !newValue.matches("^CA.*$")) {
            }
            */
            if (textField == txtDescription) {
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

            if (textField == txtType) {
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

            if (textField == txtDate && !newValue.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            }

            if (textField == txtClientId && !newValue.matches("^C.*$")) {
                new Alert(Alert.AlertType.ERROR ,"Start with C").show();
            }

            if (textField == txtLawyerId && !newValue.matches("^L.*$")) {
            }

        });
    }

    private void Validations() {
        txtLawyerId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtLawyerId.getText().isEmpty() && !event.getCharacter().equals("L")){
                event.consume();
            }
        });

    }

    private void populateBarChart() throws SQLException {
        chartCase.getData().clear();

        Map<String, Integer> caseTypeCounts =CasesRepo.getAllToChart();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        caseTypeCounts.forEach((type, count) -> {
            series.getData().add(new XYChart.Data<>(type, count));
        });

        chartCase.getData().add(series);
    }

    private void setCellValueFactory() {
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

    }

    private void loadAllCases() {
        ObservableList<CasesTm> obList = FXCollections.observableArrayList();

        try{
            List<Cases> casesList = CasesRepo.getAll();
            for (Cases cases : casesList) {
                CasesTm tm = new CasesTm(
                        cases.getDescription(),
                        cases.getDate(),
                        cases.getType(),
                        cases.getClientId()
                );

                obList.add(tm);
            }

            tblCase.setItems(obList);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String caseId = txtCaseId.getText();
        String clientId = txtClientId.getText();
        String description = txtDescription.getText();
        String type = txtType.getText();
        Date date = Date.valueOf(txtDate.getText());
        String lawyerId = txtLawyerId.getText();

        Cases cases = new Cases(caseId, description, type, date, clientId);

        try{
            boolean isSaved = CasesRepo.save(cases);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "case saved!").show();
                LawyerFormController.lawCaseUpdate(caseId, lawyerId, date);
                loadAllCases();
                clearFields();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtCaseId.setText("");
        txtDescription.setText("");
        txtDate.setText("");
        txtClientId.setText("");
        txtType.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String caseId = txtCaseId.getText();
        String clientId = txtClientId.getText();
        String description = txtDescription.getText();
        String type = txtType.getText();
        Date date = Date.valueOf(txtDate.getText());

        Cases cases = new Cases(caseId, description, type, date, clientId);

        try{
            boolean isUpdated = CasesRepo.update(cases);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "case updated!").show();
                clearFields();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCaseId.getText();

        try {
            boolean isDeleted = CasesRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Case deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
