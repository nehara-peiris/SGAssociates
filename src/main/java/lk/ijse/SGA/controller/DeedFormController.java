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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.Deed;
import lk.ijse.SGA.model.tm.DeedTm;
import lk.ijse.SGA.repository.ClientRepo;
import lk.ijse.SGA.repository.DeedRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DeedFormController implements Initializable {

    public JFXButton btnSave;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TableView<DeedTm> tblDeed;
    @FXML
    private TableColumn<?,?> colDeedId;
    @FXML
    private TableColumn<?,?> colClientId;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colType;
    @FXML
    private TableColumn<?, ?> colLawyerId;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TextField txtDeedId;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtLawyerId;
    @FXML
    private TextField txtClientId;
    @FXML
    private TextField txtDate;
    @FXML
    private BarChart<String, Number> chartDeeds;
    @FXML
    private CategoryAxis axisDeeds;
    @FXML
    private NumberAxis axisNoOfDeeds;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllDeeds();
        setDate();

        keyEventsHandling();
        setDeedBarchart();

        Validations();
        addTextChangeListener(txtDescription);
        addTextChangeListener(txtType);

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    private void Validations() {
        txtDeedId.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[D0-9]")) {
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

    }

    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

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
        });
    }

    private void keyEventsHandling() {
        txtDeedId.setOnKeyPressed(event -> {
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
                txtLawyerId.requestFocus();
            }
        });

        txtLawyerId.setOnKeyPressed(event -> {
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

    private void setDeedBarchart() {
        axisDeeds.setLabel("Deed Type");
        axisNoOfDeeds.setLabel("Number of Deeds");

        try {
            populateBarChart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateBarChart() throws SQLException {
        chartDeeds.getData().clear();

        Map<String, Integer> deedTypeCounts = DeedRepo.getAllToChart();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        deedTypeCounts.forEach((type, count) -> {
            XYChart.Data<String, Number> data = new XYChart.Data<>(type, count);
            series.getData().add(data);
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: #1c7850;");
                }
            });
        });

        chartDeeds.getData().add(series);
    }

    private void setCellValueFactory() {
        colDeedId.setCellValueFactory(new PropertyValueFactory<>("deedId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colLawyerId.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
    }

    private void loadAllDeeds() {
        ObservableList<DeedTm> obList = FXCollections.observableArrayList();

        try{
            List<Deed> deedList = DeedRepo.getAll();
            for (Deed deed : deedList) {
                DeedTm tm = new DeedTm(
                        deed.getDeedId(),
                        deed.getDescription(),
                        deed.getType(),
                        deed.getDate(),
                        deed.getLawyerId(),
                        deed.getClientId()
                );

                obList.add(tm);
            }

            tblDeed.setItems(obList);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction (ActionEvent event){
        String deedId = txtDeedId.getText();
        String description = txtDescription.getText();
        String type = txtType.getText();
        String dateOfDeed = txtDate.getText();
        String lawyerId = txtLawyerId.getText();
        String clientId = txtClientId.getText();

        if (deedId.isEmpty() || clientId.isEmpty() || description.isEmpty() || type.isEmpty() || dateOfDeed.isEmpty() || lawyerId.isEmpty()) {
            if (deedId.isEmpty()) {
                txtDeedId.requestFocus();
                txtDeedId.setStyle("-fx-border-color: red;");
            } else if (clientId.isEmpty()) {
                txtClientId.requestFocus();
                txtClientId.setStyle("-fx-border-color: red;");
            } else if (description.isEmpty()) {
                txtDescription.requestFocus();
                txtDescription.setStyle("-fx-border-color: red;");
            } else if (type.isEmpty()) {
                txtType.requestFocus();
                txtType.setStyle("-fx-border-color: red;");
            } else if (dateOfDeed.isEmpty()) {
                txtDate.requestFocus();
                txtDate.setStyle("-fx-border-color: red;");
            } else {
                txtLawyerId.requestFocus();
                txtLawyerId.setStyle("-fx-border-color: red;");
            }
            return;
        }

        Date date = Date.valueOf(dateOfDeed);

        Deed deed = new Deed(deedId, description, type, date, lawyerId, clientId);

        try{
            boolean isSaved = DeedRepo.save(deed);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Deed saved successfully!").show();
                clearFields();
                setDate();
                loadAllDeeds();
                txtDeedId.requestFocus();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtDeedId.clear();
        txtDescription.clear();
        txtType.clear();
        txtDate.clear();
        txtClientId.clear();
        txtLawyerId.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event){
        String deedId = txtDeedId.getText();
        String description = txtDescription.getText();
        String type = txtType.getText();
        String dateOfDeed = txtDate.getText();
        String lawyerId = txtLawyerId.getText();
        String clientId = txtClientId.getText();

        if (deedId.isEmpty() || clientId.isEmpty() || description.isEmpty() || type.isEmpty() || dateOfDeed.isEmpty() || lawyerId.isEmpty()) {
            if (deedId.isEmpty()) {
                txtDeedId.requestFocus();
                txtDeedId.setStyle("-fx-border-color: red;");
            } else if (clientId.isEmpty()) {
                txtClientId.requestFocus();
                txtClientId.setStyle("-fx-border-color: red;");
            } else if (description.isEmpty()) {
                txtDescription.requestFocus();
                txtDescription.setStyle("-fx-border-color: red;");
            } else if (type.isEmpty()) {
                txtType.requestFocus();
                txtType.setStyle("-fx-border-color: red;");
            } else if (dateOfDeed.isEmpty()) {
                txtDate.requestFocus();
                txtDate.setStyle("-fx-border-color: red;");
            } else {
                txtLawyerId.requestFocus();
                txtLawyerId.setStyle("-fx-border-color: red;");
            }
            return;
        }

        Date date = Date.valueOf(dateOfDeed);

        Deed deed = new Deed(deedId, description, type, date, lawyerId, clientId);

        try{
            boolean isUpdated = DeedRepo.update(deed);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Deed updated successfully!").show();
                loadAllDeeds();
                clearFields();
                setDate();
                txtDeedId.requestFocus();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtDeedId.getText();

        try {
            boolean isDeleted = ClientRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deed deleted!").show();
                loadAllDeeds();
                clearFields();
                setDate();
                txtDeedId.requestFocus();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void TableOnClick(MouseEvent mouseEvent) {
        DeedTm deedTm = tblDeed.getSelectionModel().getSelectedItem();
        txtDeedId.setText(deedTm.getDeedId());
        txtDescription.setText(deedTm.getDescription());
        txtType.setText(deedTm.getType());
        txtLawyerId.setText(deedTm.getLawyerId());
        txtDate.setText(String.valueOf(deedTm.getDate()));
        txtClientId.setText(deedTm.getClientId());
    }
}

