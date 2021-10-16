package controller;

import com.sun.webkit.dom.XPathNSResolverImpl;
import com.sun.webkit.dom.XPathResultImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Schedule;
import model.TimeCalculations;

import javax.swing.*;
import java.text.Format;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.TimeZone;

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
    private TextField contactTxt;

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

    public void initialize(){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);

        startDateTxt.setText(Appointment.dateToString(start));
        endDateTxt.setText(Appointment.dateToString(end));

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

    public void addSelectedCustomer(Customer customerTBS){
        customer = customerTBS;
    }

    @FXML
    void addBttn(ActionEvent event) {
        Customer customer = upperTable.getSelectionModel().getSelectedItem();
        Appointment.scheduleCustomer(customer);

        addSelectedCustomer(customer);
    }

    @FXML
    void cancelBttn(ActionEvent event) {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    public void removeSelectedCustomer(){
        customer = null;
    }

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

    @FXML
    void saveBttn(ActionEvent event) throws ParseException {
        int ID = (int) (Math.random()*(90000)+100000);

        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String contact = contactTxt.getText();
        String type = typeTxt.getText();
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

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
        String amount = rawOffset.substring(2,3);
        String offset = sign + amount;
        String closingX, openingY;

        switch (offset) {
            case "-6" : closingX = "20:00"; openingY = "06:00";
            break;

            case "-4" : closingX = "22:00"; openingY = "08:00";
            break;

            case "+1" : closingX = "03:00"; openingY = "13:00";
            break;

            default:
                throw new IllegalStateException("Unexpected value: " + offset);
        }

        DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime closing = LocalTime.parse(closingX, FORMAT);
        LocalTime opening = LocalTime.parse(openingY, FORMAT);

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

        Appointment appointment = new Appointment(ID,
                title,
                description,
                location,
                contact,
                type,
                startDate,
                endDate,
                customer.getID());
        Schedule.addAppointment(appointment);
        cancelBttn(event);
    }

    @FXML
    void searchProductBttn(ActionEvent event) {

    }

}
