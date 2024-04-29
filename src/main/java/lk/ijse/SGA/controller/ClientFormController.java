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
import lk.ijse.SGA.model.Client;
import lk.ijse.SGA.model.tm.ClientTm;
import lk.ijse.SGA.repository.ClientRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    @FXML
    private TextField txtClientId;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtLawyerId;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colAddress;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colContact;
    @FXML
    private TableColumn<?, ?> colLawyerId;
    @FXML
    private TableView<ClientTm> tblClient;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllClients();

        txtClientId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus();
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAddress.requestFocus();
            }
        });

        txtAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtContact.requestFocus();
            }
        });

        txtContact.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtEmail.requestFocus();
            }
        });

        txtEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtLawyerId.requestFocus();
            }
        });
    }

    private void setCellValueFactory() {
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colLawyerId.setCellValueFactory(new PropertyValueFactory<>("Lawyer ID"));
    }

    private void loadAllClients() {
       ObservableList<ClientTm> obList = FXCollections.observableArrayList();

        try{
            List<Client> clientList = ClientRepo.getAll();
            for (Client client : clientList){
                ClientTm tm = new ClientTm(
                        client.getName(),
                        client.getAddress(),
                        client.getEmail(),
                        client.getContact(),
                        client.getLawyerId()
                );
                obList.add(tm);
            }
            tblClient.setItems(obList);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSaveOnAction (ActionEvent event)  {
        String clientId = txtClientId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        int contact = Integer.parseInt(txtContact.getText());
        String lawyerId = txtLawyerId.getText();

        Client client = new Client(clientId, name, address, email, contact, lawyerId);

        try{
            boolean isSaved = ClientRepo.save(client);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Client saved successfully!").show();
                clearFields();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtClientId.clear();
        txtName.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtContact.clear();
        txtLawyerId.clear();
    }


    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtClientId.getText();

        Client client = ClientRepo.searchById(id);
        if (client != null) {
            txtClientId.setText(client.getClientId());
            txtName.setText(client.getName());
            txtAddress.setText(client.getAddress());
            txtEmail.setText(client.getEmail());
            txtContact.setText(String.valueOf(client.getContact()));
            txtLawyerId.setText(String.valueOf(client.getLawyerId()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Client not found!").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event){
        String clientId = txtClientId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        int contact = Integer.parseInt(txtContact.getText());
        String lawyerId = txtLawyerId.getText();

        Client client = new Client(clientId, name, address, email, contact, lawyerId);

        try{
            boolean isUpdated = ClientRepo.update(client);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Client updated successfully!").show();
                clearFields();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtClientId.getText();

        try {
            boolean isDeleted = ClientRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Client deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
