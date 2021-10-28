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

import java.sql.SQLException;

public class AddCustomer {

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
     * sets choices for Country in the Country ComboBox
     * @throws SQLException handles SQL exceptions
     */
    public void initialize() throws SQLException {
        countryBox.getItems().clear();
        countryBox.getItems().addAll(JDBC.loadLCountries());
    }

    /**
     * choosing a country filters first level divisions
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
     * saves customer info to database
     * @param event executes method when button is clicked
     * @throws SQLException handles SQL exceptions
     */
    @FXML
    void saveBttn(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Country and/or Division must be selected to save customer record.");

        int ID = 0;

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

        Customer customer = new Customer(ID, name, address, postalCode, phone, country, division);
        JDBC.saveCustomer(customer);
        cancelBttn(event);
    }

}
