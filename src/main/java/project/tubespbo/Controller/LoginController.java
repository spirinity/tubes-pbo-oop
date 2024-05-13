package project.tubespbo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    public void initialize() {
        // Initialize database connection
        DatabaseConnection dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
    }

    @FXML
    private Label messageLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button loginButton;

    private Connection connection;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
    }

    @FXML
    protected void loginOnAction(ActionEvent e) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty()) {
            messageLabel.setText("Please input a username!");
        } else if (password.isEmpty()) {
            messageLabel.setText("Please input a password!");
        } else {
            if (validateLogin(username, password)) {
                messageLabel.setText("Login successful!");
                String role = Session.getRole(); // Retrieve user's role
                if ("admin".equals(role)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/DashboardAdminView.fxml"));
                    Parent root = loader.load();
                    DashboardAdminController dashboardAdminController = loader.getController(); // Change to your actual admin dashboard controller
                    dashboardAdminController.setStage((Stage) loginButton.getScene().getWindow());
                    Scene currentScene = loginButton.getScene();
                    currentScene.setRoot(root);
                } else {
                }
            } else {
                messageLabel.setText("Invalid username or password!");
            }
        }
    }

    private boolean validateLogin(String username, String password) {
        String query = "SELECT level FROM useraccounts WHERE username = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String level = resultSet.getString("level");
                Session.setUsername(username); // Set the session username
                Session.setRole(level); // Set the session role
                return true; // User exists
            }
            return false; // User not found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void createOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/RegisterView.fxml"));
        Parent root = loader.load();
        RegisterController registerController = loader.getController();
        registerController.setStage((Stage) createAccountButton.getScene().getWindow()); // Set the stage instance

        // Get the current scene and set its root to the new scene's root
        Scene currentScene = createAccountButton.getScene();
        currentScene.setRoot(root);
    }

}
