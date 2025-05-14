package oopproject.rpmsfinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class UserTypeController {

    @FXML
    private Button patientButton;
    @FXML
    private Button doctorButton;
    @FXML
    private Button adminButton;

    @FXML
    public void initialize() {
        // Initialization logic if needed
    }

    @FXML
    private void handlePatientButton() {
        try {
            // Set user type to Patient
            SessionNative.setUserType("Patient");
            // Load login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            StackPane root = loader.load();
            // Switch to the login scene
            Stage stage = (Stage) patientButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            // Print error if loading fails
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDoctorButton() {
        try {
            // Set user type to Doctor
            SessionNative.setUserType("Doctor");
            // Load login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            StackPane root = loader.load();
            // Switch to the login scene
            Stage stage = (Stage) doctorButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminButton() {
        try {
            // Set user type to Administrator
            SessionNative.setUserType("Administrator");
            // Load login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            StackPane root = loader.load();
            // Switch to the login scene
            Stage stage = (Stage) adminButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
