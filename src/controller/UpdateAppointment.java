package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Schedule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private TextField endTimeTxt;

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
    private TextField startTimeTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private TableView<Customer> upperTable;

    public static void selectAppointment(Appointment appointmentTBU) {
        appointment = appointmentTBU;
    }

    public static void selectRow(int rowTBU) {
        row = rowTBU;
    }

    public static ObservableList<Customer> getUpperTableItems() {
        for (Customer unScheduledCustomer : Appointment.getUnscheduledCustomers()) {
            if (!lowerTableItem.contains(unScheduledCustomer)) {
                upperTableItems.add(unScheduledCustomer);
            }
        }
        return upperTableItems;
    }

    public static ObservableList<Customer> getLowerTableItem() {
        int ID = appointment.getCustomerID();
        for (Customer scheduledCustomer : Schedule.getAllCustomers()) {
            if (scheduledCustomer.getID() == ID) {
                customer = scheduledCustomer;
            }
        }
        lowerTableItem.add(customer);
        return lowerTableItem;
    }

    public void initialize(){
        titleTxt.setText(appointment.getTitle());
        descriptionTxt.setText(appointment.getDescription());
        locationTxt.setText(appointment.getLocation());
        contactTxt.setText(appointment.getContact());
        typeTxt.setText(appointment.getType());

        //format dates and times
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        String startDate = dateFormat.format(appointment.getStartDate());
        String startTime = timeFormat.format(appointment.getStartTime());
        String endDate = dateFormat.format(appointment.getEndDate());
        String endTime = timeFormat.format(appointment.getEndTime());

        startDateTxt.setText(startDate);
        startTimeTxt.setText(startTime);
        endDateTxt.setText(endDate);
        endTimeTxt.setText(endTime);

        //upper TableView
        upperTable.setItems(getUpperTableItems());
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        //lower TableView
        lowerTable.setItems(getLowerTableItem());
        lowCustIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        lowCustNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lowCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        lowDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
    }

    @FXML
    void cancelBttn(ActionEvent event) {
        upperTableItems.clear();
        lowerTableItem.clear();

        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteBttn(ActionEvent event) {
        Customer customer = lowerTable.getSelectionModel().getSelectedItem();
        upperTableItems.add(customer);
        lowerTableItem.remove(customer);
    }

    @FXML
    void saveBttn(ActionEvent event) throws ParseException {

        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String contact = contactTxt.getText();
        String type = typeTxt.getText();

        //format dates and times
        String startDateS = startDateTxt.getText();
        Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDateS);
        String startTimeS = startTimeTxt.getText();
        Date startTime = new SimpleDateFormat("hh:mm").parse(startTimeS);
        String endDateS = endDateTxt.getText();
        Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDateS);
        String endTimeS = endTimeTxt.getText();
        Date endTime = new SimpleDateFormat("hh:mm").parse(endTimeS);


        Appointment appointmentUD = new Appointment(appointment.getID(),
                title,
                description,
                location,
                contact,
                type,
                startDate,
                startTime,
                endDate,
                endTime,
                customer.getID());
        Schedule.updateAppointment(appointmentUD, row);
        cancelBttn(event);

        upperTableItems.clear();
        lowerTableItem.clear();
    }

    @FXML
    void searchProductBttn(ActionEvent event) {

    }

    public void addBttn(ActionEvent event) {
        Customer customer = upperTable.getSelectionModel().getSelectedItem();
        Appointment.scheduleCustomer(customer);
    }
}
