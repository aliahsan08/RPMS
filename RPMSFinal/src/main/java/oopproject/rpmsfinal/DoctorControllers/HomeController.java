package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    // FXML buttons for various features in the doctor's home screen
    @FXML private Button profileButton;
    @FXML private Button appointmentsButton;
    @FXML private Button vitalsButton;
    @FXML private Button logoutButton;
    @FXML private Button videoCallButton;
    @FXML private Button patientsButton;
    @FXML private Button addFeedbackButton;
    @FXML private Button chatButton;

    /**
     * Handles navigation to the Profile screen when the profile button is clicked.
     */
    @FXML
    private void handleProfileButton() {
        try {
            // Load the Profile screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Profile.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) profileButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Profile screen)
        } catch (IOException e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }

    /**
     * Handles navigation to the Appointments screen when the appointments button is clicked.
     */
    @FXML
    private void handleAppointmentsButton() {
        try {
            // Load the Appointments screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Appointments.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) appointmentsButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Appointments screen)
        } catch (IOException e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }

    /**
     * Handles navigation to the Vitals screen when the vitals button is clicked.
     */
    @FXML
    private void handleVitalsButton() {
        try {
            // Load the Vitals screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Vitals.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) vitalsButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Vitals screen)
        } catch (IOException e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }

    /**
     * Handles logging out of the system when the logout button is clicked.
     */
    @FXML
    private void handleLogoutButton() {
        try {
            // Load the Login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/Login.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Login screen)
        } catch (IOException e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }

    /**
     * Handles navigation to the Video Call screen when the video call button is clicked.
     */
    @FXML
    private void handleVideoCallButton() {
        try {
            // Load the Video Call screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/VideoCall.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) videoCallButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Video Call screen)
        } catch (IOException e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }

    /**
     * Handles navigation to the Patients screen when the patients button is clicked.
     */
    @FXML
    private void handlePatientsButton() {
        try {
            // Load the Patients screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Patients.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) patientsButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Patients screen)
        } catch (IOException e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }

    /**
     * Handles navigation to the Add Feedback screen when the add feedback button is clicked.
     */
    @FXML
    private void handleAddFeedbackButton() {
        try {
            // Load the Add Feedback screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/AddFeedback.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) addFeedbackButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Add Feedback screen)
        } catch (IOException e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }

    /**
     * Handles navigation to the Chat screen when the chat button is clicked.
     */
    @FXML
    private void handleChatButton() {
        try {
            // Load the Chat screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Chat.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) chatButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Chat screen)
        } catch (IOException e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }
}
