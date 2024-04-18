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
import lk.ijse.SGA.model.Deed;
import lk.ijse.SGA.model.tm.DeedTm;
import lk.ijse.SGA.repository.DeedRepo;

import java.sql.SQLException;
import java.util.List;

public class DeedFormController {
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TableView<DeedTm> tblDeed;
    @FXML
    private TableColumn<?,?> colClientId;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colLawyerId;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TextField txtDeedId;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtLawyerId;
    @FXML
    private TextField txtClientId;
    @FXML
    private TextField txtDate;


    public void initialize(){
        setCellValueFactory();
        loadAllDeeds();
    }

    private void setCellValueFactory() {
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLawyerId.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    private void loadAllDeeds() {
        ObservableList<DeedTm> obList = FXCollections.observableArrayList();

        List<Deed> deedList = DeedRepo.getAll();
        for (Deed deed : deedList) {
            DeedTm tm = new DeedTm(
                    deed.getClientId(),
                    deed.getDescription(),
                    deed.getDate(),
                    deed.getLawyerId()
            );

            obList.add(tm);
        }

        tblDeed.setItems(obList);
    }

    @FXML
    void btnSaveOnAction (ActionEvent event) throws SQLException {
        String deedId = txtDeedId.getText();
        String clientId = txtClientId.getText();
        String description = txtDescription.getText();
        String date = txtDate.getText();
        String lawyerId = txtLawyerId.getText();

        Deed deed = new Deed(deedId, description, date, lawyerId, clientId);

        boolean isSaved = DeedRepo.save(deed);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "deed saved!").show();
            clearFields();
        }
    }

    private void clearFields() {
        txtDeedId.setText("");
        txtDescription.setText("");
        txtDate.setText("");
        txtClientId.setText("");
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
