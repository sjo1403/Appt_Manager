package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Appointment {

    private int ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int customerID;
    private String contact;
    private int userID;

    /**
     * Appointment constructor used for adding new appointments.
     * @param ID Appointment ID
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param type Appointment type
     * @param startDate Appointment start date and time
     * @param endDate Appointment end date and time
     * @param customerID Customer ID associated with appointment
     * @param contact Appointment contact
     * @param userID User ID associated with appointment
     */
    public Appointment(int ID,
                       String title,
                       String description,
                       String location,
                       String type,
                       LocalDateTime startDate,
                       LocalDateTime endDate,
                       int customerID,
                       String contact,
                       int userID) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
        this.contact = contact;
        this.userID = userID;
    }

    /**
     * Empty appointment constructor used for updating appointments.
     */
    public Appointment() {

    }

    /**
     *
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     *
     * @param ID Set ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title Set title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description set description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location set location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type set type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return start date and time
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate set start date and time
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return end date and time
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate end date and time
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return customer ID associated with appointment
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @param customerID set customer ID associated with appointment
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @return contact
     */
    public String getContact() {
        return contact;
    }

    /**
     *
     * @param contact set contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     *
     * @return user ID associated with appointment
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @param userID set user ID associated with appointment
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    private static ObservableList<Customer> unscheduledCustomers = FXCollections.observableArrayList();
    private static ObservableList<Customer> scheduledCustomers = FXCollections.observableArrayList();

    /**
     *
     * @param customer Used for TableView in 'Add Appointment' and 'Update Appointment' stages
     */
    public static void addUnscheduledCustomer(Customer customer){
        unscheduledCustomers.add(customer);
    }

    /**
     *
     * @param customer Used for TableView in 'Add Appointment' and 'Update Appointment' stages
     */
    public static void deleteUnscheduledCustomer(Customer customer){
        unscheduledCustomers.remove(customer);
    }

    /**
     *
     * @param index Index of customer to be updated
     * @param customer Customer object to be updated
     */
    public static void updateCustomer(Integer index, Customer customer) {
        for (int i = 0; i < unscheduledCustomers.size(); i++) {
            if (unscheduledCustomers.get(i).getID() == customer.getID()) {
                unscheduledCustomers.set(i, customer);
                break;
            }
        }

        for (int i = 0; i < scheduledCustomers.size(); i++) {
            if (scheduledCustomers.get(i).getID() == customer.getID()) {
                scheduledCustomers.set(i, customer);
                break;
            }
        }
    }

    /**
     *
     * @return Used to set items in TableView in 'Add Appointment' and 'Update Appointment' stages
     */
    public static ObservableList<Customer> getScheduledCustomers() {
        return scheduledCustomers;
    }

    /**
     *
     * @return Used to set items in TableView in 'Add Appointment' and 'Update Appointment' stages
     */
    public static ObservableList<Customer> getUnscheduledCustomers() {
        return unscheduledCustomers;
    }

    /**
     *
     * @param customer Used for TableView in 'Add Appointment' and 'Update Appointment' stages
     */
    public static void scheduleCustomer(Customer customer) {
        unscheduledCustomers.remove(customer);
        scheduledCustomers.add(customer); }

    /**
     *
      * @param customer Used for TableView in 'Add Appointment' and 'Update Appointment' stages
     */
    public static void descheduleCustomer(Customer customer) {
        unscheduledCustomers.add(customer);
        scheduledCustomers.remove(customer); }

    /**
     * Used for TableView in 'Add Appointment' and 'Update Appointment' stages
     */
    public static void resetSchedule(){
        for (Customer customer : scheduledCustomers) {
            if (!unscheduledCustomers.contains(customer)) {
                unscheduledCustomers.add(customer);
            }
        }
        scheduledCustomers.clear();
    }

    /**
     *
     * @param date unformatted date
     * @return formatted date
     * @throws ParseException handles ParseException
     */
    public static LocalDateTime stringToDate(String date) throws ParseException {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateD = LocalDateTime.parse(date, FORMATTER);
        return dateD;
    }

    /**
     *
     * @param date unformatted date
     * @return formatted date
     */
    public static String dateToString(LocalDateTime date) {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateS = FORMATTER.format(date);
        return dateS;
    }

}
