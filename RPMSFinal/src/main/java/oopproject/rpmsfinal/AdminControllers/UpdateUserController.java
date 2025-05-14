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

public class UpdateUserController {

    // FXML UI elements for buttons and text fields
    @FXML private Button backButton;
    @FXML private Button selectButton;
    @FXML private Button updateButton;

    @FXML private TextField userType;  // Text field for user type (patient, doctor, administrator)
    @FXML private TextField userId;    // Text field for user ID to be updated
    @FXML private TextField attribute; // Text field for the attribute to be updated
    @FXML private TextField newAttribute; // Text field for the new value of the attribute

    @FXML private Text attributeList;  // Displays the list of updatable attributes
    @FXML private Text updateText;     // Displays messages to the user

    /**
     * Navigates back to the Users screen when the back button is clicked.
     */
    @FXML
    private void goBack() {
        try {
            // Load the Users screen layout
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Users.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Users screen)
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions that occur while loading the users screen
        }
    }

    /**
     * Displays the list of attributes that can be updated based on the selected user type.
     */
    @FXML
    private void handleSelectButton() {
        String type = userType.getText().trim().toLowerCase();
        String extraAttributes = "";

        // Determine the additional attributes based on the user type
        if (type.equals("patient")) {
            extraAttributes = ", assigned_doctor"; // Patients can have assigned_doctor
        } else if (type.equals("doctor")) {
            extraAttributes = ", specialization, working_hours"; // Doctors can have specialization and working hours
        } else if (type.equals("administrator") || type.equals("admin")) {
            extraAttributes = ", role, official_email, supervisor_name"; // Administrators can have role, official_email, and supervisor_name
        }

        // Update the list of editable attributes
        attributeList.setText(
                "You can update these fields:\n" +
                        "user_id, name, age, gender, phone, email, username, password" + extraAttributes
        );
    }

    /**
     * Handles the update of the user's attribute when the update button is clicked.
     */
    @FXML
    private void handleUpdateButton() {
        String type = userType.getText().trim().toLowerCase();
        String id = userId.getText().trim();
        String column = attribute.getText().trim();
        String newValue = newAttribute.getText().trim();

        // Check if all fields are filled in
        if (type.isEmpty() || id.isEmpty() || column.isEmpty() || newValue.isEmpty()) {
            updateText.setText("All fields must be filled.");
            return;
        }

        String table;
        // Determine the corresponding table based on the user type
        switch (type) {
            case "patient": table = "patients"; break;
            case "doctor": table = "doctors"; break;
            case "administrator": table = "administrators"; break;
            default:
                updateText.setText("Invalid user type.");
                return;
        }

        // Construct the SQL query to update the user data
        String query = "UPDATE " + table + " SET " + column + " = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // If the attribute is "age", parse the new value as an integer
            if (column.equalsIgnoreCase("age")) {
                try {
                    int age = Integer.parseInt(newValue);
                    stmt.setInt(1, age);
                } catch (NumberFormatException e) {
                    updateText.setText("Invalid age. Please enter a valid number.");
                    return;
                }
            } else {
                stmt.setString(1, newValue); // Set the new value for other attributes
            }

            stmt.setString(2, id); // Set the user ID for the query
            int rowsAffected = stmt.executeUpdate(); // Execute the update query

            // Display appropriate message based on whether the update was successful
            if (rowsAffected > 0) {
                updateText.setText("User updated successfully.");
            } else {
                updateText.setText("User not found or invalid attribute.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            updateText.setText("Update failed. Check attribute name and value.");
        }
    }
}
