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
import oopproject.rpmsfinal.EmailNotification;
import oopproject.rpmsfinal.SessionNative;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleUsersController {

    // Singleton instance of EmailNotification to send notifications
    EmailNotification emailer = EmailNotification.getInstance();

    // FXML UI elements
    @FXML private Button backButton;       // Button to go back to the previous screen
    @FXML private Button notifyButton;     // Button to send notifications to users
    @FXML private Text notification;       // Text element to show notifications or status messages
    @FXML private TextField messageArea;   // Text field where admin can enter a message to send

    /**
     * Navigates the user back to the Notifications screen when the back button is clicked.
     */
    @FXML
    private void goBack() {
        try {
            // Load the Notifications screen FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Notifications.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the new scene (Notifications screen)
        } catch (IOException e) {
            // Handle any exceptions while loading the Notifications screen
            e.printStackTrace();
        }
    }

    /**
     * Sends a notification to a group of users based on their user type (patient, doctor, or user).
     * The message is taken from the messageArea text field.
     */
    @FXML
    private void handleNotifyButton() {
        String userType = SessionNative.getNotifyUserType();  // Get the user type to notify: "patient", "doctor", or "user"
        String message = messageArea.getText().trim();        // Get the message entered by the admin

        // Check if the message is empty and display an error if it is
        if (message.isEmpty()) {
            notification.setText("Message cannot be empty.");
            return;
        }

        // Fetch the list of emails based on the user type
        List<String> emails = fetchEmails(userType);

        // If no emails were found, display a message indicating no users to notify
        if (emails.isEmpty()) {
            notification.setText("No users found to notify.");
            return;
        }

        // Send the notification to all fetched emails
        for (String email : emails) {
            emailer.sendNotification(email, "Notification from Admin", message); // Send the email notification
        }

        // Display the result indicating how many users were notified
        notification.setText("Notification sent to " + emails.size() + " " + userType.toLowerCase() + "(s).");
    }

    /**
     * Fetches the list of emails for users based on their user type.
     *
     * @param userType The type of users to fetch emails for ("patient", "doctor", or "user")
     * @return A list of email addresses for the specified user type
     */
    private List<String> fetchEmails(String userType) {
        List<String> emails = new ArrayList<>();
        String query;

        // Build the query based on the user type
        switch (userType) {
            case "patient":
                query = "SELECT email FROM patients";  // Get emails from the patients table
                break;
            case "doctor":
                query = "SELECT email FROM doctors";   // Get emails from the doctors table
                break;
            case "user":
                query = "SELECT email FROM patients UNION SELECT email FROM doctors UNION SELECT email FROM administrators";
                // Get emails from patients, doctors, and administrators
                break;
            default:
                query = "";  // Invalid user type
        }

        // Execute the query to fetch emails from the database
        try (Connection conn = DBConnection.getConnection();  // Establish DB connection
             PreparedStatement stmt = conn.prepareStatement(query);  // Prepare the query
             ResultSet rs = stmt.executeQuery()) {  // Execute the query

            // Add all fetched emails to the list
            while (rs.next()) {
                emails.add(rs.getString("email"));
            }
        } catch (SQLException e) {
            // Handle SQL exceptions (e.g., connection issues, query problems)
            e.printStackTrace();
        }

        // Return the list of emails
        return emails;
    }
}
