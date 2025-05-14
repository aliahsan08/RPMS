package oopproject.rpmsfinal;

public class DPAssignment {
    private final String doctorId; // Doctor's unique ID
    private final String patientId; // Patient's unique ID

    // Constructor to initialize doctor and patient IDs
    public DPAssignment(String doctorId, String patientId) {
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    // Getter method to retrieve the doctor's ID
    public String getDoctorId() {
        return doctorId;
    }

    // Getter method to retrieve the patient's ID
    public String getPatientId() {
        return patientId;
    }
}
