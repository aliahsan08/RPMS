package oopproject.rpmsfinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Appointment {
    private final String appointmentId; // Unique identifier for the appointment
    private final String patientId; // ID of the patient for this appointment
    private final String doctorId; // ID of the doctor assigned to the appointment
    private LocalDateTime dateTime; // Date and time of the appointment
    private String purpose; // Purpose or reason for the appointment
    private AppointmentStatus status; // Status of the appointment (requested, confirmed, etc.)
    private String notes; // Notes related to the appointment
    private String prescriptionId; // Prescription ID, if any, linked to this appointment

    // Constructor for creating a new appointment with auto-generated ID
    public Appointment(String patientId, String doctorId, LocalDateTime dateTime, String purpose) throws SQLException {
        this.appointmentId = generateNextId("AP", "appointments", "appointment_id"); // Generates unique appointment ID
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.purpose = purpose;
        this.status = AppointmentStatus.REQUESTED; // Default status is 'requested'
        this.notes = ""; // Initial notes are empty
    }

    // Constructor for creating an appointment with an existing ID (used for loading from DB)
    public Appointment(String appointmentId, String patientId, String doctorId, LocalDateTime dateTime, String purpose) throws SQLException {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.purpose = purpose;
        this.status = AppointmentStatus.REQUESTED; // Default status is 'requested'
        this.notes = ""; // Initial notes are empty
    }

    // Getter methods
    public String getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getPurpose() { return purpose; }
    public AppointmentStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public String getPrescriptionId() { return prescriptionId; }

    // Setter methods
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }

    // Adds additional notes to the appointment (if provided)
    public void addNotes(String notes) {
        if (notes != null && !notes.trim().isEmpty()) {
            this.notes = this.notes.isEmpty() ? notes : this.notes + "\n" + notes; // Appends new notes if there are existing ones
        }
    }

    // Enum to represent different appointment statuses
    public enum AppointmentStatus {
        REQUESTED, CONFIRMED, CANCELLED, COMPLETED
    }

    // Links this appointment to a prescription (if available)
    public void linkWithPrescription(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    // Static method to generate the next appointment ID based on the prefix, table, and column
    public static String generateNextId(String prefix, String tableName, String idColumn) throws SQLException {
        String latestId = null;
        String sql = "SELECT " + idColumn + " FROM " + tableName + " WHERE " + idColumn + " LIKE ? ORDER BY " + idColumn + " DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prefix + "%"); // Set search pattern for IDs starting with the prefix
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                latestId = rs.getString(1); // Fetch the latest ID
            }
        }

        int nextNumber = 1; // Default to 1 if no previous IDs exist
        if (latestId != null && latestId.length() > prefix.length()) {
            String numberPart = latestId.substring(prefix.length()); // Extract numeric part of the ID
            try {
                nextNumber = Integer.parseInt(numberPart) + 1; // Increment the number
            } catch (NumberFormatException e) {
                // If parsing fails, fallback to 1
            }
        }

        // Return the new ID formatted with the prefix and padded to 3 digits
        return String.format("%s%03d", prefix, nextNumber);
    }
}
