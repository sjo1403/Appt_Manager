package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Schedule;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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
    private Button searchProductBttn;

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

    public void initialize(){
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plusHours(1);

        //replace all Java.util.date with Java.time.LocalDateTime
        //startDateTxt.setText(Appointment.dateToString(date));
        //endDateTxt.setText(Appointment.dateToString(date));

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
        Appointment.descheduleCustomer(customer);

        removeSelectedCustomer();
    }

    @FXML
    void saveBttn(ActionEvent event) throws ParseException {
        int ID = (int) (Math.random()*(90000)+100000);

        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String contact = contactTxt.getText();
        String type = typeTxt.getText();

        //format dates and times
        Date startDate = Appointment.stringToDate(startDateTxt.getText());
        Date endDate = Appointment.stringToDate(endDateTxt.getText());

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
