package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Schedule {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static void addCustomer(Customer customer){
        customers.add(customer);
    }

    public static void deleteCustomer(Customer customer){
        customers.remove(customer);
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

    public static ObservableList<Appointment> getAllAppointments() {
        return appointments;
    }
}
