package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.JDBC;
import model.Schedule;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Login {

        @FXML
        private Button loginBttn;

        @FXML
        private TextField passwordTxt;

        @FXML
        private TextField userTxt;

        @FXML
        private Label zoneTxt;

        public void initialize() {
            String zone = ZoneId.systemDefault().getId();
            String lang = System.getProperty("user.language");
            zoneTxt.setText(zone);
        }

        @FXML
        void loginBttn(ActionEvent event) throws IOException, SQLException {
            Alert loginAlert = new Alert(Alert.AlertType.ERROR, "Username and/or password field is blank. " +
                    "Enter username and password to continue.");
            if (userTxt.getText().isBlank() || passwordTxt.getText().isBlank()) {
                loginAlert.showAndWait();
                return;
            }

            boolean authenticated = JDBC.authenticate(userTxt.getText(), passwordTxt.getText());
            if (authenticated) {
                Parent root = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Appointment Manager");
                stage.setScene(new Scene(root, 1100, 435));
                stage.show();

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime quarterAfter = LocalDateTime.now().plusMinutes(15);

                //upcoming appointment alert
                for (Appointment appointment : Schedule.getAllAppointments()) {
                    long minutes = ChronoUnit.MINUTES.between(now, appointment.getStartDate());
                    if (appointment.getStartDate().isBefore(quarterAfter) && appointment.getStartDate().isAfter(now)) {
                        Alert alert = new Alert(Alert.AlertType.NONE, "Appointment titled '" + appointment.getTitle()
                                + "' will begin in " + minutes + " minutes.");

                        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        alert.showAndWait();
                    }
                }

                Stage primaryStage = (Stage) loginBttn.getScene().getWindow();
                primaryStage.close();
            }
        }
}
