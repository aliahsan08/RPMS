package oopproject.rpmsfinal.AdminControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oopproject.rpmsfinal.SessionNative;

import java.io.IOException;

public class NotificationsController {

    // FXML UI elements
    @FXML private Button backButton;        // Button to navigate back to the Home screen
    @FXML private Button usersButton;       // Button to navigate to notify users
    @FXML private Button patientsButton;    // Button to navigate to notify patients
    @FXML private Button doctorsButton;     // Button to navigate to notify doctors
    @FXML private Button specificUserButton; // Button to navigate to notify a specific user

    /**
     * Navigates back to the Home screen when the back button is clicked.
     */
    @FXML
    private void goBackHome() {
        try {
            // Load the Home screen FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (Home screen)
        } catch (Exception e) {
            // Handle any exceptions while loading the Home screen
            e.printStackTrace();
        }
    }

    /**
     * Sets the user type to "user" and navigates to the Multiple Users notification screen.
     */
    @FXML
    private void handleUsersButton() {
        try {
            // Set the notify user type to "user"
            SessionNative.setNotifyUserType("user");

            // Load the Multiple Users notification screen FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/MultipleUsers.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) usersButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (Multiple Users screen)
        } catch (IOException e) {
            // Handle any exceptions while loading the Multiple Users screen
            e.printStackTrace();
        }
    }

    /**
     * Sets the user type to "patient" and navigates to the Multiple Users notification screen.
     */
    @FXML
    private void handlePatientsButton() {
        try {
            // Set the notify user type to "patient"
            SessionNative.setNotifyUserType("patient");

            // Load the Multiple Users notification screen FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/MultipleUsers.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) patientsButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (Multiple Users screen)
        } catch (IOException e) {
            // Handle any exceptions while loading the Multiple Users screen
            e.printStackTrace();
        }
    }

    /**
     * Sets the user type to "doctor" and navigates to the Multiple Users notification screen.
     */
    @FXML
    private void handleDoctorsButton() {
        try {
            // Set the notify user type to "doctor"
            SessionNative.setNotifyUserType("doctor");

            // Load the Multiple Users notification screen FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/MultipleUsers.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) doctorsButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (Multiple Users screen)
        } catch (IOException e) {
            // Handle any exceptions while loading the Multiple Users screen
            e.printStackTrace();
        }
    }

    /**
     * Sets the user type to "specificUser" and navigates to the Specific User notification screen.
     */
    @FXML
    private void handleSpecificUserButton() {
        try {
            // Set the notify user type to "specificUser"
            SessionNative.setNotifyUserType("specificUser");

            // Load the Specific User notification screen FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/specificUser.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) specificUserButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (Specific User screen)
        } catch (IOException e) {
            // Handle any exceptions while loading the Specific User screen
            e.printStackTrace();
        }
    }
}
