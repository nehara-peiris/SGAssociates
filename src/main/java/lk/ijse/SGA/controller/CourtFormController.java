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
import javafx.scene.input.KeyCode;
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


        txtCourtId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtLocation.requestFocus();
            }
        });

    }

    private void setCellValueFactory() {
        colCourtId.setCellValueFactory(new PropertyValueFactory<>("courtId"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    private void loadAllCourts() {
        ObservableList<CourtTm> obList = FXCollections.observableArrayList();

        try{
            List<Court> courtList = CourtRepo.getAll();
            for (Court court : courtList) {
                CourtTm tm = new CourtTm(
                        court.getCourtId(),
                        court.getLocation()
                );

                obList.add(tm);
            }

            tblCourt.setItems(obList);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction (ActionEvent event){
        String courtId = txtCourtId.getText();
        String location = txtLocation.getText();

        Court court = new Court(courtId, location);

        try{
            boolean isSaved = CourtRepo.save(court);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "court saved!").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtCourtId.setText("");
        txtLocation.setText("");
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event){
        String courtId = txtCourtId.getText();
        String location = txtLocation.getText();

        Court court = new Court(courtId, location);

        try{
            boolean isUpdated = CourtRepo.update(court);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "court updated!").show();
                clearFields();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCourtId.getText();

        try {
            boolean isDeleted = CourtRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Court deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
