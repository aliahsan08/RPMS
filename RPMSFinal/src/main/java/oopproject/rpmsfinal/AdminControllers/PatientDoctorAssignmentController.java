package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class PatientDoctorAssignmentController {

    // FXML UI elements
    @FXML private Button backButton;        // Button to navigate back to the Home screen
    @FXML private Button assignButton;      // Button to navigate to the Assign Patient to Doctor screen
    @FXML private Button viewButton;        // Button to navigate to the View Assignments screen
    @FXML private Button removeButton;      // Button to navigate to the Remove Assignment screen

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
     * Navigates to the Assign Patient to Doctor screen when the assign button is clicked.
     */
    @FXML
    private void handleAssignButton() {
        try {
            // Load the Assign Patient to Doctor screen FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/AssignPtoD.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) assignButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (Assign Patient to Doctor screen)
        } catch (IOException e) {
            // Handle any exceptions while loading the Assign Patient to Doctor screen
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the View Assignments screen when the view button is clicked.
     */
    @FXML
    private void handleViewButton() {
        try {
            // Load the View Assignments screen FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/ViewAssignments.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) viewButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (View Assignments screen)
        } catch (IOException e) {
            // Handle any exceptions while loading the View Assignments screen
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the Remove Assignment screen when the remove button is clicked.
     */
    @FXML
    private void handleRemoveButton() {
        try {
            // Load the Remove Assignment screen FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/RemoveAssignment.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) removeButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (Remove Assignment screen)
        } catch (IOException e) {
            // Handle any exceptions while loading the Remove Assignment screen
            e.printStackTrace();
        }
    }
}
