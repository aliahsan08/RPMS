package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    // FXML UI elements for buttons
    @FXML private Button profileButton;        // Button to navigate to the profile page
    @FXML private Button reportsButton;        // Button to navigate to the reports page
    @FXML private Button logoutButton;         // Button to log out of the application
    @FXML private Button notificationButton;   // Button to navigate to the notifications page
    @FXML private Button usersButton;          // Button to navigate to the users management page
    @FXML private Button assignmentButton;     // Button to navigate to the patient-doctor assignment page

    /**
     * Handles the event when the profile button is clicked.
     * Navigates the user to the profile page.
     */
    @FXML
    private void handleProfileButton() {
        try {
            // Load the Profile page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Profile.fxml"));
            StackPane root = loader.load(); // Load the FXML into a StackPane
            Stage stage = (Stage) profileButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the scene to the Profile page
        } catch (IOException e) {
            // Handle any exceptions while loading the Profile page
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the reports button is clicked.
     * Navigates the user to the reports page.
     */
    @FXML
    private void handleReportsButton() {
        try {
            // Load the Reports page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Reports.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) reportsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            // Handle any exceptions while loading the Reports page
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the logout button is clicked.
     * Logs the user out and navigates to the login page.
     */
    @FXML
    private void handleLogoutButton() {
        try {
            // Load the Login page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/Login.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            // Handle any exceptions while loading the Login page
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the notifications button is clicked.
     * Navigates the user to the notifications page.
     */
    @FXML
    private void handleNotificationsButton() {
        try {
            // Load the Notifications page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Notifications.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) notificationButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            // Handle any exceptions while loading the Notifications page
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the users button is clicked.
     * Navigates the user to the users management page.
     */
    @FXML
    private void handleUsersButton() {
        try {
            // Load the Users page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Users.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) usersButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            // Handle any exceptions while loading the Users page
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the assignment button is clicked.
     * Navigates the user to the patient-doctor assignment page.
     */
    @FXML
    private void handleAssignmentButton() {
        try {
            // Load the Patient-Doctor Assignment page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/PatientDoctorAssignment.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) assignmentButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            // Handle any exceptions while loading the Patient-Doctor Assignment page
            e.printStackTrace();
        }
    }
}
