package oopproject.rpmsfinal.DoctorControllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oopproject.rpmsfinal.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VitalsController {

    @FXML private Button backButton; // Button for going back to the previous screen
    @FXML private Button downloadPdfButton; // Button for downloading the vitals report as PDF
    @FXML private Button fetchVitalsButton; // Button to fetch vital records
    @FXML private Button viewTrends; // Button to view trends for the patient
    @FXML private TextField patientIdField; // Text field for entering the patient's ID
    @FXML private Text statusText; // Text field for displaying status messages

    @FXML private TableView<VitalRecord> vitalsTable; // Table to display vital records
    @FXML private TableColumn<VitalRecord, String> tsCol; // Column for timestamp
    @FXML private TableColumn<VitalRecord, Double> tempCol; // Column for temperature readings
    @FXML private TableColumn<VitalRecord, Integer> hrCol; // Column for heart rate readings
    @FXML private TableColumn<VitalRecord, String> bpCol; // Column for blood pressure (systolic/diastolic)
    @FXML private TableColumn<VitalRecord, Integer> respCol; // Column for respiration rate readings
    @FXML private TableColumn<VitalRecord, Double> oxCol; // Column for oxygen saturation readings
    @FXML private TableColumn<VitalRecord, String> statusCol; // Column for status (Normal/CRITICAL)

    private final Doctor doctor = (Doctor) SessionNative.getCurrentUser(); // Get the current doctor user
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // Date format for timestamp

    @FXML
    public void initialize() {
        // Wire up cell value factories to bind table columns to data
        tsCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getTimestamp().format(DF))); // Format timestamp
        tempCol.setCellValueFactory(r -> new ReadOnlyObjectWrapper<>(r.getValue().getTemperature())); // Display temperature
        hrCol.setCellValueFactory(r -> new ReadOnlyObjectWrapper<>(r.getValue().getHeartRate())); // Display heart rate
        bpCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getSystolicBP() + "/" + r.getValue().getDiastolicBP())); // Display blood pressure
        respCol.setCellValueFactory(r -> new ReadOnlyObjectWrapper<>(r.getValue().getRespirationRate())); // Display respiration rate
        oxCol.setCellValueFactory(r -> new ReadOnlyObjectWrapper<>(r.getValue().getOxygenSaturation())); // Display oxygen saturation
        statusCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().isCritical() ? "CRITICAL" : "Normal")); // Display vital status
    }

    @FXML
    private void handleFetchVitals() {
        String patientId = patientIdField.getText().trim(); // Get the patient ID from the input field
        statusText.setText(""); // Clear status text

        if (patientId.isEmpty()) { // Check if the input is empty
            statusText.setText("Please enter a Patient ID."); // Prompt user to enter patient ID
            return;
        }

        List<VitalRecord> records = VitalRecord.getPatientVitals(patientId); // Fetch vital records for the patient

        if (records.isEmpty()) { // Check if no records are found
            statusText.setText("No vital records found."); // Show message if no records exist
            vitalsTable.getItems().clear(); // Clear the table
        } else {
            vitalsTable.setItems(FXCollections.observableArrayList(records)); // Display records in the table
        }
    }

    @FXML
    private void handleViewTrends() {
        String pid = patientIdField.getText().trim(); // Get the patient ID
        if (!pid.isEmpty()) { // Check if patient ID is not empty
            oopproject.rpmsfinal.HealthTrends.getInstance().showPatientTrends(pid); // Show health trends for the patient
        }
    }

    @FXML
    private void handleDownloadPdf() {
        // 1) grab what's currently in the table
        List<VitalRecord> records = new ArrayList<>(vitalsTable.getItems()); // Get all vitals from the table
        if (records.isEmpty()) { // Check if there are no vitals to export
            statusText.setText("No vitals to export."); // Notify user
            return;
        }

        // 2) ask user where to save the file
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Vitals Report"); // Set dialog title
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf")); // Filter for PDF files
        File file = chooser.showSaveDialog(downloadPdfButton.getScene().getWindow()); // Show save dialog
        if (file == null) return; // If user cancels, exit

        // 3) export via ReportGenerator to save PDF
        try {
            ReportGenerator.getInstance()
                    .exportVitalsReportPdf(patientIdField.getText(), records, file); // Generate and save PDF report
            statusText.setText("PDF saved: " + file.getName()); // Notify user of success
        } catch (Exception e) {
            e.printStackTrace();
            statusText.setText("Failed to save PDF."); // Notify user of failure
        }
    }

    @FXML
    private void goBackHome() {
        try {
            StackPane root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Home.fxml")); // Load home screen
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get current stage
            stage.setScene(new Scene(root)); // Set new scene for home screen
        } catch (IOException e) {
            e.printStackTrace(); // Handle any loading errors
        }
    }
}
