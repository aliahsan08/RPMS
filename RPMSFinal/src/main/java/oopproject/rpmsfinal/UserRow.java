package oopproject.rpmsfinal;

public class UserRow {
    // Fields representing user details
    private final String userId, name, phone, email, role;

    // Constructor to initialize all fields
    public UserRow(String userId, String name, String phone, String email, String role) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    // Getter for user ID
    public String getUserId() { return userId; }

    // Getter for name
    public String getName() { return name; }

    // Getter for phone number
    public String getPhone() { return phone; }

    // Getter for email address
    public String getEmail() { return email; }

    // Getter for role
    public String getRole() { return role; }
}
