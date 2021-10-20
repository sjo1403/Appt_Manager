package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.JDBC;
import model.Schedule;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class MainScreen {

    private ObservableList<Appointment> appointmentView = Schedule.getAllAppointments();

    @FXML
    private Button addApptBttn;

    @FXML
    private Button addCustBttn;

    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;

    @FXML
    private TableView<Appointment> apptTable;

    @FXML
    private TableColumn<Appointment, String> apptTitleCol;

    @FXML
    private TableColumn<Appointment, Integer> contactCol;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private TableColumn<Appointment, Integer> custIDCol;

    @FXML
    private TableColumn<Customer, String> custNameCol;

    @FXML
    private TableView<Customer> custTable;

    @FXML
    private TableColumn<Customer, String> apptCustIDCol;

    @FXML
    private Button deleteApptBttn;

    @FXML
    private Button deleteCustBttn;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Customer, String> divisionCol;

    @FXML
    private TableColumn<Appointment, Date> endCol;

    @FXML
    private Button exitBttn;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private Button searchApptBttn;

    @FXML
    private TextField searchApptTxt;

    @FXML
    private Button searchCustBttn;

    @FXML
    private TextField searchCustTxt;

    @FXML
    private TableColumn<Appointment, Date> startCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private Button updateApptBttn;

    @FXML
    private Button updateCustBttn;

    @FXML
    private TableColumn<Appointment, Integer> userIDCol;

    public void initialize() {
        //Customer TableView
        custTable.setItems(Schedule.getAllCustomers());

        custIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        //Appointment TableView
        apptTable.setItems(appointmentView);

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    @FXML
    void addApptBttn(ActionEvent event) throws IOException {
        Appointment.resetSchedule();

        Parent root = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add Customer");
        primaryStage.setScene(new Scene(root, 950, 630));
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
        Appointment appointment = apptTable.getSelectionModel().getSelectedItem();

        if (appointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select an appointment to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Selected appointment will be deleted. " +
                "Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Schedule.deleteAppointment(appointment);
        }
    }

    @FXML
    void deleteCustBttn(ActionEvent event) {
        Customer customer = custTable.getSelectionModel().getSelectedItem();

        if (customer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select a customer record to delete.");
            alert.showAndWait();
            return;
        }

        for (Appointment appointment : Schedule.getAllAppointments()) {
            if (customer.getID() == appointment.getCustomerID()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Selected customer record is included in an " +
                        "appointment(s). Please delete corresponding appointment(s) before deleting customer record.");
                alert.showAndWait();
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Selected customer record will be deleted. Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Schedule.deleteCustomer(customer);
        }
    }

    @FXML
    void exitBttn(ActionEvent event) {
        Stage stage = (Stage) exitBttn.getScene().getWindow();
        stage.close();

        JDBC.closeConnection();
    }

    @FXML
    void allRadio(ActionEvent event) {
        appointmentView = Schedule.getAllAppointments();
        initialize();
    }


    @FXML
    void monthRadio(ActionEvent event) {
        appointmentView = Schedule.getMonthAppointments();
        initialize();
    }


    @FXML
    void weekRadio(ActionEvent event) {
        appointmentView = Schedule.getWeekAppointments();
        initialize();
    }

    @FXML
    void updateApptBttn(ActionEvent event) throws IOException {
        Appointment appointment = apptTable.getSelectionModel().getSelectedItem();
        int row = apptTable.getSelectionModel().selectedIndexProperty().get();

        if (appointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select an appointment to update.");
            alert.showAndWait();
            return;
        }

        UpdateAppointment.selectAppointment(appointment);
        UpdateAppointment.selectRow(row);

        Parent root = FXMLLoader.load(getClass().getResource("../view/UpdateAppointment.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add Customer");
        primaryStage.setScene(new Scene(root, 950, 630));
        primaryStage.show();
    }

    @FXML
    void updateCustBttn(ActionEvent event) throws IOException {
        Customer customer = custTable.getSelectionModel().getSelectedItem();
        int row = custTable.getSelectionModel().selectedIndexProperty().get();

        if (customer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select a customer record to update.");
            alert.showAndWait();
            return;
        }

        UpdateCustomer.selectCustomer(customer);
        UpdateCustomer.selectRow(row);

        Parent root = FXMLLoader.load(getClass().getResource("../view/UpdateCustomer.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add Customer");
        primaryStage.setScene(new Scene(root, 560, 450));
        primaryStage.show();
    }

}
