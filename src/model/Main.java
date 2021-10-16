package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        primaryStage.setTitle("Appointment Manager");
        primaryStage.setScene(new Scene(root, 1100, 435));
        primaryStage.show();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime quarterAfter = LocalDateTime.now().plusMinutes(15);

        //upcoming appointment alert
        for (Appointment appointment : Schedule.getAllAppointments()) {
            long minutes = ChronoUnit.MINUTES.between(now, appointment.getStartDate());
            if ( appointment.getStartDate().isBefore(quarterAfter) && appointment.getStartDate().isAfter(now) ) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Appointment titled '" + appointment.getTitle()
                        + "' will begin in " + minutes + " minutes.");

                alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                alert.showAndWait();
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
