package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Schedule {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    //change the un/scheduled customer methods from static, move to Appointment class
    private static ObservableList<Customer> unscheduledCustomers = FXCollections.observableArrayList();
    private static ObservableList<Customer> scheduledCustomers = FXCollections.observableArrayList();

    public static void addCustomer(Customer customer){
        customers.add(customer);
        unscheduledCustomers.add(customer);
    }

    public static void deleteCustomer(Customer customer){
        customers.remove(customer);
        unscheduledCustomers.remove(customer);
    }

    public static void updateCustomer(Customer customer, Integer index){
        customers.set(index, customer);
    }

    public static ObservableList<Customer> getAllCustomers() {
        return customers;
    }

    public static void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }

    public static void deleteAppointment(Appointment appointment){
        appointments.remove(appointment);
    }

    public static void updateAppointment(Appointment appointment, Integer index){
        appointments.set(index, appointment);
    }

    //change the un/scheduled customer methods from static, move to Appointment class
    //these methods will use the appointment object as the parameter (customer will be in the Appointment constructor)
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

    public static ObservableList<Appointment> getAllAppointments() {
        return appointments;
    }
}
