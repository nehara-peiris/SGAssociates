package lk.ijse.SGA.controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.Summon;
import lk.ijse.SGA.model.tm.SummonTm;
import lk.ijse.SGA.repository.SummonRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class SummonFormController implements Initializable {

    public JFXButton btnSave;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField txtSummonId;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtDefendant;
    @FXML
    private TextField txtLawyerId;
    @FXML
    private TextField txtDate;
    @FXML
    private TableView<SummonTm> tblSummon;
    @FXML
    private TableColumn<?, ?> colSummonId;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colDefendant;
    @FXML
    private TableColumn<?, ?> colLawyerId;
    @FXML
    private TableColumn<?, ?> colDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        setCellValueFactory();
        loadAllSummons();
        keyEventsHandling();

        Validations();
        addTextChangeListener(txtSummonId);
        addTextChangeListener(txtDescription);
        addTextChangeListener(txtDefendant);
        addTextChangeListener(txtLawyerId);
        addTextChangeListener(txtDate);

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    private void keyEventsHandling() {
        txtSummonId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDescription.requestFocus();
            }
        });

        txtDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDefendant.requestFocus();
            }
        });

        txtDefendant.setOnKeyPressed(event -> {
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

    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtSummonId && !newValue.matches("^S.*$")) {
            }

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

            if (textField == txtDefendant) {
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

            if (textField == txtLawyerId && !newValue.matches("^L.*$")) {
            }

            if (textField == txtDate && !newValue.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            }
        });
    }

    private void Validations() {
        txtSummonId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtSummonId.getText().isEmpty() && !event.getCharacter().equals("S")){
                event.consume();
            }
        });

        txtLawyerId.addEventFilter(KeyEvent.KEY_TYPED, event ->{
            if (txtLawyerId.getText().isEmpty() && !event.getCharacter().equals("L")){
                event.consume();
            }
        });
    }

    private void setCellValueFactory() {
        colSummonId.setCellValueFactory(new PropertyValueFactory<>("summonId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDefendant.setCellValueFactory(new PropertyValueFactory<>("defendant"));
        colLawyerId.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadAllSummons() {
        ObservableList<SummonTm> obList = FXCollections.observableArrayList();

        try{
            List<Summon> summonList = SummonRepo.getAll();
            for (Summon summon : summonList) {
                SummonTm tm = new SummonTm(
                        summon.getSummonId(),
                        summon.getDescription(),
                        summon.getDefendant(),
                        summon.getLawyerId(),
                        summon.getDate()
                );

                obList.add(tm);
            }

            tblSummon.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction (ActionEvent event) {
        String summonId = txtSummonId.getText();
        String description = txtDescription.getText();
        String defendant = txtDefendant.getText();
        String lawyerId = txtLawyerId.getText();
        String sDate = txtDate.getText();

        if (summonId.isEmpty() || description.isEmpty() || defendant.isEmpty() || lawyerId.isEmpty() || sDate.isEmpty()) {
            if (summonId.isEmpty()) {
                txtSummonId.requestFocus();
                txtSummonId.setStyle("-fx-border-color: red;");
            } else if (description.isEmpty()) {
                txtDescription.requestFocus();
                txtDescription.setStyle("-fx-border-color: red;");
            } else if (defendant.isEmpty()) {
                txtDefendant.requestFocus();
                txtDefendant.setStyle("-fx-border-color: red;");
            } else if (lawyerId.isEmpty()) {
                txtLawyerId.requestFocus();
                txtLawyerId.setStyle("-fx-border-color: red;");
            } else {
                txtDate.requestFocus();
                txtDate.setStyle("-fx-border-color: red;");
            }
            return;
        }

        Date date = Date.valueOf(sDate);

        Summon summon = new Summon(summonId, description, defendant, lawyerId, date);

        try{
            boolean isSaved = SummonRepo.save(summon);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "summon saved!").show();
                clearFields();
                setDate();
                loadAllSummons();
                txtSummonId.requestFocus();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtSummonId.clear();
        txtDescription.clear();
        txtDefendant.clear();
        txtLawyerId.clear();
        txtDate.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event){
        String summonId = txtSummonId.getText();
        String description = txtDescription.getText();
        String defendant = txtDefendant.getText();
        String lawyerId = txtLawyerId.getText();
        Date date = Date.valueOf(txtDate.getText());

        Summon summon = new Summon(summonId, description, defendant, lawyerId, date);

        try{
            boolean isUpdated = SummonRepo.update(summon);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "summon updated successfully!").show();
                clearFields();
                loadAllSummons();
                setDate();
                txtSummonId.requestFocus();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtSummonId.getText();

        try {
            boolean isDeleted = SummonRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "summon deleted!").show();
                loadAllSummons();
                clearFields();
                setDate();
                txtSummonId.requestFocus();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void TableOnClick(MouseEvent mouseEvent) {
        SummonTm summonTm = tblSummon.getSelectionModel().getSelectedItem();
        txtSummonId.setText(summonTm.getSummonId());
        txtDescription.setText(summonTm.getDescription());
        txtDefendant.setText(String.valueOf(summonTm.getDefendant()));
        txtDate.setText(String.valueOf(summonTm.getDate()));
        txtLawyerId.setText(String.valueOf(summonTm.getLawyerId()));
    }
}
