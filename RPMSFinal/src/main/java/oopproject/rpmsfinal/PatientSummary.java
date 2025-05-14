package oopproject.rpmsfinal;

/**
 * Simple data class representing a patient's ID and name.
 */
public class PatientSummary {
    // Unique identifier for the patient
    private final String userId;

    // Patient's full name
    private final String name;

    // Constructor to initialize both fields
    public PatientSummary(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    // Getter for userId
    public String getUserId() {
        return userId;
    }

    // Getter for name
    public String getName() {
        return name;
    }
}
