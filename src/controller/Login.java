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
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Login {

        private String language = "";

        @FXML
        private Button loginBttn;

        @FXML
        private Label passwordLabel;

        @FXML
        private Label userLabel;

        @FXML
        private TextField passwordTxt;

        @FXML
        private TextField userTxt;

        @FXML
        private Label zoneTxt;

    /**
     * Sets UI language (English or French), and displays ZoneID
     */
    public void initialize() {
            String zone = ZoneId.systemDefault().getId();
            zoneTxt.setText(zone);
            String language = Locale.getDefault().getLanguage();

            if (!language.equals("en")) {
                userLabel.setText("Nom d'utilisateur");
                passwordLabel.setText("le mot de passe");
                loginBttn.setText("connexion");
            }
        }

    /**
     * used to authenticate login credentials, logs login attempts
     * @param event method executes when login button clicked
     * @throws IOException handles IO exceptions
     * @throws SQLException handles SQL exceptions
     */
        @FXML
        void loginBttn(ActionEvent event) throws IOException, SQLException {
            if (!language.equals("en")) {
                Alert loginAlert = new Alert(Alert.AlertType.ERROR, "Le champ du nom d'utilisateur et/ou du mot de passe est vide. " +
                        "Entrez le nom d'utilisateur et le mot de passe pour continuer.");
                if (userTxt.getText().isBlank() || passwordTxt.getText().isBlank()) {
                    loginAlert.showAndWait();
                    return;
                }
            }

            else {
                Alert loginAlert = new Alert(Alert.AlertType.ERROR, "Username and/or password field is blank. " +
                        "Enter username and password to continue.");
                if (userTxt.getText().isBlank() || passwordTxt.getText().isBlank()) {
                    loginAlert.showAndWait();
                    return;
                }
            }

            boolean authenticated = JDBC.authenticate(userTxt.getText(), passwordTxt.getText(), language);
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
                        if (!language.equals("en")) {
                            Alert alert = new Alert(Alert.AlertType.NONE, "Rendez-vous intitulé '" + appointment.getTitle()
                                    + "' commencera dans " + minutes + " minutes.");

                            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                            alert.showAndWait();
                        }

                        else {
                            Alert alert = new Alert(Alert.AlertType.NONE, "Appointment titled '" + appointment.getTitle()
                                    + "' will begin in " + minutes + " minutes.");

                            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                }

                Stage primaryStage = (Stage) loginBttn.getScene().getWindow();
                primaryStage.close();
            }

            try {
                OutputStream outputStream = new FileOutputStream("./login_activity.txt", true);
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

                String line = "\nUser: " + userTxt.getText() + "\t\t" + LocalDateTime.now() +
                        "\t\tSuccessful Login: " + authenticated;

                writer.append(line);
                writer.close();

            } catch (IOException e) {
                System.out.println(e);
            }
        }
}
