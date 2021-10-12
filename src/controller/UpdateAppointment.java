package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UpdateAppointment {

    @FXML
    private TextField IDtxt;

    @FXML
    private Button addBttn;

    @FXML
    private AnchorPane addOutsourced;

    @FXML
    private Button cancelBttn;

    @FXML
    private TableColumn<?, ?> countryCol;

    @FXML
    private TableColumn<?, ?> custIDCol;

    @FXML
    private TableColumn<?, ?> custNameCol;

    @FXML
    private Button deleteBttn;

    @FXML
    private TableColumn<?, ?> divisionCol;

    @FXML
    private TextField invTxt;

    @FXML
    private TableColumn<?, ?> lowCountryCol;

    @FXML
    private TableColumn<?, ?> lowCustIDCol;

    @FXML
    private TableColumn<?, ?> lowCustNameCol;

    @FXML
    private TableColumn<?, ?> lowDivisionCol;

    @FXML
    private TableView<?> lowerTable;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField maxTxt1;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField minTxt1;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField priceTxt1;

    @FXML
    private TextField priceTxt11;

    @FXML
    private Button saveBttn;

    @FXML
    private Button searchProductBttnsearchProductBttn;

    @FXML
    private TextField searchTxt;

    @FXML
    private TableView<?> upperTable;

    @FXML
    void addBttn(ActionEvent event) {

    }

    @FXML
    void cancelBttn(ActionEvent event) {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteBttn(ActionEvent event) {

    }

    @FXML
    void saveBttn(ActionEvent event) {

    }

    @FXML
    void searchProductBttn(ActionEvent event) {

    }

}
