package lk.ijse.SGA.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.SGA.model.Client;
import lk.ijse.SGA.model.tm.ClientTm;
import lk.ijse.SGA.repository.ClientRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ClientFormController {
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

    public void initialize(){
        setCellValueFactory();
        loadAllClients();
    }

    private void setCellValueFactory() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colLawyerId.setCellValueFactory(new PropertyValueFactory<>("lawyerId"));
    }

    private void loadAllClients() {
        ObservableList<ClientTm> obList = FXCollections.observableArrayList();

        List<Client> clientList = ClientRepo.getAll();
        for (Client client : clientList) {
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
    }

    @FXML
    void btnSaveOnAction (ActionEvent event){
        String clientId = txtClientId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String lawyerId = txtLawyerId.getText();

        Client client = new Client(clientId, name, address, email, contact, lawyerId);

        try {
            boolean isSaved = ClientRepo.save(client);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "client saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtClientId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        txtLawyerId.setText("");
    }


    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String clientId = txtClientId.getText();

        Client client = ClientRepo.searchById(clientId);
        if (client != null) {
            txtClientId.setText(client.getClientId());
            txtName.setText(client.getName());
            txtAddress.setText(client.getAddress());
            txtEmail.setText(client.getEmail());
            txtContact.setText(client.getContact());
            txtLawyerId.setText(client.getLawyerId());
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
        String contact = txtContact.getText();
        String lawyerId = txtLawyerId.getText();

        Client client = new Client(clientId, name, address, email, contact, lawyerId);

        try {
            boolean isUpdated = ClientRepo.update(client);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "client updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtClientId.getText();

        try {
            boolean isDeleted = ClientRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "client deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }
}
