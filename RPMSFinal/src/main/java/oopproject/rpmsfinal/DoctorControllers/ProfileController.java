package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.Doctor;
import oopproject.rpmsfinal.SessionNative;

public class ProfileController {

    @FXML
    private Button backButton; // Button to navigate back to the home screen

    @FXML
    private Text profileText; // Text area to display the doctor's profile information

    /**
     * Initializes the profile screen with the current doctor's details.
     */
    @FXML
    public void initialize() {
        Doctor doctor = (Doctor) SessionNative.getCurrentUser(); // Get the currently logged-in doctor from the session
        profileText.setText(doctor.toString()); // Display the doctor's profile details (from the Doctor class' toString method)
    }

    /**
     * Navigates back to the home screen when the back button is clicked.
     * @param event the action event triggered by clicking the back button
     */
    @FXML
    private void goBackHome(ActionEvent event) {
        try {
            // Load the home screen UI layout (FXML)
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (home screen)
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions encountered during the transition
        }
    }
}
