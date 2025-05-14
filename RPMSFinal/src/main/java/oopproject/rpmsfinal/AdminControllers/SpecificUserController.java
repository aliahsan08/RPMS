package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oopproject.rpmsfinal.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecificUserController {

    // Instance of EmailNotification and UserManager to handle email and user-related tasks
    EmailNotification emailer = EmailNotification.getInstance();
    private UserManager userManager = UserManager.getInstance();

    // FXML UI elements
    @FXML private TextField userIdField;    // Input field for the user ID
    @FXML private TextField messageArea;     // Input field for the message to be sent
    @FXML private Text notification;         // Text element to show feedback to the user
    @FXML private Button notifyButton;       // Button to trigger notification sending
    @FXML private Button backButton;         // Button to navigate back to the previous screen

    /**
     * Handles the click of the notify button: checks inputs, fetches the user's email,
     * and sends an email notification.
     */
    @FXML
    private void handleNotifyButton() {
        // Get the user ID and message from the input fields
        String userId = userIdField.getText().trim();
        String message = messageArea.getText().trim();

        // Check if the fields are filled
        if (userId.isEmpty() || message.isEmpty()) {
            notification.setText("Please fill all fields.");
            return;
        }

        // Fetch the user's email based on the user ID
        String email = fetchUserEmail(userId);

        // If no email is found, display an error
        if (email == null) {
            notification.setText("User not found.");
            return;
        }

        // Send the notification email to the user's email address
        emailer.sendNotification(email, "Notification from Admin", message);

        // Show a confirmation message in the UI
        notification.setText("Notification sent to user ID: " + userId);
    }

    /**
     * Fetches the email of the user by their user ID using the UserManager.
     * @param userId The ID of the user to fetch
     * @return The email address of the user, or null if the user is not found
     */
    private String fetchUserEmail(String userId) {
        // Retrieve the User object by user ID and return the email
        User user = userManager.getUserById(userId);
        return user != null ? user.getEmail() : null;
    }

    /**
     * Navigates back to the Notifications screen when the back button is clicked.
     */
    @FXML
    private void goBack() {
        try {
            // Load the Notifications screen layout
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Notifications.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Notifications screen)
        } catch (IOException e) {
            e.printStackTrace(); // Handle any exceptions that occur while loading the Notifications screen
        }
    }
}
