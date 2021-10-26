package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.print.DocFlavor;
import java.sql.*;
import java.time.LocalDateTime;
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

    public static boolean authenticate(String userName, String password, String language) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!language.equals("en")) {
            alert = new Alert(Alert.AlertType.ERROR, "Nom d'utilisateur et / ou mot de passe incorrect.");
        }

        else {
            alert = new Alert(Alert.AlertType.ERROR, "Invalid username and/or password.");
        }

        try {
            String query = "SELECT COUNT(User_Name) FROM USERS WHERE User_Name=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, userName);
            ResultSet rs1 = ps.executeQuery();
            rs1.next();

            if (rs1.getInt(1) == 1) {
                query = "SELECT Password FROM USERS WHERE User_Name=?";
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

    public static void loadAllCustomers() throws SQLException {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Country_ID, Division " +
                    "FROM FIRST_LEVEL_DIVISIONS " +
                    "INNER JOIN CUSTOMERS ON FIRST_LEVEL_DIVISIONS.Division_ID = CUSTOMERS.Division_ID";
            ResultSet rs = stmt.executeQuery(query);

            List<String> countries = Arrays.asList(null ,"U.S", "UK", "Canada");

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

    public static void loadCustomer() throws SQLException {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Country_ID, Division " +
                    "FROM FIRST_LEVEL_DIVISIONS " +
                    "INNER JOIN CUSTOMERS ON FIRST_LEVEL_DIVISIONS.Division_ID = CUSTOMERS.Division_ID " +
                    "ORDER BY Customer_ID DESC LIMIT 1;";
            ResultSet rs = stmt.executeQuery(query);

            List<String> countries = Arrays.asList(null ,"U.S", "UK", "Canada");

            rs.next();
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
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void loadAllAppointments(){
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

    public static void loadAppointment(){
        try {
            Statement stmt = connection.createStatement();
            String query1 = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "Customer_ID, User_ID, Contact_Name FROM CONTACTS " +
                    "INNER JOIN APPOINTMENTS ON CONTACTS.Contact_ID = APPOINTMENTS.Contact_ID " +
                    "ORDER BY Appointment_ID DESC LIMIT 1;";
            ResultSet rs1 = stmt.executeQuery(query1);

            rs1.next();
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

        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static ObservableList<String> loadLCountries() throws SQLException {
        ObservableList<String> countries = FXCollections.observableArrayList();

        String query = "SELECT Country FROM COUNTRIES";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            countries.add(rs.getString("Country"));
        }

        return countries;
    }

    public static ObservableList<String> loadDivisions(int countryID) throws SQLException {
        ObservableList<String> divisions = FXCollections.observableArrayList();

        String query = "SELECT Division FROM FIRST_LEVEL_DIVISIONS WHERE Country_ID = " + countryID;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            divisions.add(rs.getString("Division"));
        }

        return divisions;
    }

    public static ObservableList<String> loadContacts() throws SQLException {
        ObservableList<String> contacts = FXCollections.observableArrayList();

        String query = "SELECT Contact_Name FROM CONTACTS";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            contacts.add(rs.getString("Contact_Name"));
        }

        return contacts;
    }

    public static void saveCustomer(Customer customer) throws SQLException{
        String query1 = "SELECT Division_ID FROM FIRST_LEVEL_DIVISIONS WHERE Division = ?";
        PreparedStatement ps1 = connection.prepareStatement(query1);
        ps1.setString(1, customer.getDivision());
        ResultSet rs1 = ps1.executeQuery();
        rs1.next();
        int divisionID = rs1.getInt("Division_ID");

        String query2 = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps2 = connection.prepareStatement(query2);
        ps2.setString(1, customer.getName());
        ps2.setString(2, customer.getAddress());
        ps2.setString(3, customer.getPostalCode());
        ps2.setString(4, customer.getPhone());
        ps2.setInt(5, divisionID);

        ps2.execute();
        loadCustomer();
    }

    public static void updateCustomer(Customer customer) throws SQLException{
        String query1 = "SELECT Division_ID FROM FIRST_LEVEL_DIVISIONS WHERE Division=?";
        PreparedStatement ps1 = connection.prepareStatement(query1);
        ps1.setString(1, customer.getDivision());
        ResultSet rs1 = ps1.executeQuery();
        rs1.next();
        int divisionID = rs1.getInt("Division_ID");

        String query2 = "UPDATE CUSTOMERS " +
                "SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
        PreparedStatement ps2 = connection.prepareStatement(query2);
        ps2.setString(1, customer.getName());
        ps2.setString(2, customer.getAddress());
        ps2.setString(3, customer.getPostalCode());
        ps2.setString(4, customer.getPhone());
        ps2.setInt(5, divisionID);
        ps2.setInt(6, customer.getID());

        ps2.execute();
    }

    public static void deleteCustomer(Customer customer) throws SQLException {
        String query = "DELETE FROM CUSTOMERS WHERE Customer_ID=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, customer.getID());
        ps.execute();
        Schedule.deleteCustomer(customer);
    }

    public static void saveAppointment(Appointment appointment) throws SQLException{
        String query1 = "SELECT Contact_ID FROM CONTACTS WHERE Contact_Name=?";
        PreparedStatement ps1 = connection.prepareStatement(query1);
        ps1.setString(1, appointment.getContact());
        ResultSet rs1 = ps1.executeQuery();
        rs1.next();
        int contactID = rs1.getInt("Contact_ID");

        String query2 = "INSERT INTO APPOINTMENTS " +
                "(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps2 = connection.prepareStatement(query2);
        ps2.setString(1, appointment.getTitle());
        ps2.setString(2, appointment.getDescription());
        ps2.setString(3, appointment.getLocation());
        ps2.setString(4, appointment.getType());
        ps2.setTimestamp(5, Timestamp.valueOf(appointment.getStartDate()));
        ps2.setTimestamp(6, Timestamp.valueOf(appointment.getEndDate()));
        ps2.setInt(7, appointment.getCustomerID());
        ps2.setInt(8, appointment.getUserID());
        ps2.setInt(9, contactID);

        ps2.execute();
        loadAppointment();
    }

    public static void updateAppointment(Appointment appointment) throws SQLException{
        String query1 = "SELECT Contact_ID FROM CONTACTS WHERE Contact_Name=?";
        PreparedStatement ps1 = connection.prepareStatement(query1);
        ps1.setString(1, appointment.getContact());
        ResultSet rs1 = ps1.executeQuery();
        rs1.next();
        int contactID = rs1.getInt("Contact_ID");

        String query2 = "UPDATE APPOINTMENTS " +
                "SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, User_ID=?, Contact_ID=? " +
                "WHERE Appointment_ID=?";
        PreparedStatement ps2 = connection.prepareStatement(query2);
        ps2.setString(1, appointment.getTitle());
        ps2.setString(2, appointment.getDescription());
        ps2.setString(3, appointment.getLocation());
        ps2.setString(4, appointment.getType());
        ps2.setTimestamp(5, Timestamp.valueOf(appointment.getStartDate()));
        ps2.setTimestamp(6, Timestamp.valueOf(appointment.getEndDate()));
        ps2.setInt(7, appointment.getCustomerID());
        ps2.setInt(8, appointment.getUserID());
        ps2.setInt(9, contactID);
        ps2.setInt(10, appointment.getID());

        ps2.execute();
    }

    public static void deleteAppointment(Appointment appointment) throws SQLException{
        String query = "DELETE FROM APPOINTMENTS WHERE Appointment_ID=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, appointment.getID());
        ps.execute();
        Schedule.deleteAppointment(appointment);
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
