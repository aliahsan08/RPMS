package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UsersController {

    // FXML UI elements for buttons to navigate to different user management screens
    @FXML private Button backButton;
    @FXML private Button viewButton;
    @FXML private Button createButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;

    /**
     * Initializes the controller. Currently not used for specific initializations.
     */
    @FXML
    public void initialize() {
        // Initialization logic can go here if needed in the future
    }

    /**
     * Navigates back to the home screen when the back button is clicked.
     */
    @FXML
    private void goBackHome() {
        try {
            // Load the Home screen layout
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Home screen)
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions that occur while loading the home screen
        }
    }

    /**
     * Handles the "View Users" button click, navigating to the "View Users" screen.
     */
    @FXML
    private void handleViewButton() {
        try {
            // Load the View Users screen layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/ViewUsers.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) viewButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (View Users screen)
        } catch (IOException e) {
            e.printStackTrace(); // Handle any exceptions that occur while loading the view users screen
        }
    }

    /**
     * Handles the "Update User" button click, navigating to the "Update User" screen.
     */
    @FXML
    private void handleUpdateButton() {
        try {
            // Load the Update User screen layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/UpdateUser.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) updateButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Update User screen)
        } catch (IOException e) {
            e.printStackTrace(); // Handle any exceptions that occur while loading the update user screen
        }
    }

    /**
     * Handles the "Create User" button click, navigating to the "Create User" screen.
     */
    @FXML
    private void handleCreateButton() {
        try {
            // Load the Create User screen layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/CreateUser.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) createButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Create User screen)
        } catch (IOException e) {
            e.printStackTrace(); // Handle any exceptions that occur while loading the create user screen
        }
    }

    /**
     * Handles the "Delete User" button click, navigating to the "Delete User" screen.
     */
    @FXML
    private void handleDeleteButton() {
        try {
            // Load the Delete User screen layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/DeleteUser.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) deleteButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Delete User screen)
        } catch (IOException e) {
            e.printStackTrace(); // Handle any exceptions that occur while loading the delete user screen
        }
    }
}
