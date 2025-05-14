package oopproject.rpmsfinal;

/**
 * Manages session-level information for the currently logged-in user.
 */
public class SessionNative {
    // Fields to hold session-related user data
    private static String userType;
    private static String username;
    private static String userId;
    private static User user;
    private static String createUserType;
    private static String notifyUserType;

    // Set and get the main user type (Patient, Doctor, Admin)
    public static void setUserType(String type) {
        userType = type;
    }

    public static String getUserType() {
        return userType;
    }

    // Set and get the user type being created (used in admin panel)
    public static void setCreateUserType(String type) {
        createUserType = type;
    }

    public static String getCreateUserType() {
        return createUserType;
    }

    // Set and get the user type being notified (for messages/alerts)
    public static void setNotifyUserType(String type) {
        notifyUserType = type;
    }

    public static String getNotifyUserType() {
        return notifyUserType;
    }

    // Set and get the current session username
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String name) {
        username = name;
    }

    // Set and get the current session user ID
    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String id) {
        userId = id;
    }

    // Set and get the full User object for the current session
    public static User getCurrentUser() {
        return user;
    }

    public static void setCurrentUser(User newUser) {
        user = newUser;
    }
}
