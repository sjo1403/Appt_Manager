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
import java.sql.SQLException;
import java.util.ArrayList;
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
    private TableColumn<Appointment, String> contactCol;

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

    /**
     * sets customer and appointment TableViews
     */
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
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /**
     * navigates user to 'Add Appointment' screen
     * @param event method executes when button is clicked
     * @throws IOException handles IO exceptions
     */
    @FXML
    void addApptBttn(ActionEvent event) throws IOException {
        Appointment.resetSchedule();

        Parent root = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add Customer");
        primaryStage.setScene(new Scene(root, 950, 630));
        primaryStage.show();
    }

    /**
     * navigates user to 'Add Customer' screen
     * @param event method executes when button is clicked
     * @throws IOException handles IO exceptions
     */
    @FXML
    void addCustBttn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add Customer");
        primaryStage.setScene(new Scene(root, 560, 450));
        primaryStage.show();
    }

    /**
     * deletes appointment from TableView and database
     * @param event method executes when button is clicked
     * @throws SQLException handles SQL exceptions
     */
    @FXML
    void deleteApptBttn(ActionEvent event) throws SQLException {
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
            JDBC.deleteAppointment(appointment);
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Alert: " + appointment.getType()+" appointment, ID# " +
                    appointment.getID() + " removed from schedule.");
            alert.showAndWait();
        }
    }

    /**
     * deletes customer from TableView and database
     * @param event method executes when button is clicked
     * @throws SQLException handles SQL exception
     */
    @FXML
    void deleteCustBttn(ActionEvent event) throws SQLException {
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
            JDBC.deleteCustomer(customer);
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Alert: " + customer.getName() +
                    " removed from customer records.");
            alert.showAndWait();
        }
    }

    /**
     * closes database connection and closes program
     * @param event method executes when button is clicked
     */
    @FXML
    void exitBttn(ActionEvent event) {
        Stage stage = (Stage) exitBttn.getScene().getWindow();
        stage.close();

        JDBC.closeConnection();
    }

    /**
     * displays all appointments
     * @param event method executes when button is clicked
     */
    @FXML
    void allRadio(ActionEvent event) {
        appointmentView = Schedule.getAllAppointments();
        initialize();
    }

    /**
     * filters appointments by current month
     * @param event method executes when button is clicked
     */
    @FXML
    void monthRadio(ActionEvent event) {
        appointmentView = Schedule.getMonthAppointments();
        initialize();
    }

    /**
     * filters appointments by current day, plus seven days
     * @param event method executes when button is clicked
     */
    @FXML
    void weekRadio(ActionEvent event) {
        appointmentView = Schedule.getWeekAppointments();
        initialize();
    }

    /**
     * navigates user to 'Update Appointment' screen
     * @param event method executes when button is clicked
     * @throws IOException handles IO exception
     */
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

    /**
     * navigates user to 'Update Customer' screen
     * @param event method executes when button is clicked
     * @throws IOException handles IO exceptions
     */
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

    /**
     * generates report in UI, lambda functions in this method implemented to iterate through Customer and Appointment
     * objects (thus shortening the amount of code and improving efficiency)
     * @param event method executes when button is clicked
     */
    @FXML
    void reportBttn(ActionEvent event) {
        StringBuilder report = new StringBuilder();

        StringBuilder custCountry = new StringBuilder();
        StringBuilder usCust = new StringBuilder();
        StringBuilder ukCust = new StringBuilder();
        StringBuilder canCust = new StringBuilder();

        StringBuilder custAppt = new StringBuilder();

        StringBuilder contactSched = new StringBuilder();

        custCountry.append("REPORT PART 1: CUSTOMERS BY COUNTRY");
        Schedule.getAllCustomers().forEach(customer -> {    //lambda function for iteration

            if (customer.getCountry().equals("U.S")) {
                usCust.append("\n" + customer.getName());
            }

            if (customer.getCountry().equals("UK")) {
                ukCust.append("\n" + customer.getName());
            }

            if (customer.getCountry().equals("Canada")) {
                canCust.append("\n" + customer.getName());
            }
        });
        custCountry.append("\n\nUS Customers: ");
        custCountry.append(usCust);
        custCountry.append("\n\nUK Customers: ");
        custCountry.append(ukCust);
        custCountry.append("\n\nCanada Customers: ");
        custCountry.append(canCust);



        custAppt.append("\n\nREPORT PART 2: APPOINTMENTS BY TYPE AND MONTH\n\n");
        ArrayList<String> apptTypes = new ArrayList<>();
        ArrayList<String> apptMonths = new ArrayList<>();
        ArrayList<String> Contacts = new ArrayList<>();

        Schedule.getAllAppointments().forEach(appointment -> {    //lambda function for iteration
            if (!apptTypes.contains(appointment.getType())) {
                apptTypes.add(appointment.getType());
            }
        });

        apptTypes.forEach(type -> {    //lambda function for iteration
            int sum = 0;
            custAppt.append(type + " Appointments: ");

            for (Appointment appointment : Schedule.getAllAppointments()) {
                if (appointment.getType().equals(type)) {
                    sum += 1;
                }
            }
            custAppt.append(sum + "\n\n");
        });

        Schedule.getAllAppointments().forEach(appointment ->  {    //lambda function for iteration
            if (!apptMonths.contains(appointment.getStartDate().getMonth().toString())) {
                apptMonths.add(appointment.getStartDate().getMonth().toString());
            }
        });

        apptMonths.forEach(month -> {    //lambda function for iteration
            int sum = 0;
            custAppt.append(month + " Appointments: ");

            for (Appointment appointment : Schedule.getAllAppointments()) {
                if (appointment.getStartDate().getMonth().toString().equals(month)) {
                    sum += 1;
                }
            }
            custAppt.append(sum + "\n\n");
        });



        contactSched.append("REPORT PART 3: CONTACT SCHEDULES\n");
        Schedule.getAllAppointments().forEach(appointment -> {    //lambda function for iteration
            if (!Contacts.contains(appointment.getContact())) {
                Contacts.add(appointment.getContact());
            }
        });

        Contacts.forEach(contact -> {
            contactSched.append("\n" + contact + "'s Appointments: \n");

            Schedule.getAllAppointments().forEach(appointment -> {    //lambda function for iteration
                if (contact.equals(appointment.getContact())) {

                    contactSched.append("Appointment ID: " + appointment.getID() + "\t" + appointment.getTitle() + " " +
                            appointment.getType() + " " + appointment.getDescription() + " " + appointment.getLocation()
                            + " " + appointment.getStartDate() + " " + appointment.getEndDate() + "\tCustomer ID: " +
                            appointment.getCustomerID() + "\n");

                }
            });
        });


        report.append(custCountry);
        report.append(custAppt);
        report.append(contactSched);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, report.toString());
        alert.setTitle("REPORT");
        alert.showAndWait();
    }

}
