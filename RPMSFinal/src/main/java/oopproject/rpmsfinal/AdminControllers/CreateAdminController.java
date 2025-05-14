package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oopproject.rpmsfinal.Administrator;
import oopproject.rpmsfinal.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateAdminController extends CreateUserController {

    // FXML UI elements for form fields and buttons
    @FXML private Button backButton;            // Button to navigate back to the previous screen
    @FXML private Button createButton;          // Button to create a new administrator
    @FXML private Text createText;              // Text to display success or error messages
    @FXML private TextField roleField;          // TextField to input the administrator's role
    @FXML private TextField officialEmailField; // TextField to input the administrator's official email
    @FXML private TextField supervisorNameField; // TextField to input the administrator's supervisor name

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
     * Handles the event when the "Create Administrator" button is clicked.
     * Creates a new administrator in the database.
     */
    @FXML
    private void handleCreateButton() {
        try {
            // Get the role, official email, and supervisor name from the input fields
            String role = roleField.getText().trim();
            String officialEmail = officialEmailField.getText().trim();
            String supervisorName = supervisorNameField.getText().trim();

            // Validate the input fields
            if (role.isEmpty() || officialEmail.isEmpty() || supervisorName.isEmpty()) {
                // If any field is empty, display an error message
                createText.setText("All fields are required.");
                return;
            }

            // Create a new Administrator object with the provided data
            Administrator admin = new Administrator(usernameString, passwordString, userIdString, nameString,
                    ageInt, genderString, phoneString, emailString, role, officialEmail, supervisorName);

            // SQL query to insert the new administrator into the 'administrators' table
            String sql = "INSERT INTO administrators (user_id, username, password, name, age, gender, phone, email, role, official_email, supervisor_name) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Insert the new administrator into the database
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                // Set the parameters for the SQL statement
                stmt.setString(1, admin.getUserId());
                stmt.setString(2, admin.getUsername());
                stmt.setString(3, admin.getPassword());
                stmt.setString(4, admin.getName());
                stmt.setInt(5, admin.getAge());
                stmt.setString(6, admin.getGender());
                stmt.setString(7, admin.getPhone());
                stmt.setString(8, admin.getEmail());
                stmt.setString(9, admin.getRole());
                stmt.setString(10, admin.getOfficialEmail());
                stmt.setString(11, admin.getSupervisorName());

                // Execute the update to insert the new administrator
                stmt.executeUpdate();
            }

            // Display a success message with the new administrator's ID
            createText.setText("Administrator created! ID: " + admin.getUserId());

        } catch (Exception e) {
            // If an error occurs, display an error message
            createText.setText("Error creating administrator.");
            e.printStackTrace();
        }
    }
}
