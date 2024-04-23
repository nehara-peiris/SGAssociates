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
import lk.ijse.SGA.model.Court;
import lk.ijse.SGA.model.tm.CourtTm;
import lk.ijse.SGA.repository.CourtRepo;

import java.sql.SQLException;
import java.util.List;

public class CourtFormController {
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField txtCourtId;
    @FXML
    private TextField txtLocation;
    @FXML
    private TableView<CourtTm> tblCourt;
    @FXML
    private TableColumn<?, ?> colCourtId;
    @FXML
    private TableColumn<?, ?> colLocation;

    public void initialize(){
        setCellValueFactory();
        loadAllCourts();
    }

    private void setCellValueFactory() {
        colCourtId.setCellValueFactory(new PropertyValueFactory<>("courtId"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    private void loadAllCourts() {
        ObservableList<CourtTm> obList = FXCollections.observableArrayList();

        List<Court> courtList = CourtRepo.getAll();
        for (Court court : courtList) {
            CourtTm tm = new CourtTm(
                    court.getCourtId(),
                    court.getLocation()
            );

            obList.add(tm);
        }

        tblCourt.setItems(obList);
    }

    @FXML
    void btnSaveOnAction (ActionEvent event){
        String courtId = txtCourtId.getText();
        String location = txtLocation.getText();

        Court court = new Court(courtId, location);

        boolean isSaved = CourtRepo.save(court);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "court saved!").show();
            clearFields();
        }
    }

    private void clearFields() {
        txtCourtId.setText("");
        txtLocation.setText("");
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
