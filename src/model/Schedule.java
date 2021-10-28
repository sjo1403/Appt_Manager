package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.Month;

public class Schedule {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     *
     * @param customer Adds customer to TableView and database
     */
    public static void addCustomer(Customer customer){
        customers.add(customer);
        Appointment.addUnscheduledCustomer(customer);
    }

    /**
     *
     * @param customer Deletes customer from TableView and database
     */
    public static void deleteCustomer(Customer customer){
        customers.remove(customer);
        Appointment.deleteUnscheduledCustomer(customer);
    }

    /**
     *
     * @param customer Updates customer in TableView and Database
     * @param index Index of customer to be updated
     */
    public static void updateCustomer(Customer customer, Integer index){
        customers.set(index, customer);
        Appointment.updateCustomer(index, customer);
    }

    /**
     *
     * @return Used in 'Main Screen' TableView
     */
    public static ObservableList<Customer> getAllCustomers() {
        return customers;
    }

    /**
     *
     * @param appointment Adds appointment to TableView and database
     */
    public static void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }

    /**
     *
     * @param appointment Deletes appointment from TableView and database
     */
    public static void deleteAppointment(Appointment appointment){
        appointments.remove(appointment);
    }

    /**
     *
     * @param appointment Updates appointment in TableView and database
     * @param index Index of appointment to be updated
     */
    public static void updateAppointment(Appointment appointment, Integer index){
        appointments.set(index, appointment);
    }

    /**
     *
     * @return Used in 'Main Screen' TableView
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return appointments;
    }

    /**
     *
     * @return Filters appointment based on month
     */
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

    /**
     *
     * @return Filters appointments based on week
     */
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
