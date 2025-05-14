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
import oopproject.rpmsfinal.Patient;

import java.sql.*;

public class CreatePatientController extends CreateUserController {

    // FXML UI elements for the form fields and buttons
    @FXML private Button backButton;          // Button to navigate back to the previous screen
    @FXML private Button createButton;        // Button to create a new patient
    @FXML private Text createText;            // Text to display success or error messages
    @FXML private TextField assignedDoctorField; // TextField to input the assigned doctor ID
    @FXML private Text displayOptions;        // Text to display available doctors for assignment

    /**
     * Initializes the controller by loading the list of doctors from the database
     * and displaying them in the options area.
     */
    @FXML
    public void initialize() {
        String options = "";

        String query = "SELECT user_id, name, specialization FROM doctors";  // Query to get doctors' details

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Loop through each doctor and format the information to display
            int i = 1;
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");

                // Format the doctor's information and add it to the options string
                options += String.format("%s Dr.  %s (%s)\n", userId, name, specialization);
            }

            // Display the list of doctors in the UI
            displayOptions.setText(options);

        } catch (SQLException e) {
            // If there's an error, display an error message
            e.printStackTrace();
            displayOptions.setText("Error loading doctor list.");
        }
    }

    /**
     * Handles the event when the back button is clicked.
     * Navigates back to the CreateUser page.
     */
    @FXML
    private void goBack() {
        try {
            // Load the CreateUser page and set it as the current scene
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/CreateUser.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace(); // Handle any errors while navigating
        }
    }

    /**
     * Handles the event when the "Create Patient" button is clicked.
     * Creates a new patient in the database and assigns the selected doctor.
     */
    @FXML
    private void handleCreateButton() {
        try {
            String assignedDoctor = assignedDoctorField.getText().trim();  // Get the assigned doctor's ID
            if (assignedDoctor.isEmpty()) {
                // If no doctor is assigned, display an error message
                createText.setText("Assigned doctor is required.");
                return;
            }

            // Create a new Patient object with the provided data
            Patient patient = new Patient(usernameString, passwordString, userIdString, nameString,
                    ageInt, genderString, phoneString, emailString, assignedDoctor);

            try (Connection conn = DBConnection.getConnection()) {
                // Insert the new patient into the 'patients' table
                String sqlPatient = "INSERT INTO patients (user_id, username, password, name, age, gender, phone, email, assigned_doctor) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sqlPatient)) {
                    stmt.setString(1, patient.getUserId());
                    stmt.setString(2, patient.getUsername());
                    stmt.setString(3, patient.getPassword());
                    stmt.setString(4, patient.getName());
                    stmt.setInt(5, patient.getAge());
                    stmt.setString(6, patient.getGender());
                    stmt.setString(7, patient.getPhone());
                    stmt.setString(8, patient.getEmail());
                    stmt.setString(9, patient.getAssignedDoctor());
                    stmt.executeUpdate(); // Execute the insertion query
                }

                // Insert the doctor-patient assignment into the 'doctor_patient_assignments' table
                String sqlAssignment = "INSERT INTO doctor_patient_assignments (doctor_id, patient_id) VALUES (?, ?)";
                try (PreparedStatement assignStmt = conn.prepareStatement(sqlAssignment)) {
                    assignStmt.setString(1, assignedDoctor);
                    assignStmt.setString(2, patient.getUserId());
                    assignStmt.executeUpdate(); // Execute the assignment query
                }
            }

            // Display a success message
            createText.setText("Patient created! ID: " + patient.getUserId());

        } catch (Exception e) {
            // If an error occurs, display an error message
            createText.setText("Error creating patient.");
            e.printStackTrace();
        }
    }
}
