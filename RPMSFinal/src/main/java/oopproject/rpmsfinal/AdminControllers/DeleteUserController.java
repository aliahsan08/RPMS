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
import java.sql.ResultSet;

public class DeleteUserController {

    // FXML UI elements
    @FXML private Button backButton;         // Button to go back to the previous screen
    @FXML private Button deleteButton;       // Button to delete the user
    @FXML private TextField userIdField;     // TextField to input the user ID
    @FXML private Text userText;             // Text element to display user feedback

    /**
     * Handles the event when the back button is clicked.
     * Navigates back to the Users page.
     */
    @FXML
    private void goBackHome() {
        try {
            // Load the Users page FXML and set it as the current scene
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Users.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            // Handle any exceptions while loading the Users page
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the delete button is clicked.
     * Deletes the user with the specified user ID from the database.
     */
    @FXML
    private void handleDeleteButton() {
        String userId = userIdField.getText().trim();  // Get the user ID from the input field

        if (userId.isEmpty()) {
            userText.setText("Please enter a user ID.");
            return; // Exit early if the user ID is empty
        }

        String[] tables = {"patients", "doctors", "administrators"};  // Tables to check for the user
        boolean userFound = false;

        try (Connection conn = DBConnection.getConnection()) {
            // Loop through all relevant tables to check if the user exists
            for (String table : tables) {
                String checkQuery = "SELECT user_id FROM " + table + " WHERE user_id = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                    checkStmt.setString(1, userId);  // Set the user ID in the query
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next()) {  // If the user is found in the current table
                        String deleteQuery = "DELETE FROM " + table + " WHERE user_id = ?";
                        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                            deleteStmt.setString(1, userId);  // Set the user ID to delete
                            deleteStmt.executeUpdate();  // Execute the deletion
                            userText.setText("User with ID " + userId + " deleted from " + table + ".");
                            userFound = true;  // Mark that the user was found and deleted
                            break;  // Exit the loop after deleting the user
                        }
                    }
                }
            }

            if (!userFound) {
                userText.setText("User ID not found.");  // Display message if no user was found
            }

        } catch (Exception e) {
            e.printStackTrace();
            userText.setText("Error deleting user.");  // Display error message if deletion fails
        }
    }
}
