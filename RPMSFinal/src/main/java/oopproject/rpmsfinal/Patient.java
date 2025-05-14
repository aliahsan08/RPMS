package oopproject.rpmsfinal;

// Patient class extending the base User class
public class Patient extends User {
    private String assignedDoctor;
    private String emergencyContact;
    private String emergencyPhone;

    // Constructor initializing patient info and assigned doctor
    public Patient(String username, String password, String userId, String name, int age, String gender,
                   String phone, String email, String assignedDoctor) {
        super(username, password, userId, name, age, gender, phone, email);
        this.assignedDoctor = assignedDoctor;
    }

    // Getter for assigned doctor
    public String getAssignedDoctor() {
        return assignedDoctor;
    }

    // Setter for assigned doctor
    public void setAssignedDoctor(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    // Sets emergency contact details
    public void setEmergencyContact(String email, String phone) {
        this.emergencyContact = email;
        this.emergencyPhone = phone;
    }

    // Getter for emergency contact email
    public String getEmergencyContact() {
        return emergencyContact;
    }

    // Getter for emergency contact phone
    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    // Returns string representation including assigned doctor
    @Override
    public String toString() {
        return super.toString() + "\nAssigned Doctor: " + assignedDoctor;
    }
}
