package oopproject.rpmsfinal;

import java.sql.SQLException;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static oopproject.rpmsfinal.Appointment.generateNextId;

public class DoctorPatient {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // Formatter for timestamp

    // Inner class to store doctor feedback
    public static class DoctorFeedback {
        private String feedbackId; // Unique ID for the feedback
        private String doctorId;   // Doctor's unique ID
        private String patientId;  // Patient's unique ID
        private LocalDateTime timestamp; // Timestamp of feedback
        private String recommendations; // Doctor's recommendations for the patient
        private String prescription;    // Prescription details
        private String diagnosis;       // Diagnosis made by the doctor

        // Constructor to initialize feedback details
        public DoctorFeedback(String doctorId, String patientId, String diagnosis,
                              String recommendations, String prescription) throws SQLException {
            this.feedbackId = generateNextId("FB", "doctor_feedback", "feedback_id"); // Generates a unique feedback ID
            this.doctorId = doctorId;
            this.patientId = patientId;
            this.timestamp = LocalDateTime.now(); // Sets the timestamp to the current time
            this.diagnosis = diagnosis;
            this.recommendations = recommendations;
            this.prescription = prescription;
        }

        // Getter methods
        public String getFeedbackId() {
            return feedbackId;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public String getPatientId() {
            return patientId;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getDiagnosis() {
            return diagnosis;
        }

        public String getRecommendations() {
            return recommendations;
        }

        public String getPrescription() {
            return prescription;
        }

        // Setter methods
        public void setFeedbackId(String feedbackId) {
            this.feedbackId = feedbackId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public void setDiagnosis(String diagnosis) {
            this.diagnosis = diagnosis;
        }

        public void setRecommendations(String recommendations) {
            this.recommendations = recommendations;
        }

        public void setPrescription(String prescription) {
            this.prescription = prescription;
        }

        // Overrides toString method to return a formatted string of the doctor's feedback
        @Override
        public String toString() {
            return String.format("""
             
                Date: %s
                Doctor ID: %s
                Diagnosis: %s
                Recommendations: %s
                Prescription: %s
                """,
                    timestamp.format(formatter),
                    doctorId,
                    diagnosis,
                    recommendations,
                    prescription
            );
        }
    }
}
