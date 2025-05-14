package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import oopproject.rpmsfinal.SessionNative;

public class CreateUserController {

    // FXML UI elements for the form fields
    @FXML private Button backButton;         // Button to navigate back to the Users page
    @FXML private TextField username;        // TextField to enter username
    protected String usernameString;         // Variable to store username
    @FXML private TextField password;        // TextField to enter password
    protected String passwordString;         // Variable to store password
    @FXML private TextField userId;          // TextField to enter user ID
    protected String userIdString;           // Variable to store user ID
    @FXML private TextField name;            // TextField to enter name
    protected String nameString;             // Variable to store name
    @FXML private TextField age;             // TextField to enter age
    protected int ageInt;                    // Variable to store age
    @FXML private TextField gender;          // TextField to enter gender
    protected String genderString;           // Variable to store gender
    @FXML private TextField phone;           // TextField to enter phone number
    protected String phoneString;            // Variable to store phone number
    @FXML private TextField email;           // TextField to enter email address
    protected String emailString;            // Variable to store email address

    @FXML private Button patientButton;      // Button to create a patient user
    @FXML private Button doctorButton;       // Button to create a doctor user
    @FXML private Button adminButton;        // Button to create an administrator user

    /**
     * Handles the event when the back button is clicked.
     * Navigates back to the Users page.
     */
    @FXML
    private void goBack() {
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
     * Handles the event when the "Create Patient" button is clicked.
     * Sets the user type as "patient" and navigates to the CreatePatient page.
     */
    @FXML
    private void handlePatientButton() {
        try {
            SessionNative.setCreateUserType("patient");  // Set the user type to "patient"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/CreatePatient.fxml"));
            StackPane root = loader.load();
            CreatePatientController controller = loader.getController();
            controller.setUserData(
                    userId.getText(),       // Pass user ID
                    username.getText(),     // Pass username
                    password.getText(),     // Pass password
                    name.getText(),         // Pass name
                    Integer.parseInt(age.getText()),  // Pass age
                    gender.getText(),       // Pass gender
                    phone.getText(),        // Pass phone number
                    email.getText()         // Pass email address
            );
            Stage stage = (Stage) patientButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the "Create Doctor" button is clicked.
     * Sets the user type as "doctor" and navigates to the CreateDoctor page.
     */
    @FXML
    private void handleDoctorButton() {
        try {
            SessionNative.setCreateUserType("doctor");  // Set the user type to "doctor"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/CreateDoctor.fxml"));
            StackPane root = loader.load();
            CreateDoctorController controller = loader.getController();
            controller.setUserData(
                    userId.getText(),       // Pass user ID
                    username.getText(),     // Pass username
                    password.getText(),     // Pass password
                    name.getText(),         // Pass name
                    Integer.parseInt(age.getText()),  // Pass age
                    gender.getText(),       // Pass gender
                    phone.getText(),        // Pass phone number
                    email.getText()         // Pass email address
            );
            Stage stage = (Stage) doctorButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the "Create Admin" button is clicked.
     * Sets the user type as "administrator" and navigates to the CreateAdmin page.
     */
    @FXML
    private void handleAdminButton() {
        try {
            SessionNative.setCreateUserType("administrator");  // Set the user type to "administrator"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/CreateAdmin.fxml"));
            StackPane root = loader.load();
            CreateAdminController controller = loader.getController();
            controller.setUserData(
                    userId.getText(),       // Pass user ID
                    username.getText(),     // Pass username
                    password.getText(),     // Pass password
                    name.getText(),         // Pass name
                    Integer.parseInt(age.getText()),  // Pass age
                    gender.getText(),       // Pass gender
                    phone.getText(),        // Pass phone number
                    email.getText()         // Pass email address
            );
            Stage stage = (Stage) adminButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set user data from the fields to be passed to the next screen.
     */
    public void setUserData(String userId, String username, String password, String name,
                            int age, String gender, String phone, String email) {
        this.userIdString = userId;
        this.usernameString = username;
        this.passwordString = password;
        this.nameString = name;
        this.ageInt = age;
        this.genderString = gender;
        this.phoneString = phone;
        this.emailString = email;
    }
}
