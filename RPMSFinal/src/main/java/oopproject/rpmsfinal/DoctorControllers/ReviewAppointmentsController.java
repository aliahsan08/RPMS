package oopproject.rpmsfinal.DoctorControllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oopproject.rpmsfinal.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewAppointmentsController {

    @FXML private Button backButton; // Button to navigate back to the previous screen
    @FXML private Button acceptButton; // Button to accept an appointment
    @FXML private Button rejectButton; // Button to reject an appointment
    @FXML private TextField appointmentId; // Text field to input appointment ID for updates
    @FXML private Text infoText; // Text element to display info or error messages
    @FXML private Text appointmentsText; // Text element to display success or failure of appointment actions
    @FXML private TableView<Appointment> appointmentsTable; // Table to display the list of appointments
    @FXML private TableColumn<Appointment, String> apptIdCol; // Column for appointment ID
    @FXML private TableColumn<Appointment, String> patientIdCol; // Column for patient ID
    @FXML private TableColumn<Appointment, String> dateTimeCol; // Column for appointment date and time
    @FXML private TableColumn<Appointment, String> statusCol; // Column for appointment status

    private final Doctor doctor = (Doctor) SessionNative.getCurrentUser(); // Retrieve current doctor from session

    @FXML
    public void initialize() {
        // Set up the columns to show the relevant data for each appointment record
        apptIdCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getAppointmentId()));
        patientIdCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getPatientId()));
        dateTimeCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getDateTime().toString()));
        statusCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getStatus().toString()));

        // Fetch appointments for the current doctor from the database
        List<Appointment> appointments = new ArrayList<>();
        String doctorId = doctor.getUserId();

        String query = "SELECT * FROM appointments WHERE doctor_id = ?";

        try (Connection conn = DBConnection.getConnection(); // Open a connection to the database
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, doctorId); // Set the doctor ID parameter in the query
            ResultSet rs = stmt.executeQuery(); // Execute the query and get the result set

            // Populate the appointments list with results
            while (rs.next()) {
                String apptId = rs.getString("appointment_id");
                String patId = rs.getString("patient_id");
                String docId = rs.getString("doctor_id");
                LocalDateTime dateTime = rs.getTimestamp("appointment_datetime").toLocalDateTime();
                String purpose = rs.getString("purpose");
                String statusStr = rs.getString("status");
                Appointment.AppointmentStatus status = Appointment.AppointmentStatus.valueOf(statusStr);
                Appointment appointment = new Appointment(apptId, patId, docId, dateTime, purpose);
                appointment.setStatus(status); // Set the status of the appointment
                appointments.add(appointment); // Add the appointment to the list
            }

            appointmentsTable.getItems().setAll(appointments); // Display the appointments in the table

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
    }

    @FXML
    private void handleAcceptButton() {
        updateAppointmentStatus("CONFIRMED"); // Accept the appointment by updating its status to 'CONFIRMED'
    }

    @FXML
    private void handleRejectButton() {
        updateAppointmentStatus("CANCELLED"); // Reject the appointment by updating its status to 'CANCELLED'
    }

    private void updateAppointmentStatus(String newStatus) {
        String apptId = appointmentId.getText().trim(); // Get the appointment ID from the input field

        if (apptId.isEmpty()) {
            infoText.setText("Please enter an Appointment ID."); // Display an error message if no ID is entered
            return;
        }

        String updateQuery = "UPDATE appointments SET status = ? WHERE appointment_id = ? AND doctor_id = ?";

        try (Connection conn = DBConnection.getConnection(); // Open a connection to the database
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, newStatus); // Set the new status
            stmt.setString(2, apptId); // Set the appointment ID
            stmt.setString(3, doctor.getUserId()); // Set the current doctor ID

            int rowsAffected = stmt.executeUpdate(); // Execute the update query

            if (rowsAffected > 0) {
                appointmentsText.setText("Appointment " + apptId + " marked as " + newStatus + "."); // Display success message
            } else {
                appointmentsText.setText("Invalid Appointment ID or you are not assigned to it."); // Display error message
            }

        } catch (Exception e) {
            infoText.setText("Failed to update appointment."); // Display failure message
            e.printStackTrace(); // Handle any exceptions
        }
    }

    @FXML
    private void goBack() {
        try {
            StackPane root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Appointments.fxml")); // Load the appointments page
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current window
            stage.setScene(new Scene(root)); // Set the scene to the appointments page
        } catch (IOException e) {
            e.printStackTrace(); // Handle IO exceptions
        }
    }
}
