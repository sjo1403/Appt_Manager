package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

    private int ID;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int customerID;

    public Appointment(int ID,
                       String title,
                       String description,
                       String location,
                       String contact,
                       String type,
                       LocalDateTime startDate,
                       LocalDateTime endDate,
                       int customerID) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
    }

    public Appointment() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customer) {
        this.customerID = customer;
    }

    //change the un/scheduled customer methods from static, move to Appointment class
    private static ObservableList<Customer> unscheduledCustomers = FXCollections.observableArrayList();
    private static ObservableList<Customer> scheduledCustomers = FXCollections.observableArrayList();

    //change the un/scheduled customer methods from static, move to Appointment class
    //these methods will use the appointment object as the parameter (customer will be in the Appointment constructor)
    public static void addUnscheduledCustomer(Customer customer){
        unscheduledCustomers.add(customer);
    }

    public static ObservableList<Customer> getScheduledCustomers() {
        return scheduledCustomers;
    }

    public static ObservableList<Customer> getUnscheduledCustomers() {
        return unscheduledCustomers;
    }

    public static void scheduleCustomer(Customer customer) {
        unscheduledCustomers.remove(customer);
        scheduledCustomers.add(customer); }

    public static void descheduleCustomer(Customer customer) {
        unscheduledCustomers.add(customer);
        scheduledCustomers.remove(customer); }

    public static void resetSchedule(){
        for (Customer customer : scheduledCustomers) {
            if (!unscheduledCustomers.contains(customer)) {
                unscheduledCustomers.add(customer);
                scheduledCustomers.removeAll(customer);
            }
        }
    }

    public static LocalDateTime stringToDate(String date) throws ParseException {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateD = LocalDateTime.parse(date, FORMATTER);
        return dateD;
    }

    public static String dateToString(LocalDateTime date) {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateS = FORMATTER.format(date);
        return dateS;
    }

}
