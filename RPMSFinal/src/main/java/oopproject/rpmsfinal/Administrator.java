package oopproject.rpmsfinal;

/**
 * Administrator class
 * Represents an administrator user in the Remote Health Monitoring System
 */
public class Administrator extends User {
    private String role; // The role of the administrator (e.g., system admin, manager)
    private String officialEmail; // The official email address of the administrator
    private String supervisorName; // The name of the administrator's supervisor

    // Constructor for initializing an Administrator object
    public Administrator(String username, String password, String userId, String name, int age, String gender,
                         String phone, String email, String role, String officialEmail, String supervisorName) {
        super(username, password, userId, name, age, gender, phone, email); // Calls parent constructor
        this.role = role;
        this.officialEmail = officialEmail;
        this.supervisorName = supervisorName;
    }

    // Getter and setter for the administrator's role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Getter and setter for the administrator's official email
    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    // Getter and setter for the administrator's supervisor's name
    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    // Overridden toString method to display administrator's details
    @Override
    public String toString() {
        return super.toString() + // Calls parent toString method to include user details
                "\nRole: " + role +
                "\nOfficial Email: " + officialEmail +
                "\nSupervisor: " + supervisorName;
    }
}
