package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oopproject.rpmsfinal.DBConnection;
import oopproject.rpmsfinal.Doctor;
import oopproject.rpmsfinal.DoctorPatient.DoctorFeedback;
import oopproject.rpmsfinal.SessionNative;
import java.sql.*;

public class AddFeedbackController {

    // FXML UI elements (buttons and input fields)
    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField patientId;
    @FXML
    private TextField prescription;
    @FXML
    private TextField recommendation;
    @FXML
    private TextField diagnosis;
    @FXML
    private Text feedbackText;

    // The current doctor, retrieved from the session
    Doctor doctor = (Doctor) SessionNative.getCurrentUser();

    /**
     * Initializes the controller, can be expanded if needed.
     */
    @FXML
    public void initialize() {
        // Initialization logic can go here, if needed
    }

    /**
     * Navigates back to the Home screen when the back button is clicked.
     */
    @FXML
    private void goBack() {
        try {
            // Load the Home screen
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Home screen)
        } catch (Exception e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }

    /**
     * Handles the Add button click to submit the feedback.
     * It validates if the doctor is assigned to the patient,
     * and inserts the feedback into the database.
     */
    @FXML
    private void handleAddButton() {
        String doctorId = doctor.getUserId(); // Get current doctor's ID
        String patientIdText = patientId.getText(); // Get patient ID from the input field

        // Query to check if the doctor is assigned to the patient
        String checkQuery = "SELECT * FROM doctor_patient_assignments WHERE doctor_id = ? AND patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            // Set parameters for the query
            checkStmt.setString(1, doctorId);
            checkStmt.setString(2, patientIdText);

            // Execute the query and check if any result is returned
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                // If doctor is not assigned to the patient, display an error message
                feedbackText.setText("Doctor is not assigned to this patient.");
                return;
            }

            // 2. Create feedback object with the gathered information
            DoctorFeedback feedback = new DoctorFeedback(
                    doctorId,
                    patientIdText,
                    diagnosis.getText(), // Get diagnosis from the input field
                    recommendation.getText(), // Get recommendations from the input field
                    prescription.getText() // Get prescription from the input field
            );

            // 3. Insert feedback into the database
            String insertQuery = "INSERT INTO doctor_feedback (feedback_id, doctor_id, patient_id, timestamp, diagnosis, recommendations, prescription) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, feedback.getFeedbackId()); // Feedback ID
                insertStmt.setString(2, feedback.getDoctorId()); // Doctor ID
                insertStmt.setString(3, feedback.getPatientId()); // Patient ID
                insertStmt.setTimestamp(4, Timestamp.valueOf(feedback.getTimestamp())); // Timestamp
                insertStmt.setString(5, feedback.getDiagnosis()); // Diagnosis
                insertStmt.setString(6, feedback.getRecommendations()); // Recommendations
                insertStmt.setString(7, feedback.getPrescription()); // Prescription

                // Execute the insert query
                insertStmt.executeUpdate();
                feedbackText.setText("Feedback added successfully!"); // Success message
            }

        } catch (Exception e) {
            e.printStackTrace(); // Print error if something goes wrong
            feedbackText.setText("Error occurred while submitting feedback."); // Display error message
        }
    }
}
