package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

public class Schedule {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static void addCustomer(Customer customer){
        customers.add(customer);
        Appointment.addUnscheduledCustomer(customer);
    }

    public static void deleteCustomer(Customer customer){
        customers.remove(customer);
        Appointment.deleteUnscheduledCustomer(customer);
    }

    public static void updateCustomer(Customer customer, Integer index){
        customers.set(index, customer);
        Appointment.updateCustomer(index, customer);
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

    public static ObservableList<Appointment> getMonthAppointments() {
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
        monthAppointments.clear();

        Month currentMonth = LocalDateTime.now().getMonth();
        for (Appointment appointment : getAllAppointments()) {
            if (appointment.getStartDate().getMonth() == currentMonth) {
                monthAppointments.add(appointment);
            }
        }

        int currentYear = LocalDateTime.now().getYear();
        for (Appointment appointment : getAllAppointments()) {
            if (appointment.getStartDate().getYear() != currentYear) {
                monthAppointments.remove(appointment);
            }
        }

        return monthAppointments;
    }

    public static ObservableList<Appointment> getWeekAppointments() {
        ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
        weekAppointments.clear();

        LocalDateTime weekLater = LocalDateTime.from(LocalDateTime.now().plusWeeks(1));
        for (Appointment appointment : getAllAppointments()) {
            if (appointment.getStartDate().isBefore(weekLater)) {
                weekAppointments.add(appointment);
            }
        }

        int currentYear = LocalDateTime.now().getYear();
        for (Appointment appointment : getAllAppointments()) {
            if (appointment.getStartDate().getYear() != currentYear) {
                weekAppointments.remove(appointment);
            }
        }

        return weekAppointments;
    }
}
