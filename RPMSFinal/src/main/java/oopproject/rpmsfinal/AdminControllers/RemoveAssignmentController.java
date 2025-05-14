package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

import oopproject.rpmsfinal.DBConnection;

public class RemoveAssignmentController {

    // FXML UI elements for user input and display
    @FXML private Button backButton;              // Button to navigate back to the previous screen
    @FXML private TextField patientIdField;       // TextField to input the Patient ID
    @FXML private TextField doctorIdField;        // TextField to input the Doctor ID
    @FXML private Text assignment;                // Text to display assignment status messages
    @FXML private Button removeButton;            // Button to initiate assignment removal

    /**
     * Handles the removal of a doctor-patient assignment when the remove button is clicked.
     */
    @FXML
    private void handleRemoveButton() {
        // Get the entered Patient ID and Doctor ID from the fields
        String patientId = patientIdField.getText().trim();
        String doctorId = doctorIdField.getText().trim();

        // Check if both fields are filled
        if (patientId.isEmpty() || doctorId.isEmpty()) {
            assignment.setText("Please enter both Patient ID and Doctor ID.");
            return;
        }

        // Try to remove the assignment from the database
        try {
            Connection conn = DBConnection.getConnection(); // Get the database connection
            // SQL query to delete the assignment for the given doctor and patient IDs
            String sql = "DELETE FROM doctor_patient_assignments WHERE doctor_id = ? AND patient_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql); // Prepare the SQL statement
            pstmt.setString(1, doctorId); // Set the doctor ID parameter
            pstmt.setString(2, patientId); // Set the patient ID parameter

            // Execute the query and check how many rows were affected
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                assignment.setText("Assignment removed successfully."); // Success message
            } else {
                assignment.setText("No assignment found for the given IDs."); // Failure message
            }

        } catch (Exception e) {
            // Handle any errors that occur during the database operation
            assignment.setText("Error removing assignment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Navigates back to the Patient-Doctor Assignment screen when the back button is clicked.
     */
    @FXML
    private void goBack() {
        try {
            // Load the PatientDoctorAssignment screen layout
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/PatientDoctorAssignment.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (PatientDoctorAssignment screen)
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions while loading the previous screen
        }
    }
}
