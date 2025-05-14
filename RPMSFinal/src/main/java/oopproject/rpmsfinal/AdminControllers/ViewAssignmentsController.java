package oopproject.rpmsfinal.AdminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import oopproject.rpmsfinal.DBConnection;
import oopproject.rpmsfinal.DPAssignment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewAssignmentsController {

    // FXML UI elements for the back button and table to display assignments
    @FXML private Button backButton;
    @FXML private TableView<DPAssignment> assignmentTable;
    @FXML private TableColumn<DPAssignment, Integer> doctorIdCol;
    @FXML private TableColumn<DPAssignment, Integer> patientIdCol;

    /**
     * Initializes the table and populates it with doctor-patient assignment data from the database.
     */
    @FXML
    public void initialize() {
        // Set the cell value factories for the doctor and patient ID columns
        doctorIdCol.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        patientIdCol.setCellValueFactory(new PropertyValueFactory<>("patientId"));

        // Create an observable list to hold assignment data
        ObservableList<DPAssignment> assignments = FXCollections.observableArrayList();

        // Query the database to retrieve doctor-patient assignments
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT doctor_id, patient_id FROM doctor_patient_assignments")) {

            // Process each row in the result set and add to the assignments list
            while (rs.next()) {
                String doctorId = rs.getString("doctor_id");
                String patientId = rs.getString("patient_id");
                assignments.add(new DPAssignment(doctorId, patientId));
            }

            // Set the table's items to the assignments list
            assignmentTable.setItems(assignments);

        } catch (Exception e) {
            e.printStackTrace(); // Handle any database exceptions
        }
    }

    /**
     * Navigates back to the previous screen when the back button is clicked.
     */
    @FXML
    private void goBack() {
        try {
            // Load the PatientDoctorAssignment screen
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/PatientDoctorAssignment.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (PatientDoctorAssignment screen)
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions that occur while navigating
        }
    }
}
