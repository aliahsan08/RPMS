package oopproject.rpmsfinal.AdminControllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.Appointment;
import oopproject.rpmsfinal.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AptSummaryController {
    // FXML UI elements for displaying the appointment details in a table
    @FXML private TableView<Appointment> appointmentsTable;  // Table view to show appointments
    @FXML private TableColumn<Appointment, String> apptIdCol; // Column to display appointment ID
    @FXML private TableColumn<Appointment, String> patientIdCol; // Column to display patient ID
    @FXML private TableColumn<Appointment, String> doctorIdCol; // Column to display doctor ID
    @FXML private TableColumn<Appointment, String> purposeCol; // Column to display appointment purpose
    @FXML private TableColumn<Appointment, String> statusCol; // Column to display appointment status
    @FXML private TableColumn<Appointment, String> notesCol; // Column to display appointment notes
    @FXML private TableColumn<Appointment, String> dateTimeCol; // Column to display appointment date and time

    // FXML UI elements for navigation and displaying messages
    @FXML private Button backButton; // Button to go back to the previous screen
    @FXML private Text reportText;  // Text area to display any report or message

    /**
     * Initializes the table with appointment data from the database.
     * Sets the cell value factories to bind the data to the table columns.
     */
    @FXML
    public void initialize() {
        // Set cell value factories to extract and display data for each column
        apptIdCol.setCellValueFactory(a -> new ReadOnlyStringWrapper(a.getValue().getAppointmentId()));
        patientIdCol.setCellValueFactory(a -> new ReadOnlyStringWrapper(a.getValue().getPatientId()));
        doctorIdCol.setCellValueFactory(a -> new ReadOnlyStringWrapper(a.getValue().getDoctorId()));
        purposeCol.setCellValueFactory(a -> new ReadOnlyStringWrapper(a.getValue().getPurpose()));
        statusCol.setCellValueFactory(a -> new ReadOnlyStringWrapper(a.getValue().getStatus().toString()));
        notesCol.setCellValueFactory(a -> new ReadOnlyStringWrapper(a.getValue().getNotes()));
        dateTimeCol.setCellValueFactory(a -> new ReadOnlyStringWrapper(a.getValue().getDateTime().toString()));

        // List to store appointments retrieved from the database
        List<Appointment> appointments = new ArrayList<>();

        // SQL query to fetch all appointments from the database
        String query = "SELECT * FROM appointments";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Iterate over the result set and create Appointment objects
            while (rs.next()) {
                // Retrieve appointment details from the database
                String apptId = rs.getString("appointment_id");
                String patientId = rs.getString("patient_id");
                String doctorId = rs.getString("doctor_id");
                String purpose = rs.getString("purpose");
                LocalDateTime dateTime = rs.getTimestamp("appointment_datetime").toLocalDateTime();

                // Create an Appointment object and set its values
                Appointment appt = new Appointment(apptId, patientId, doctorId, dateTime, purpose);

                // Manually override the status and notes fields as the constructor sets defaults
                appt.setStatus(Appointment.AppointmentStatus.valueOf(rs.getString("status")));

                // Add the appointment to the list
                appointments.add(appt);
            }

            // Update the table with the list of appointments
            appointmentsTable.getItems().setAll(appointments);

        } catch (Exception e) {
            // Handle any exceptions and print the stack trace
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the back button is clicked.
     * Navigates back to the Reports page.
     */
    @FXML
    private void goBack() {
        try {
            // Load the Reports page and set it as the current scene
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Reports.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            // Handle any exceptions and print the stack trace
            e.printStackTrace();
        }
    }
}
