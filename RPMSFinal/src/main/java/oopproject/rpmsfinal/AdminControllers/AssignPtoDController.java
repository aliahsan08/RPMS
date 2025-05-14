package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oopproject.rpmsfinal.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AssignPtoDController {

    // FXML UI elements for form fields and buttons
    @FXML private Button backButton;        // Button to navigate back to the Patient-Doctor Assignment page
    @FXML private Button assignButton;      // Button to assign a patient to a doctor
    @FXML private TextField patientIdField; // TextField for entering the patient ID
    @FXML private TextField doctorIdField;  // TextField for entering the doctor ID
    @FXML private Text assignment;          // Text element to display messages (success/error)

    /**
     * Handles the event when the back button is clicked.
     * Navigates back to the Patient-Doctor Assignment page.
     */
    @FXML
    private void goBack() {
        try {
            // Load the PatientDoctorAssignment page and set it as the current scene
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/PatientDoctorAssignment.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace(); // Handle any errors while navigating
        }
    }

    /**
     * Handles the event when the "Assign" button is clicked.
     * Assigns a patient to a doctor in the database.
     */
    @FXML
    private void handleAssignButton() {
        // Get the patient ID and doctor ID from the input fields
        String patientId = patientIdField.getText().trim();
        String doctorId = doctorIdField.getText().trim();

        // Check if both fields are filled in
        if (patientId.isEmpty() || doctorId.isEmpty()) {
            // Display error message if any of the fields are empty
            assignment.setText("Both Patient ID and Doctor ID must be provided.");
            return;
        }

        try {
            // Establish a connection to the database
            Connection conn = DBConnection.getConnection();

            // SQL query to insert the patient-doctor assignment into the database
            String sql = "INSERT INTO doctor_patient_assignments (doctor_id, patient_id) VALUES (?, ?)";

            // Prepare the statement and set the parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, doctorId);
            stmt.setString(2, patientId);

            // Execute the update and check if the insertion was successful
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Display success message if the assignment was successful
                assignment.setText("Patient assigned to doctor successfully.");
            } else {
                // Display error message if the assignment failed
                assignment.setText("Failed to assign patient.");
            }

            // Close the statement and connection
            stmt.close();
            conn.close();
        } catch (Exception e) {
            // Handle any errors that occur during the database operation
            e.printStackTrace();
            assignment.setText("Error assigning patient to doctor.");
        }
    }
}
