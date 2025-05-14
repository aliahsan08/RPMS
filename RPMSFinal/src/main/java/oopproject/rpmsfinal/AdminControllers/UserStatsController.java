package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import oopproject.rpmsfinal.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserStatsController {

    // FXML UI elements for back button and the text area to display the user statistics
    @FXML
    private Button backButton;

    @FXML
    private Text reportText;

    /**
     * Initializes the statistics report by fetching counts of patients, doctors, and administrators from the database.
     * The report is then displayed in the reportText field.
     */
    @FXML
    public void initialize() {
        String report = ""; // Variable to hold the report text

        // Try-with-resources to automatically close the database connection and statement
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            int patients = 0, doctors = 0, admins = 0;

            // Query to get the count of patients
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM patients");
            if (rs.next()) patients = rs.getInt("count");
            rs.close();

            // Query to get the count of doctors
            rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM doctors");
            if (rs.next()) doctors = rs.getInt("count");
            rs.close();

            // Query to get the count of administrators
            rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM administrators");
            if (rs.next()) admins = rs.getInt("count");
            rs.close();

            // Calculate the total number of users
            int totalUsers = patients + doctors + admins;

            // Construct the report text
            report = "Total Users: " + totalUsers + "\n"
                    + "Patients: " + patients + "\n"
                    + "Doctors: " + doctors + "\n"
                    + "Administrators: " + admins;

            // Set the generated report text to the reportText field
            reportText.setText(report);

        } catch (Exception e) {
            // Handle any exceptions (e.g., DB connection issues) and display an error message
            reportText.setText("Error generating user stats.");
            e.printStackTrace();
        }
    }

    /**
     * Navigates back to the Reports screen when the back button is clicked.
     */
    @FXML
    private void goBack() {
        try {
            // Load the Reports screen
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Reports.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Reports screen)
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions that occur while navigating
        }
    }
}
