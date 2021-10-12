package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;
import model.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAppointment {

    @FXML
    private TextField IDtxt;

    @FXML
    private Button addBttn;

    @FXML
    private AnchorPane addOutsourced;

    @FXML
    private Button cancelBttn;

    @FXML
    private TextField contactTxt;

    @FXML
    private TableColumn<?, ?> countryCol;

    @FXML
    private TableColumn<?, ?> custIDCol;

    @FXML
    private TableColumn<?, ?> custNameCol;

    @FXML
    private Button deleteBttn;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private TableColumn<?, ?> divisionCol;

    @FXML
    private TextField endDateTxt;

    @FXML
    private TextField endTimeTxt;

    @FXML
    private TextField locationTxt;

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
    private Button saveBttn;

    @FXML
    private Button searchProductBttn;

    @FXML
    private TextField searchTxt;

    @FXML
    private TextField startDateTxt;

    @FXML
    private TextField startTimeTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

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
    void saveBttn(ActionEvent event) throws ParseException {
        String ID = IDtxt.getText();
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String contact = contactTxt.getText();
        String type = typeTxt.getText();

        //format dates and times
        String startDateS = startDateTxt.getText();
        Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDateS);
        String startTimeS = startTimeTxt.getText();
        Date startTime = new SimpleDateFormat("hh:mm").parse(startTimeS);
        String endDateS = endDateTxt.getText();
        Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDateS);
        String endTimeS = endTimeTxt.getText();
        Date endTime = new SimpleDateFormat("hh:mm").parse(endTimeS);


        Appointment appointment = new Appointment(ID,
                title,
                description,
                location,
                contact,
                type,
                startDate,
                startTime,
                endDate,
                endTime);
        Schedule.addAppointment(appointment);

        cancelBttn(event);
    }

    @FXML
    void searchProductBttn(ActionEvent event) {

    }

}
