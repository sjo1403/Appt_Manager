package model;

import java.util.ArrayList;

public class Schedule {
    private static ArrayList<Customer> customers = new ArrayList<Customer>();
    private static ArrayList<Appointment> appointments = new ArrayList<Appointment>();

    public static void addCustomer(Customer customer){
        customers.add(customer);
    }

    public static void deleteCustomer(Customer customer){
        customers.remove(customer);
    }

    public static void updateCustomer(Customer customer, Integer index){
        customers.set(index, customer);
    }

    public static ArrayList<Customer> getAllCustomers() {
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

    public static ArrayList<Appointment> getAllAppointments() {
        return appointments;
    }
}
