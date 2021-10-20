package model;

import java.sql.*;
import java.time.LocalDateTime;

public class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static void loadCustomers() throws SQLException {
        try {
            Statement stmt = connection.createStatement();
            String query1 = "SELECT COUNTRY_ID FROM FIRST_LEVEL_DIVISIONS " +
                    "INNER JOIN CUSTOMERS ON FIRST_LEVEL_DIVISIONS.Division_ID = CUSTOMERS.Division_ID";
            String query2 = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID " +
                    "FROM CUSTOMERS";
            ResultSet rs1 = stmt.executeQuery(query1);
            rs1.next();
            int countryID = rs1.getInt("Country_ID");
            ResultSet rs2 = stmt.executeQuery(query2);

        while (rs2.next()) {
            int ID = rs2.getInt("Customer_ID");
            String name = rs2.getString("Customer_Name");
            String address = rs2.getString("Address");
            String postalCode = rs2.getString("Postal_Code");
            String phone = rs2.getString("Phone");
            String country = Integer.toString(countryID);
            String division = rs2.getString("Division_ID");

            Customer customer = new Customer(ID, name, address, postalCode, phone, country, division);
            Schedule.addCustomer(customer);
        }

        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void loadAppointments(){
        try {
            Statement stmt = connection.createStatement();
            String query1 = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "Customer_ID, User_ID, Contact_ID FROM APPOINTMENTS";
            ResultSet rs1 = stmt.executeQuery(query1);

            while (rs1.next()) {
                int ID = rs1.getInt("Appointment_ID");
                String title = rs1.getString("Title");
                String description = rs1.getString("Description");
                String location = rs1.getString("Location");
                String contact = null;
                String type = rs1.getString("Type");
                LocalDateTime startDate = rs1.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDate = rs1.getTimestamp("End").toLocalDateTime();
                int customerID = rs1.getInt("Customer_ID");
                int contactID = rs1.getInt("Contact_ID");
                int userID = rs1.getInt("User_ID");

                Appointment appointment = new Appointment(ID,
                        title,
                        description,
                        location,
                        type,
                        startDate,
                        endDate,
                        customerID,
                        contactID,
                        userID);
                Schedule.addAppointment(appointment);
            }
        }

        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
