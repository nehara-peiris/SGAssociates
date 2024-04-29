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
import javafx.scene.layout.AnchorPane;
import lk.ijse.SGA.model.Deed;
import lk.ijse.SGA.model.tm.DeedTm;
import lk.ijse.SGA.repository.ClientRepo;
import lk.ijse.SGA.repository.DeedRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DeedFormController implements Initializable {

    @FXML
    private AnchorPane rootNode;
    @FXML
    private TableView<DeedTm> tblDeed;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllDeeds();

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
    }


    private void setCellValueFactory() {
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
        Date date = Date.valueOf(txtDate.getText());
        String lawyerId = txtLawyerId.getText();
        String clientId = txtClientId.getText();

        Deed deed = new Deed(deedId, description, type, date, lawyerId, clientId);

        try{
            boolean isSaved = DeedRepo.save(deed);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Deed saved successfully!").show();
                clearFields();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
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
        String deedId = txtDeedId.getText();
        String description = txtDescription.getText();
        String type = txtType.getText();
        Date date = Date.valueOf(txtDate.getText());
        String lawyerId = txtLawyerId.getText();
        String clientId = txtClientId.getText();

        Deed deed = new Deed(deedId, description, type, date, lawyerId, clientId);

        try{
            boolean isUpdated = DeedRepo.update(deed);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Deed updated successfully!").show();
                clearFields();
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
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtDeedId.getText();

        Deed deed = DeedRepo.searchById(id);
        if (deed != null) {
            txtDeedId.setText(deed.getDeedId());
            txtDescription.setText(deed.getDescription());
            txtType.setText(deed.getType());
            txtDate.setText(String.valueOf(deed.getDate()));
            txtClientId.setText(deed.getClientId());
            txtLawyerId.setText(deed.getLawyerId());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Client not found!").show();
        }
    }
}

