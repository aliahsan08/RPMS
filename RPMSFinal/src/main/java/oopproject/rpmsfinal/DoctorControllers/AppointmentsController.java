package oopproject.rpmsfinal.DoctorControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentsController {

    // FXML UI elements (buttons)
    @FXML private Button backButton;
    @FXML private Button reviewButton;
    @FXML private Button reminderButton;

    /**
     * Navigates back to the Home screen when the back button is clicked.
     */
    @FXML
    private void goBackHome() {
        try {
            // Load the Home screen
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Home screen)
        } catch (Exception e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }

    /**
     * Handles the Review button click to navigate to the ReviewAppointments screen.
     */
    @FXML
    private void handleReviewButton() {
        try {
            // Load the ReviewAppointments screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/ReviewAppointments.fxml"));
            StackPane root = loader.load(); // Load the screen layout
            Stage stage = (Stage) reviewButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (ReviewAppointments screen)
        } catch (IOException e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }

    /**
     * Handles the Reminder button click to navigate to the ReminderAppointments screen.
     */
    @FXML
    private void handleReminderButton() {
        try {
            // Load the ReminderAppointments screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/ReminderAppointments.fxml"));
            StackPane root = loader.load(); // Load the screen layout
            Stage stage = (Stage) reminderButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (ReminderAppointments screen)
        } catch (IOException e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }
}
