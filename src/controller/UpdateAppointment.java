package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.JDBC;
import model.Schedule;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class UpdateAppointment {

    private static Customer customer;

    private static Appointment appointment;

    private static int row;

    private static ObservableList<Customer> upperTableItems = FXCollections.observableArrayList();

    private static ObservableList<Customer> lowerTableItem = FXCollections.observableArrayList();

    @FXML
    private TextField IDtxt;

    @FXML
    private Button addBttn;

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
    private Button searchProductBttnsearchProductBttn;

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
     * passes selected appointment from MainScreen Controller to UpdateAppointment Controller
     * @param appointmentTBU appointment to be updated
     */
    public static void selectAppointment(Appointment appointmentTBU) {
        appointment = appointmentTBU;
    }

    /**
     * passes selected appointment from MainScreen Controller to UpdateAppointment Controller
     * @param rowTBU row to be updated
     */
    public static void selectRow(int rowTBU) {
        row = rowTBU;
    }

    /**
     * used to set upperTable TableView
     * @return upperTableItems (customer objects)
     */
    public static ObservableList<Customer> getUpperTableItems() {
        for (Customer customer : Schedule.getAllCustomers()) {
            if (customer != lowerTableItem.get(0))
                upperTableItems.add(customer);
            }

    return upperTableItems;
    }

    /**
     * used to set lowerTable TableView
     * @return lowerTableItems (customer objects)
     */
    public static ObservableList<Customer> getLowerTableItem() {
        int ID = appointment.getCustomerID();
        for (Customer scheduledCustomer : Schedule.getAllCustomers()) {
            if (scheduledCustomer.getID() == ID) {
                customer = scheduledCustomer;
                break;
            }
        }
        lowerTableItem.add(customer);
        return lowerTableItem;
    }

    /**
     * sets text boxes with previous appointment info
     * sets the upperTable and lowerTable TableViews
     * @throws SQLException handles SQL exceptions
     */
    public void initialize() throws SQLException {
        titleTxt.setText(appointment.getTitle());
        descriptionTxt.setText(appointment.getDescription());
        locationTxt.setText(appointment.getLocation());
        contactBox.getItems().addAll(JDBC.loadContacts());
        contactBox.getSelectionModel().select(appointment.getContact());
        typeTxt.setText(appointment.getType());
        userIDTxt.setText(Integer.toString(appointment.getUserID()));

        //format dates and times
        String startDate = Appointment.dateToString(appointment.getStartDate());
        String endDate = Appointment.dateToString(appointment.getEndDate());

        startDateTxt.setText(startDate);
        endDateTxt.setText(endDate);

        //lower TableView
        lowerTable.setItems(getLowerTableItem());
        lowCustIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        lowCustNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lowCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        lowDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        //upper TableView
        upperTable.setItems(getUpperTableItems());
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
    }

    /**
     * closes stage
     * @param event executes method when button is clicked
     */
    @FXML
    void cancelBttn(ActionEvent event) {
        upperTableItems.clear();
        lowerTableItem.clear();

        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    /**
     * removes selected customer from appointment
     * @param event executes method when button is clicked
     */
    @FXML
    void deleteBttn(ActionEvent event) {
        Customer customer = lowerTable.getSelectionModel().getSelectedItem();

        if (customer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a customer record to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Selected customer record will be removed. Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            upperTableItems.add(customer);
            lowerTableItem.remove(customer);
            addSelectedCustomer(null);
        }
    }

    /**
     * updates appointment info in database
     * @param event executes method when button is clicked
     * @throws ParseException handles parse exceptions
     * @throws SQLException handles SQL exceptions
     */
    @FXML
    void saveBttn(ActionEvent event) throws ParseException, SQLException {

        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String contact = contactBox.getSelectionModel().getSelectedItem().toString();
        String type = typeTxt.getText();
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        int userID;
        try {
            userID = Integer.parseInt(userIDTxt.getText());
            if (userID != 1 && userID !=2) {
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
                    "Use the format 'yyyy-MM-dd HH:MM.'");
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

        //validate non-overlapping schedule
        for (Appointment appointment : Schedule.getAllAppointments()) {

            if (startDate.isEqual(appointment.getStartDate()) && (customer.getID() != appointment.getCustomerID()) ) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. " +
                        "Selected appointment begins during another scheduled appointment.");
                alert.showAndWait();
                return;
            }

            if (endDate.isEqual(appointment.getEndDate()) && (customer.getID() != appointment.getCustomerID()) ) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. " +
                        "Selected appointment ends during another scheduled appointment.");
                alert.showAndWait();
                return;
            }

            if (startDate.isAfter(appointment.getStartDate()) && startDate.isBefore(appointment.getEndDate())
                    && (customer.getID() != appointment.getCustomerID()) ) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. " +
                        "Selected appointment begins during another scheduled appointment.");
                alert.showAndWait();
                return;
            }

            if (endDate.isAfter(appointment.getStartDate()) && endDate.isBefore(appointment.getEndDate())
                    && (customer.getID() != appointment.getCustomerID()) ) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. " +
                        "Selected appointment ends during another scheduled appointment.");
                alert.showAndWait();
                return;
            }

            if (startDate.isBefore(appointment.getStartDate()) && endDate.isAfter(appointment.getEndDate())
                    && (customer.getID() != appointment.getCustomerID()) ) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. " +
                        "Selected appointment overtakes another scheduled appointment.");
                alert.showAndWait();
                return;
            }
        }

        Appointment appointmentUD = new Appointment(appointment.getID(),
                title,
                description,
                location,
                type,
                startDate,
                endDate,
                customer.getID(),
                contact,
                userID);
        JDBC.updateAppointment(appointmentUD);
        Schedule.updateAppointment(appointmentUD, row);
        cancelBttn(event);

        upperTableItems.clear();
        lowerTableItem.clear();
    }

    /**
     * used in lowerTable TableView
     * @param customerTBS customer to be selected
     */
    public void addSelectedCustomer(Customer customerTBS){
        customer = customerTBS;
    }

    /**
     * adds selected customer to appointment
     * @param event executes method when button is clicked
     */
    public void addBttn(ActionEvent event) {
        if (lowerTable.getItems().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Only one customer record can be included in an appointment. " +
                    "Delete current record before adding an alternate record.");
            alert.showAndWait();
            return;
        }

        Customer customer = upperTable.getSelectionModel().getSelectedItem();
        addSelectedCustomer(customer);
        Appointment.scheduleCustomer(customer);

        lowerTableItem.add(customer);
        upperTableItems.remove(customer);
    }
}
