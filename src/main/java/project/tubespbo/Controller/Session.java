package project.tubespbo.Controller;

public class Session {
    private static String username;
    private static String role; // Add role field

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }

    public static String getRole() { // Add getRole method
        return role;
    }

    public static void setRole(String role) { // Add setRole method
        Session.role = role;
    }

    public static void clear() {
        username = null;
        role = null; // Clear both username and role
    }

    public static boolean isActive() {
        return username != null;
    }
}
