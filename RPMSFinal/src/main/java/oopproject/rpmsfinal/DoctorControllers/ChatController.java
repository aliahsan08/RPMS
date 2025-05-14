package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.ChatLauncher;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ChatController {

    // Instance of ChatLauncher to manage launching the chat
    private final ChatLauncher chatLauncher = new ChatLauncher();

    // FXML UI elements (buttons and text)
    @FXML
    private Button joinButton;
    @FXML
    private Button backButton;
    @FXML
    private Text chatText;

    /**
     * Handles navigating back to the Home screen when the back button is clicked.
     */
    @FXML
    private void goBackHome() {
        try {
            // Load the Home screen
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Home screen)
        } catch (Exception e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
    }

    /**
     * Handles joining the chat when the join button is clicked.
     * It uses ChatLauncher to launch the chat session.
     */
    @FXML
    private void handleJoinButton() {
        // Attempt to launch the chat session
        boolean success = chatLauncher.launchChat(); // Opens the default chat link

        if (!success) {
            // If unable to launch chat, update the text
            chatText.setText("Unable to launch chat");
        }
    }
}
