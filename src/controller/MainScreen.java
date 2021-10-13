package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Customer;
import model.Schedule;

import java.io.IOException;

public class MainScreen {

    @FXML
    private Button addApptBttn;

    @FXML
    private Button addCustBttn;

    @FXML
    private TableColumn<?, ?> apptIDCol;

    @FXML
    private TableView<?> apptTable;

    @FXML
    private TableColumn<?, ?> apptTitleCol;

    @FXML
    private TableColumn<?, ?> contactCol;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private TableColumn<Customer, String> custIDCol;

    @FXML
    private TableColumn<Customer, String> custNameCol;

    @FXML
    private TableView<Customer> custTable;

    @FXML
    private TableColumn<Customer, String> customerIDCol;

    @FXML
    private Button deleteApptBttn;

    @FXML
    private Button deleteCustBttn;

    @FXML
    private TableColumn<?, ?> descriptionCol;

    @FXML
    private TableColumn<Customer, String> divisionCol;

    @FXML
    private TableColumn<?, ?> endCol;

    @FXML
    private Button exitBttn;

    @FXML
    private TableColumn<?, ?> locationCol;

    @FXML
    private Button searchApptBttn;

    @FXML
    private TextField searchApptTxt;

    @FXML
    private Button searchCustBttn;

    @FXML
    private TextField searchCustTxt;

    @FXML
    private TableColumn<?, ?> startCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private Button updateApptBttn;

    @FXML
    private Button updateCustBttn;

    @FXML
    private TableColumn<?, ?> userIDCol;

    public void initialize() {
        //Customer TableView
        custTable.setItems(Schedule.getAllCustomers());

        custIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
    }

    @FXML
    void addApptBttn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add Customer");
        primaryStage.setScene(new Scene(root, 950, 590));
        primaryStage.show();
    }

    @FXML
    void addCustBttn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add Customer");
        primaryStage.setScene(new Scene(root, 560, 450));
        primaryStage.show();
    }

    @FXML
    void deleteApptBttn(ActionEvent event) {

    }

    @FXML
    void deleteCustBttn(ActionEvent event) {
        Customer customer = custTable.getSelectionModel().getSelectedItem();
        Schedule.deleteCustomer(customer);
    }

    @FXML
    void exitBttn(ActionEvent event) {
        Stage stage = (Stage) exitBttn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void searchApptBttn(ActionEvent event) {

    }

    @FXML
    void searchPartBttn(ActionEvent event) {

    }

    @FXML
    void updateApptBttn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/UpdateAppointment.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add Customer");
        primaryStage.setScene(new Scene(root, 950, 590));
        primaryStage.show();
    }

    @FXML
    void updateCustBttn(ActionEvent event) throws IOException {
        Customer customer = custTable.getSelectionModel().getSelectedItem();
        int row = custTable.getSelectionModel().selectedIndexProperty().get();
        UpdateCustomer.selectCustomer(customer);
        UpdateCustomer.selectRow(row);

        Parent root = FXMLLoader.load(getClass().getResource("../view/UpdateCustomer.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add Customer");
        primaryStage.setScene(new Scene(root, 560, 450));
        primaryStage.show();
    }

}
