package oopproject.rpmsfinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Doctor class
 * Represents a doctor user in the Remote Health Monitoring System
 */
public class Doctor extends User {
    private String specialization; // Doctor's medical specialization
    private String workingHours;   // Doctor's available working hours
    private final List<String> assignedPatientIds; // List of assigned patients' IDs
    private int patientCounter = 0; // Counter to manage patient assignments

    // Constructor to initialize a doctor object with relevant details
    public Doctor(String username, String password, String userId, String name, int age, String gender,
                  String phone, String email, String specialization, String workingHours) {
        super(username, password, userId, name, age, gender, phone, email); // Calls parent constructor
        this.specialization = specialization;
        this.workingHours = workingHours;
        this.assignedPatientIds = new ArrayList<>(); // Initializes empty patient list
    }

    // Getter for doctor's specialization
    public String getSpecialization() {
        return specialization;
    }

    // Setter for doctor's specialization
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Getter for doctor's working hours
    public String getWorkingHours() {
        return workingHours;
    }

    // Getter for list of assigned patient IDs
    public List<String> getAssignedPatientIds() {
        return assignedPatientIds;
    }

    // Setter for doctor's working hours
    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    // Method to retrieve assigned patients' list
    public List<String> getAssignedPatients() {
        return assignedPatientIds;
    }

    // Adds a patient to the doctor's assigned list if not already assigned
    public void addAssignedPatient(String patientId) {
        if (!assignedPatientIds.contains(patientId)) {
            assignedPatientIds.add(patientCounter, patientId); // Adds patient at the current counter position
            patientCounter++; // Increments the counter for each new patient
        }
    }

    // Overridden toString method to display doctor details along with specialization and working hours
    @Override
    public String toString() {
        return super.toString() +
                "\nSpecialization: " + specialization +
                "\nWorking Hours: " + workingHours;
    }
}
