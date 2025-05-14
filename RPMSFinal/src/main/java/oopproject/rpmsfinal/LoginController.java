package oopproject.rpmsfinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.sql.*;
import java.io.IOException;

public class LoginController {
    private static final UserManager userManager = UserManager.getInstance();
    @FXML
    private Text loginText;  // Text object to display login status messages
    @FXML
    private TextField usernameField;  // Input field for username

    @FXML
    private PasswordField passwordField;  // Input field for password

    @FXML
    private Button loginButton;  // Button for logging in
    @FXML
    private Button backButton;  // Button to go back to previous screen
    private String userType;  // Holds the user type (Patient, Doctor, Administrator)

    @FXML
    public void initialize() {
        userType = SessionNative.getUserType();  // Get the user type from the session
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();  // Get username from input field
        String password = passwordField.getText();  // Get password from input field
        SessionNative.setUsername(username);  // Store the username in the session

        if (username.isEmpty() || password.isEmpty()) {
            return;  // Do nothing if username or password is empty
        }

        String table = "";
        // Determine which table to query based on the user type
        if ("Patient".equals(userType)) {
            table = "patients";
        } else if ("Doctor".equals(userType)) {
            table = "doctors";
        } else if ("Administrator".equals(userType)) {
            table = "administrators";
        }

        // SQL query to validate user credentials
        String query = "SELECT * FROM " + table + " WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userId = rs.getString("user_id");  // Get user ID from result
                SessionNative.setUserId(userId);  // Store user ID in the session

                // Common fields for all user types
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                // Create user object based on user type
                if ("Patient".equals(userType)) {
                    String assignedDoctor = rs.getString("assigned_doctor");
                    Patient patient = new Patient(username, password, userId, name, age, gender, phone, email, assignedDoctor);
                    SessionNative.setCurrentUser(patient);  // Set current user in session
                } else if ("Doctor".equals(userType)) {
                    String specialization = rs.getString("specialization");
                    String workingHours = rs.getString("working_hours");
                    Doctor doctor = new Doctor(username, password, userId, name, age, gender, phone, email, specialization, workingHours);
                    SessionNative.setCurrentUser(doctor);
                } else if ("Administrator".equals(userType)) {
                    String role = rs.getString("role");
                    String officialEmail = rs.getString("official_email");
                    String supervisorName = rs.getString("supervisor_name");
                    Administrator admin = new Administrator(username, password, userId, name, age, gender, phone, email, role, officialEmail, supervisorName);
                    SessionNative.setCurrentUser(admin);
                }

                // Redirect to the corresponding home page based on user type
                String fxmlPath = null;
                if ("Patient".equals(userType)) {
                    fxmlPath = "PatientFiles/Home.fxml";
                } else if ("Doctor".equals(userType)) {
                    fxmlPath = "DoctorFiles/Home.fxml";
                } else if ("Administrator".equals(userType)) {
                    fxmlPath = "AdminFiles/Home.fxml";
                }

                if (fxmlPath != null) {
                    Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(new Scene(root));  // Set the new scene for the appropriate home page
                }

            } else {
                loginText.setText("Invalid Credentials");  // Display error message if credentials are incorrect
            }

        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
            loginText.setText("Invalid Credentials");  // Display error message if an exception occurs
        }
    }

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("UserType.fxml"));  // Load the UserType.fxml page
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));  // Set the new scene to navigate back
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        }
    }
}
