package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.MeetingLauncher;

public class VideoCallController {

    private final MeetingLauncher meetingLauncher = new MeetingLauncher(); // Initialize MeetingLauncher to manage video calls
    @FXML private Button backButton; // Button to navigate back to the home screen
    @FXML private Button joinButton; // Button to join the video call
    @FXML private Text videoCallText; // Text element for displaying video call status

    @FXML
    private void goBackHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Home.fxml")); // Load home screen
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current stage (window)
            stage.setScene(new Scene(root)); // Set the scene to the home screen
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions to the console for debugging
        }
    }

    @FXML
    private void handleJoinButton() {
        boolean success = meetingLauncher.launchMeeting();  // Attempt to launch the meeting (opens default meeting link)
        if (!success) { // If launching the meeting fails
            videoCallText.setText("Unable to launch meeting"); // Display error message
        }
    }
}
