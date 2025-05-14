package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsController {

    // FXML UI elements for buttons
    @FXML private Button backButton;              // Button to navigate back to the home screen
    @FXML private Button userStatsButton;         // Button to navigate to user statistics
    @FXML private Button aptSummaryButton;        // Button to navigate to appointment summary
    @FXML private Button emergencyAlertSummaryButton;  // Button to navigate to emergency alert summary

    @FXML
    public void initialize() {
        // Initialization logic can go here (currently not required)
    }

    /**
     * Navigates back to the home screen when the back button is clicked.
     */
    @FXML
    private void goBackHome() {
        try {
            // Load the Home screen layout
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root)); // Set the new scene (Home screen)
        } catch (Exception e) {
            e.printStackTrace(); // Print any exception that occurs while loading the Home screen
        }
    }

    /**
     * Navigates to the User Stats screen when the user statistics button is clicked.
     */
    @FXML
    private void handleUserStatsButton() {
        try {
            // Load the User Stats screen layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/UserStats.fxml"));
            StackPane root = loader.load(); // Load the screen into a StackPane
            Stage stage = (Stage) userStatsButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (User Stats screen)
        } catch (IOException e) {
            e.printStackTrace(); // Handle any I/O exceptions
        }
    }

    /**
     * Navigates to the Appointment Summary screen when the appointment summary button is clicked.
     */
    @FXML
    private void handleAptSummaryButton() {
        try {
            // Load the Appointment Summary screen layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/AptSummary.fxml"));
            StackPane root = loader.load(); // Load the screen into a StackPane
            Stage stage = (Stage) aptSummaryButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Appointment Summary screen)
        } catch (IOException e) {
            e.printStackTrace(); // Handle any I/O exceptions
        }
    }
}
