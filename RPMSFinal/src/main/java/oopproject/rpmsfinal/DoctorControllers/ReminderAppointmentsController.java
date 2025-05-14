package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oopproject.rpmsfinal.DBConnection;
import oopproject.rpmsfinal.EmailNotification;
import oopproject.rpmsfinal.Patient;
import oopproject.rpmsfinal.UserManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReminderAppointmentsController {

    EmailNotification emailer = EmailNotification.getInstance(); // Singleton instance for sending email notifications
    private UserManager userManager = UserManager.getInstance(); // Singleton instance to manage users
    @FXML private TextField userIdField; // TextField to input patient ID
    @FXML private TextField messageArea; // TextField to input reminder message
    @FXML private Text reminderText; // Text element to display feedback
    @FXML private Button remindButton; // Button to send reminder
    @FXML private Button backButton; // Button to go back to the previous screen

    /**
     * Handles the logic for sending a reminder email to the patient.
     */
    @FXML
    private void handleRemindButton() {
        // Retrieve patient ID and message from the input fields
        String userId = userIdField.getText().trim();
        String message = messageArea.getText().trim();

        // Check if both fields are filled
        if (userId.isEmpty() || message.isEmpty()) {
            reminderText.setText("Please fill all fields."); // Display error if fields are empty
            return;
        }

        // Fetch the patient's email address
        String email = fetchUserEmail(userId);
        if (email == null) {
            reminderText.setText("User not found."); // Display error if user is not found
            return;
        }

        // Send reminder email
        emailer.sendNotification(email, "Reminder for Appointment", message);
        reminderText.setText("Reminder sent to patient ID: " + userId); // Display success message
    }

    /**
     * Fetches the email address of a patient based on their user ID.
     * @param userId the patient's ID
     * @return the email address of the patient, or null if the patient does not exist
     */
    private String fetchUserEmail(String userId) {
        Patient patient = userManager.getPatientById(userId); // Get the patient object by ID
        return patient != null ? patient.getEmail() : null; // Return the patient's email, or null if not found
    }

    /**
     * Navigates the user back to the appointments screen.
     */
    @FXML
    private void goBack() {
        try {
            // Load the appointments page
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Appointments.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (appointments page)
        } catch (IOException e) {
            e.printStackTrace(); // Handle any I/O exceptions
        }
    }
}
