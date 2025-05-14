package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.Administrator;
import oopproject.rpmsfinal.Patient;
import oopproject.rpmsfinal.SessionNative;

public class ProfileController {

    // FXML UI elements
    @FXML private Button backButton;        // Button to navigate back to the Home screen
    @FXML private Text profileText;         // Text element to display profile information

    /**
     * Initializes the profile screen by loading the current logged-in user's information.
     * The user is assumed to be an Administrator based on this implementation.
     */
    @FXML
    public void initialize() {
        // Retrieve the current logged-in user from the session
        Administrator admin = (Administrator) SessionNative.getCurrentUser();

        // Display the administrator's details by calling toString() method
        profileText.setText(admin.toString());
    }

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
}
