package model;

import com.mysql.cj.protocol.Resultset;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static boolean authenticate(String userName, String password) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username and/or password.");

        try {
            String query = "SELECT COUNT(User_Name) FROM USERS WHERE User_Name = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, userName);
            ResultSet rs1 = ps.executeQuery();
            rs1.next();

            if (rs1.getInt(1) == 1) {
                query = "SELECT Password FROM USERS WHERE User_Name = ?";
                ps = connection.prepareStatement(query);
                ps.setString(1, userName);
                ResultSet rs2 = ps.executeQuery();
                rs2.next();

                if (rs2.getString("Password").equals(password)) {
                    return true;
                } else {
                    alert.showAndWait();
                    return false;
                }
            } else {
                alert.showAndWait();
                return false;
            }
        }
        catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public static void loadCustomers() throws SQLException {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Country_ID, Division " +
                    "FROM FIRST_LEVEL_DIVISIONS " +
                    "INNER JOIN CUSTOMERS ON FIRST_LEVEL_DIVISIONS.Division_ID = CUSTOMERS.Division_ID";
            ResultSet rs = stmt.executeQuery(query);

            List<String> countries = Arrays.asList(null ,"US", "UK", "Canada");

        while (rs.next()) {
            int ID = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String country = countries.get(rs.getInt("Country_ID"));
            String division = rs.getString("Division");

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
                    "Customer_ID, User_ID, Contact_Name FROM CONTACTS " +
                    "INNER JOIN APPOINTMENTS ON CONTACTS.Contact_ID = APPOINTMENTS.Contact_ID";
            ResultSet rs1 = stmt.executeQuery(query1);

            while (rs1.next()) {
                int ID = rs1.getInt("Appointment_ID");
                String title = rs1.getString("Title");
                String description = rs1.getString("Description");
                String location = rs1.getString("Location");
                String contact = rs1.getString("Contact_Name");
                String type = rs1.getString("Type");
                LocalDateTime startDate = rs1.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDate = rs1.getTimestamp("End").toLocalDateTime();
                int customerID = rs1.getInt("Customer_ID");
                int userID = rs1.getInt("User_ID");

                Appointment appointment = new Appointment(ID,
                        title,
                        description,
                        location,
                        type,
                        startDate,
                        endDate,
                        customerID,
                        contact,
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
