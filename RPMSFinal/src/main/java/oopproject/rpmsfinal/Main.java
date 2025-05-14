package oopproject.rpmsfinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // Start method for JavaFX application; sets up the stage
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the UserType.fxml file (entry point for the app)
            Parent root = FXMLLoader.load(getClass().getResource("UserType.fxml"));

            // Create and set up the scene with the loaded FXML root
            Scene scene = new Scene(root);
            primaryStage.setTitle("Remote Patient Management System"); // Set window title
            primaryStage.setScene(scene); // Set scene to primary stage
            primaryStage.show(); // Display the stage
        } catch (Exception e) {
            e.printStackTrace(); // Print any error that occurs
        }
    }

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);  // Launch the application
    }
}
