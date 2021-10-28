package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.JDBC;
import model.Schedule;

import java.sql.SQLException;

public class UpdateCustomer {

    private static int row;

    private static Customer customer;

    @FXML
    private TextField IDtxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private Button cancelBttn;

    @FXML
    private ComboBox countryBox;

    @FXML
    private ComboBox divisionBox;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalTxt;

    @FXML
    private Button saveBttn;

    /**
     * method used to pass customer selection from MainScreen Controller to UpdateCustomer Controller
     * @param customerTBU customer to be updated
     */
    public  static void selectCustomer(Customer customerTBU){
        customer = customerTBU;
    }

    /**
     * method used to pass customer selection from MainScreen Controller to UpdateCustomer Controller
     * @param rowTBU row to be updated
     */
    public static void selectRow(int rowTBU){
        row = rowTBU;
    }

    /**
     * sets text boxes with previously filled info for a selected customer
     * @throws SQLException handles SQL exceptions
     */
    public void initialize() throws SQLException {
        IDtxt.setText(Integer.toString(customer.getID()));
        nameTxt.setText(customer.getName());
        addressTxt.setText(customer.getAddress());
        postalTxt.setText(customer.getPostalCode());
        phoneTxt.setText(customer.getPhone());

        countryBox.getItems().clear();
        divisionBox.getItems().clear();
        countryBox.getItems().addAll(JDBC.loadLCountries());
        countryBox.getSelectionModel().select(customer.getCountry());

        int countryID = countryBox.getSelectionModel().getSelectedIndex() + 1;
        divisionBox.getItems().clear();
        divisionBox.getItems().addAll(JDBC.loadDivisions(countryID));
        divisionBox.getSelectionModel().select(customer.getDivision());
    }

    /**
     * filter first level divisions based on country
     * @param event executes method when button is clicked
     * @throws SQLException handles SQL exceptions
     */
    @FXML
    void countryBox(ActionEvent event) throws SQLException {
        if (countryBox.getSelectionModel().isEmpty()) {
            return;
        }

        else {
            int countryID = countryBox.getSelectionModel().getSelectedIndex() + 1;
            divisionBox.getItems().clear();
            divisionBox.getItems().addAll(JDBC.loadDivisions(countryID));
        }
    }

    /**
     * closes stage
     * @param event executes method when button is clicked
     */
    @FXML
    void cancelBttn(ActionEvent event) {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    /**
     * updates customer info in database
     * @param event executes method when button is clicked
     * @throws SQLException handles SQL exception
     */
    @FXML
    void saveBttn(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Country and/or Division must be selected to save customer record.");

        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();
        String country;
        try {
            country = countryBox.getSelectionModel().getSelectedItem().toString();
        }
        catch (Exception e) {
            alert.showAndWait();
            return;
        }
        String division;
        try {
            division = divisionBox.getSelectionModel().getSelectedItem().toString();
        }
        catch (Exception e) {
            alert.showAndWait();
            return;
        }

        if (name.isBlank() || address.isBlank() || postalCode.isBlank() || phone.isBlank()) {
            alert = new Alert(Alert.AlertType.WARNING, "Customer form has one or more blank sections. " +
                    "Fill out all sections to save customer record.");
            alert.showAndWait();
            return;
        }

        Customer customerUD = new Customer(customer.getID(), name, address, postalCode, phone, country, division);
        JDBC.updateCustomer(customerUD);
        Schedule.updateCustomer(customerUD, row);
        cancelBttn(event);
    }

}
