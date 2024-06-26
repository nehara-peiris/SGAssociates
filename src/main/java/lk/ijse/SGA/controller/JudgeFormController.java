package lk.ijse.SGA.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.Judge;
import lk.ijse.SGA.model.tm.JudgeTm;
import lk.ijse.SGA.repository.JudgeRepo;

import javax.swing.*;
import java.util.List;

public class JudgeFormController {
    public JFXButton btnSave;
    @FXML
    private TextField txtJudgeId;
    @FXML
    private TextField txtCourtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtYrsOfExp;
    public TableView<JudgeTm> tblJudge;
    @FXML
    private TableColumn<?, ?> colJudgeId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colCourtId;
    @FXML
    private TableColumn<?, ?> colYrsOfExp;
    @FXML
    private AnchorPane rootNode;

   public void initialize(){
        setCellValueFactory();
        loadAllJudges();

       keyEventsHandling();

       Validations();
       addTextChangeListener(txtName);
       addTextChangeListener(txtCourtId);
       addTextChangeListener(txtYrsOfExp);

   }

    private void keyEventsHandling() {
        txtJudgeId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus();
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCourtId.requestFocus();
            }
        });

        txtCourtId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtYrsOfExp.requestFocus();
            }
        });

        txtYrsOfExp.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSave.requestFocus();
            }
        });

    }

    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

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

            if (textField == txtCourtId && !newValue.matches("^CT.*$")) {
            }

            if (textField == txtYrsOfExp) {
                if (!newValue.matches("\\d{0,2}")) {
                    textField.setText(oldValue != null ? oldValue : "");
                    JOptionPane.showMessageDialog(null, "Only 2 numbers are allowed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void Validations() {
        txtJudgeId.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[J0-9]")) {
                event.consume();
            }
        });

        txtCourtId.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[CT0-9]")) {
                event.consume();
            }
        });



    }

    private void setCellValueFactory() {
        colJudgeId.setCellValueFactory(new PropertyValueFactory<>("judgeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCourtId.setCellValueFactory(new PropertyValueFactory<>("courtId"));
        colYrsOfExp.setCellValueFactory(new PropertyValueFactory<>("yrsOfExp"));
    }

    private void loadAllJudges() {
        ObservableList<JudgeTm> obList = FXCollections.observableArrayList();

        try{
            List<Judge> judgeList = JudgeRepo.getAll();
            for (Judge judge : judgeList) {
                JudgeTm tm = new JudgeTm(
                        judge.getJudgeId(),
                        judge.getName(),
                        judge.getCourtId(),
                        judge.getYrsOfExp()
                );

                obList.add(tm);
            }

            tblJudge.setItems(obList);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction (ActionEvent event)  {
        String judgeId = txtJudgeId.getText();
        String name = txtName.getText();
        String courtId = txtCourtId.getText();
        String yearsOfExp = txtYrsOfExp.getText();

        if (judgeId.isEmpty() || name.isEmpty() || courtId.isEmpty() || yearsOfExp.isEmpty()) {
            if (judgeId.isEmpty()) {
                txtJudgeId.requestFocus();
                txtJudgeId.setStyle("-fx-border-color: red;");
            } else if (name.isEmpty()) {
                txtName.requestFocus();
                txtName.setStyle("-fx-border-color: red;");
            } else if (courtId.isEmpty()) {
                txtName.requestFocus();
                txtName.setStyle("-fx-border-color: red;");
            } else {
                txtYrsOfExp.requestFocus();
                txtYrsOfExp.setStyle("-fx-border-color: red;");
            }
            return;
        }

        int yrsOfExp = Integer.parseInt(yearsOfExp);

        Judge judge = new Judge(judgeId, name, courtId, yrsOfExp);

        try{
            boolean isSaved = JudgeRepo.save(judge);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "deed saved!").show();
                clearFields();
                loadAllJudges();
                txtJudgeId.requestFocus();
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtJudgeId.setText("");
        txtName.setText("");
        txtCourtId.setText("");
        txtYrsOfExp.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event){
        String judgeId = txtJudgeId.getText();
        String name = txtName.getText();
        String courtId = txtCourtId.getText();
        String yearsOfExp = txtYrsOfExp.getText();

        if (judgeId.isEmpty() || name.isEmpty() || courtId.isEmpty() || yearsOfExp.isEmpty()) {
            if (judgeId.isEmpty()) {
                txtJudgeId.requestFocus();
                txtJudgeId.setStyle("-fx-border-color: red;");
            } else if (name.isEmpty()) {
                txtName.requestFocus();
                txtName.setStyle("-fx-border-color: red;");
            } else if (courtId.isEmpty()) {
                txtName.requestFocus();
                txtName.setStyle("-fx-border-color: red;");
            } else {
                txtYrsOfExp.requestFocus();
                txtYrsOfExp.setStyle("-fx-border-color: red;");
            }
            return;
        }

        int yrsOfExp = Integer.parseInt(yearsOfExp);

        Judge judge = new Judge(judgeId, name, courtId, yrsOfExp);

        try{
            boolean isUpdated = JudgeRepo.update(judge);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Judge Updated!").show();
                clearFields();
                loadAllJudges();
                txtJudgeId.requestFocus();
            }
        }catch(Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtJudgeId.getText();

        try {
            boolean isDeleted = JudgeRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Judge deleted!").show();
                clearFields();
                txtJudgeId.requestFocus();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void TableOnClick(MouseEvent mouseEvent) {
        JudgeTm judgeTm = tblJudge.getSelectionModel().getSelectedItem();
        txtJudgeId.setText(judgeTm.getJudgeId());
        txtName.setText(judgeTm.getName());
        txtYrsOfExp.setText(String.valueOf(judgeTm.getYrsOfExp()));
        txtCourtId.setText(judgeTm.getCourtId());
    }
}
