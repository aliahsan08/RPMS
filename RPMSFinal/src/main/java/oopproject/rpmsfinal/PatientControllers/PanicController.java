package oopproject.rpmsfinal.PatientControllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.*;

import java.io.IOException;
import java.sql.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class PanicController {
    Patient patient = (Patient) SessionNative.getCurrentUser();
    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private Button csvUploadButton;
    @FXML
    private Text vitalsText;
    @FXML
    private TextField temperature;
    @FXML
    private TextField heartRate;
    @FXML
    private TextField systolicBP;
    @FXML
    private TextField diastolicBP;
    @FXML
    private TextField respirationRate;
    @FXML
    private TextField oxygenSaturation;
    EmailNotification emailer = EmailNotification.getInstance();
    private UserManager userManager = UserManager.getInstance();

    @FXML
    private void goBackHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/PatientFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddButton() {
        try {
            VitalRecord record = new VitalRecord(
                    patient.getUserId(),
                    Double.parseDouble(temperature.getText()),
                    Integer.parseInt(heartRate.getText()),
                    Integer.parseInt(systolicBP.getText()),
                    Integer.parseInt(diastolicBP.getText()),
                    Integer.parseInt(respirationRate.getText()),
                    Integer.parseInt(oxygenSaturation.getText()),
                    ""
            );

            if (record.uploadVitals()) {
                if (record.isCritical()) {
                    vitalsText.setText("PANIC BUTTON ACTIVATED");
                    String email = fetchUserEmail(patient.getAssignedDoctor());
                    String message = "The health of your patient " + patient.getUserId() + " " + patient.getName() + " is unstable. Check on them as soon as possible!";
                    emailer.sendNotification(email, "Patient Health Critical!", message);
                } else {
                    vitalsText.setText("Vitals Are Stable");
                }
            } else {
                vitalsText.setText("Error saving vitals.");
            }

        } catch (NumberFormatException e) {
            vitalsText.setText("Please enter valid numbers.");
        } catch (Exception e) {
            vitalsText.setText("Error saving vitals.");
            e.printStackTrace();
        }
    }
    private String fetchUserEmail(String userId) {
        Doctor doctor = userManager.getDoctorById(userId);
        return doctor.getEmail();
    }

    @FXML
    private void handleCsvUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Vitals CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showOpenDialog(csvUploadButton.getScene().getWindow());
        if (file == null) {
            vitalsText.setText("No file selected.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int successCount = 0;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] values = line.split(",");
                if (values.length < 6) continue;

                try {
                    VitalRecord record = new VitalRecord(
                            patient.getUserId(),
                            Double.parseDouble(values[0].trim()),
                            Integer.parseInt(values[1].trim()),
                            Integer.parseInt(values[2].trim()),
                            Integer.parseInt(values[3].trim()),
                            Integer.parseInt(values[4].trim()),
                            Double.parseDouble(values[5].trim()),
                            ""
                    );

                    if (record.uploadVitals()) {
                        successCount++;
                        if (record.isCritical()) {
                            vitalsText.setText("PANIC BUTTON ACTIVATED");
                            String email = fetchUserEmail(patient.getAssignedDoctor());
                            String message = "The health of your patient " + patient.getUserId() + " " + patient.getName() + " is unstable. Check on them as soon as possible!";
                            emailer.sendNotification(email, "Patient Health Critical!", message);
                        }
                        else{
                            vitalsText.setText(successCount + " records uploaded.");
                        }
                    }

                } catch (Exception e) {
                    vitalsText.setText("Error saving vitals.");
                    e.printStackTrace();
                }
            }



        } catch (IOException e) {
            vitalsText.setText("Failed to read the CSV file.");
            e.printStackTrace();
        }
    }
}
