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
import lk.ijse.SGA.model.Judge;
import lk.ijse.SGA.model.tm.JudgeTm;
import lk.ijse.SGA.repository.JudgeRepo;

import java.sql.SQLException;
import java.util.List;

public class JudgeFormController {
    @FXML
    private TextField txtCourtId;
    @FXML
    private TextField txtJudgeId;
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
    }

    private void setCellValueFactory() {
        colJudgeId.setCellValueFactory(new PropertyValueFactory<>("judgeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCourtId.setCellValueFactory(new PropertyValueFactory<>("courtId"));
        colYrsOfExp.setCellValueFactory(new PropertyValueFactory<>("yrsOfExp"));
    }

    private void loadAllJudges() {
        ObservableList<JudgeTm> obList = FXCollections.observableArrayList();

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
    }

    @FXML
    void btnSaveOnAction (ActionEvent event) throws SQLException {
        String judgeId = txtJudgeId.getText();
        String name = txtName.getText();
        String courtId = txtCourtId.getText();
        String yrsOfExp = txtYrsOfExp.getText();

        Judge judge = new Judge(judgeId, name, courtId, yrsOfExp);

        boolean isSaved = JudgeRepo.save(judge);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "deed saved!").show();
            clearFields();
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

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }
}
