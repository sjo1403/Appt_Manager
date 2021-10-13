package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.Schedule;

public class AddCustomer {

    @FXML
    private TextField IDtxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private Button cancelBttn;

    @FXML
    private TextField countryTxt;

    @FXML
    private TextField divisonTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalTxt;

    @FXML
    private Button saveBttn;

    @FXML
    void cancelBttn(ActionEvent event) {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveBttn(ActionEvent event) {
        int ID = (int) (Math.random()*(90000)+100000);

        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();
        String country = countryTxt.getText();
        String division = divisonTxt.getText();

        Customer customer = new Customer(ID, name, address, postalCode, phone, country, division);
        Schedule.addCustomer(customer);
        cancelBttn(event);
    }

}
