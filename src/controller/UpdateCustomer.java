package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    public  static void selectCustomer(Customer customerTBU){
        customer = customerTBU;
    }

    public static void selectRow(int rowTBU){
        row = rowTBU;
    }

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

    @FXML
    void cancelBttn(ActionEvent event) {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveBttn(ActionEvent event) throws SQLException {

        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();
        String country = countryBox.getSelectionModel().getSelectedItem().toString();
        String division = divisionBox.getSelectionModel().getSelectedItem().toString();

        Customer customerUD = new Customer(customer.getID(), name, address, postalCode, phone, country, division);
        JDBC.updateCustomer(customerUD);
        Schedule.updateCustomer(customerUD, row);
        cancelBttn(event);
    }

}
