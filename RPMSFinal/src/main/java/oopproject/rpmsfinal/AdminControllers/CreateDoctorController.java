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
import oopproject.rpmsfinal.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateDoctorController extends CreateUserController {

    // FXML UI elements for form fields and buttons
    @FXML private Button backButton;           // Button to navigate back to the previous screen
    @FXML private Button createButton;         // Button to create a new doctor
    @FXML private Text createText;             // Text to display success or error messages
    @FXML private TextField specializationField; // TextField to input the doctor's specialization
    @FXML private TextField workingHoursField;  // TextField to input the doctor's working hours

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
     * Handles the event when the "Create Doctor" button is clicked.
     * Creates a new doctor in the database.
     */
    @FXML
    private void handleCreateButton() {
        try {
            // Get the specialization and working hours from the input fields
            String specialization = specializationField.getText().trim();
            String workingHours = workingHoursField.getText().trim();

            // Validate the input fields
            if (specialization.isEmpty() || workingHours.isEmpty()) {
                // If either field is empty, display an error message
                createText.setText("Specialization and working hours are required.");
                return;
            }

            // Create a new Doctor object with the provided data
            Doctor doctor = new Doctor(usernameString, passwordString, userIdString, nameString,
                    ageInt, genderString, phoneString, emailString, specialization, workingHours);

            // SQL query to insert the new doctor into the 'doctors' table
            String sql = "INSERT INTO doctors (user_id, username, password, name, age, gender, phone, email, specialization, working_hours) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Insert the new doctor into the database
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                // Set the parameters for the SQL statement
                stmt.setString(1, doctor.getUserId());
                stmt.setString(2, doctor.getUsername());
                stmt.setString(3, doctor.getPassword());
                stmt.setString(4, doctor.getName());
                stmt.setInt(5, doctor.getAge());
                stmt.setString(6, doctor.getGender());
                stmt.setString(7, doctor.getPhone());
                stmt.setString(8, doctor.getEmail());
                stmt.setString(9, doctor.getSpecialization());
                stmt.setString(10, doctor.getWorkingHours());

                // Execute the update to insert the new doctor
                stmt.executeUpdate();
            }

            // Display a success message with the new doctor's ID
            createText.setText("Doctor created! ID: " + doctor.getUserId());

        } catch (Exception e) {
            // If an error occurs, display an error message
            createText.setText("Error creating doctor.");
            e.printStackTrace();
        }
    }
}
