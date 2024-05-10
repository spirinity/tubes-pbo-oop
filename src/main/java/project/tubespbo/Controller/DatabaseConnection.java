package project.tubespbo.Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "java";
        String databaseUser = "root"; // Update with your database
        String databasePassword = ""; // Update with your database password
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("Database connected successfully"); // Print success message
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database"); // Print error message
        }

        return databaseLink;
    }
}
