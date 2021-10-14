package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Appointment {

    private int ID;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private Date startDate;
    private Date startTime;
    private Date endDate;
    private Date endTime;
    private int customerID;

    public Appointment(int ID,
                       String title,
                       String description,
                       String location,
                       String contact,
                       String type,
                       Date startDate,
                       Date startTime,
                       Date endDate,
                       Date endTime,
                       int customerID) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

}
