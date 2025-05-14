package oopproject.rpmsfinal.DoctorControllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PatientsController {

    @FXML private TableView<PatientSummary> patientsTable; // Table view to display list of assigned patients
    @FXML private TableColumn<PatientSummary, String> pidCol; // Column to display patient ID
    @FXML private TableColumn<PatientSummary, String> pinCol; // Column to display patient name
    @FXML private Button backButton; // Button to navigate back to the home screen

    @FXML private Text patientsText; // Text field to display status or information

    Doctor doctor = (Doctor) SessionNative.getCurrentUser(); // Get the currently logged-in doctor from the session

    /**
     * Initializes the controller by setting up the table columns
     * and populating the patients list assigned to the doctor.
     */
    @FXML
    public void initialize() {
        // Set the value factory for each table column to display the patient details
        pidCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getUserId()));
        pinCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getName()));
        viewAssignedPatients(doctor); // Call method to fetch and display assigned patients
    }

    /**
     * Fetches and displays the list of patients assigned to the doctor.
     * @param doctor the currently logged-in doctor
     */
    private void viewAssignedPatients(Doctor doctor) {
        List<PatientSummary> summaries = new ArrayList<>(); // List to store patient summaries

        // SQL query to fetch patients assigned to the current doctor
        String query = """
        SELECT p.user_id, p.name
        FROM doctor_patient_assignments dpa
        JOIN patients p ON dpa.patient_id = p.user_id
        WHERE dpa.doctor_id = ?;
    """;

        try (Connection conn = DBConnection.getConnection(); // Establish DB connection
             PreparedStatement stmt = conn.prepareStatement(query)) { // Prepare SQL statement

            stmt.setString(1, doctor.getUserId()); // Set the doctor's user ID in the query
            ResultSet rs = stmt.executeQuery(); // Execute the query and get the result set

            // Iterate through the result set to build a list of PatientSummary objects
            while (rs.next()) {
                String patientId = rs.getString("user_id");
                String patientName = rs.getString("name");
                summaries.add(new PatientSummary(patientId, patientName)); // Add patient summary to the list
            }

            patientsTable.getItems().setAll(summaries); // Update the table with the list of patients

        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace in case of an error
        }
    }

    /**
     * Navigates back to the home screen when the back button is clicked.
     */
    @FXML
    private void goBackHome() {
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
