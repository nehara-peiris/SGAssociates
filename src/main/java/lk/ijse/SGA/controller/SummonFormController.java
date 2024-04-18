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
import lk.ijse.SGA.model.Summon;
import lk.ijse.SGA.model.tm.SummonTm;
import lk.ijse.SGA.repository.SummonRepo;

import java.sql.SQLException;
import java.util.List;

public class SummonFormController {

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
    private TableView<SummonTm> tblSummon;
    @FXML
    private TableColumn<?, ?> colSummonId;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colDefendant;
    @FXML
    private TableColumn<?, ?> colLawyerId;

    public void initialize(){
        setCellValueFactory();
        loadAllSummons();
    }

    private void setCellValueFactory() {
        colSummonId.setCellValueFactory(new PropertyValueFactory<>("summonId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDefendant.setCellValueFactory(new PropertyValueFactory<>("defendant"));
        colLawyerId.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
    }

    private void loadAllSummons() {
        ObservableList<SummonTm> obList = FXCollections.observableArrayList();

        List<Summon> summonList = SummonRepo.getAll();
        for (Summon summon : summonList) {
            SummonTm tm = new SummonTm(
                    summon.getSummonId(),
                    summon.getDescription(),
                    summon.getDefendant(),
                    summon.getLawyerId()
            );

            obList.add(tm);
        }

        tblSummon.setItems(obList);
    }

    @FXML
    void btnSaveOnAction (ActionEvent event) throws SQLException {
        String summonId = txtSummonId.getText();
        String description = txtDescription.getText();
        String defendant = txtDefendant.getText();
        String lawyerId = txtLawyerId.getText();

        Summon summon = new Summon(summonId, description, defendant, lawyerId);

        boolean isSaved = SummonRepo.save(summon);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "summon saved!").show();
            clearFields();
        }
    }

    private void clearFields() {
        txtSummonId.setText("");
        txtDescription.setText("");
        txtDefendant.setText("");
        txtLawyerId.setText("");
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
