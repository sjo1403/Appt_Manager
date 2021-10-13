package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.Schedule;

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
    private TextField countryTxt;

    @FXML
    private TextField divisionTxt;

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

    public void initialize(){
        IDtxt.setText(Integer.toString(customer.getID()));
        nameTxt.setText(customer.getName());
        addressTxt.setText(customer.getAddress());
        postalTxt.setText(customer.getPostalCode());
        phoneTxt.setText(customer.getPhone());
        countryTxt.setText(customer.getCountry());
        divisionTxt.setText(customer.getDivision());
    }

    @FXML
    void cancelBttn(ActionEvent event) {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveBttn(ActionEvent event) {

        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();
        String country = countryTxt.getText();
        String division = divisionTxt.getText();

        Customer customerUD = new Customer(customer.getID(), name, address, postalCode, phone, country, division);
        Schedule.updateCustomer(customerUD, row);
        cancelBttn(event);
    }

}
