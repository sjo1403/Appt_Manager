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

    public void initialize() throws SQLException {
        countryBox.getItems().clear();
        countryBox.getItems().addAll(JDBC.loadLCountries());
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
        int ID = 0;

        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();
        String country = countryBox.getSelectionModel().getSelectedItem().toString();
        String division = divisionBox.getSelectionModel().getSelectedItem().toString();

        Customer customer = new Customer(ID, name, address, postalCode, phone, country, division);
        JDBC.saveCustomer(customer);
        cancelBttn(event);
    }

}
