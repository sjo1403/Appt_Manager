package controller;

import com.sun.scenario.effect.Offset;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.JDBC;
import model.Schedule;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class AddAppointment {

    private Customer customer;

    @FXML
    private TextField IDtxt;

    @FXML
    private Button addBttn;

    @FXML
    private AnchorPane addOutsourced;

    @FXML
    private Button cancelBttn;

    @FXML
    private ComboBox contactBox;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private TableColumn<Customer, Integer> custIDCol;

    @FXML
    private TableColumn<Customer, String> custNameCol;

    @FXML
    private Button deleteBttn;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private TableColumn<Customer, String> divisionCol;

    @FXML
    private TextField endDateTxt;

    @FXML
    private TextField userIDTxt;

    @FXML
    private TextField locationTxt;

    @FXML
    private TableColumn<Customer, String> lowCountryCol;

    @FXML
    private TableColumn<Customer, Integer> lowCustIDCol;

    @FXML
    private TableColumn<Customer, String> lowCustNameCol;

    @FXML
    private TableColumn<Customer, String> lowDivisionCol;

    @FXML
    private TableView<Customer> lowerTable;

    @FXML
    private Button saveBttn;

    @FXML
    private Button searchProductBttn;

    @FXML
    private TextField searchTxt;

    @FXML
    private TextField startDateTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private TableView<Customer> upperTable;

    /**
     * sets customer TableView
     * @throws SQLException handles SQL exceptions
     */
    public void initialize() throws SQLException {
        contactBox.getItems().addAll(JDBC.loadContacts());

        //upper TableView
        upperTable.setItems(Appointment.getUnscheduledCustomers());
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        //lower TableView
        lowerTable.setItems(Appointment.getScheduledCustomers());
        lowCustIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        lowCustNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lowCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        lowDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
    }

    /**
     * places selected customer to lowerTable TableView
     * @param customerTBS customer to be selected
     */
    public void addSelectedCustomer(Customer customerTBS){
        customer = customerTBS;
    }

    /**
     * adds selected customer to appointment
     * @param event executes method when button is clicked
     */
    @FXML
    void addBttn(ActionEvent event) {
        if (lowerTable.getItems().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Only one customer record can be included in an appointment. " +
                    "Delete current record before adding an alternate record.");
            alert.showAndWait();
            return;
        }

        Customer customer = upperTable.getSelectionModel().getSelectedItem();
        Appointment.scheduleCustomer(customer);

        addSelectedCustomer(customer);
    }

    /**
     * closes stage
     * @param event executes method when button is clicked
     */
    @FXML
    void cancelBttn(ActionEvent event) {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    /**
     * places selected customer in upperTable TableView
     */
    public void removeSelectedCustomer(){
        customer = null;
    }

    /**
     * removes selected customer from appointment
     * @param event executes method when button is clicked
     */
    @FXML
    void deleteBttn(ActionEvent event) {
        Customer customer = lowerTable.getSelectionModel().getSelectedItem();

        if (customer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a customer record to remove.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Selected customer record will be removed. Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Appointment.descheduleCustomer(customer);
            removeSelectedCustomer();
        }
    }

    /**
     * saves appointment info in database
     * @param event executes method when button is clicked
     * @throws ParseException handles parse exception
     * @throws SQLException handles SQL exception
     */
    @FXML
    void saveBttn(ActionEvent event) throws ParseException, SQLException {
        int ID = 0;

        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String contact;
        try {
            contact = contactBox.getSelectionModel().getSelectedItem().toString();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a contact.");
            alert.showAndWait();
            return;
        }
        String type = typeTxt.getText();
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        int userID;
        try {
            userID = Integer.parseInt(userIDTxt.getText());
            if (userID != 1 && userID!=2) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid entry for User ID. " +
                        "Enter '1' for test or '2' for admin.");
                alert.showAndWait();
                return;
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid entry for User ID. " +
                    "Enter '1' for test or '2' for admin.");
            alert.showAndWait();
            return;
        }

        if (title.isBlank() || description.isBlank() || location.isBlank() || contact.isBlank() || type.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Appointment form has one or more blank sections. " +
                    "Fill out all sections to schedule an appointment.");
            alert.showAndWait();
            return;
        }

        //format dates and times
        ArrayList<LocalTime> times = new ArrayList<>();
        times.clear();

        try {
            startDate = Appointment.stringToDate(startDateTxt.getText());
            endDate = Appointment.stringToDate(endDateTxt.getText());

            times.add(startDate.toLocalTime());
            times.add(endDate.toLocalTime());
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Date/Time entry. " +
                    "Use the format 'yyyy-MM-dd HH:MM.");
            alert.showAndWait();
            return;
        }

        String rawOffset = OffsetDateTime.now().getOffset().toString();
        String sign = rawOffset.substring(0,1);
        int amount = Integer.parseInt(rawOffset.substring(2,3));
        int closingOffset, openingOffset;

        if (sign.equals("-")) {
            closingOffset = (-amount) + 4;
            openingOffset = (-amount) + 4;
        }

        else {
            closingOffset = amount + 4;
            openingOffset = amount + 4;
        }

        DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime estClosing = LocalTime.parse("22:00", FORMAT);
        LocalTime estOpening = LocalTime.parse("08:00", FORMAT);
        LocalTime closing = estClosing.plusHours(closingOffset);
        LocalTime opening = estOpening.plusHours(openingOffset);

        //validate appointment time
        for (LocalTime time : times) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Selected appointment time not within hours of operation." +
                    " Please select valid appointment time (8:00AM-10:00PM EST).");

            //throw an error if appointment outside of business hours
            if ( time.isAfter(closing) || time.isBefore(opening) ) {
                alert.showAndWait();
                return;
            }
        }

        if (endDate.isBefore(startDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Start date & time must take place before end date & time.");
            alert.showAndWait();
            return;
        }

        //validate customer selection
        if (customer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer record must be selected create an appointment.");
            alert.showAndWait();
            return;
        }

        if (lowerTable.getItems().size() > 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Only one customer record can be included in an appointment.");
            alert.showAndWait();
            return;
        }

        //validate non-overlapping schedule
        for (Appointment appointment : Schedule.getAllAppointments()) {

            if (startDate.isEqual(appointment.getStartDate())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. " +
                        "Selected appointment begins during another scheduled appointment.");
                alert.showAndWait();
                return;
            }

            if (endDate.isEqual(appointment.getEndDate())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. " +
                        "Selected appointment ends during another scheduled appointment.");
                alert.showAndWait();
                return;
            }

            if (startDate.isAfter(appointment.getStartDate()) && startDate.isBefore(appointment.getEndDate())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. " +
                        "Selected appointment begins during another scheduled appointment.");
                alert.showAndWait();
                return;
            }

            if (endDate.isAfter(appointment.getStartDate()) && endDate.isBefore(appointment.getEndDate())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. " +
                        "Selected appointment ends during another scheduled appointment.");
                alert.showAndWait();
                return;
            }

            if (startDate.isBefore(appointment.getStartDate()) && endDate.isAfter(appointment.getEndDate())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. " +
                        "Selected appointment overtakes another scheduled appointment.");
                alert.showAndWait();
                return;
            }
        }

        Appointment appointment = new Appointment(ID,
                title,
                description,
                location,
                type,
                startDate,
                endDate,
                customer.getID(),
                contact,
                userID);
        JDBC.saveAppointment(appointment);
        cancelBttn(event);
    }

}
