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

public class RegisterController {

    @FXML
    private Button loginButton;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
    }

    @FXML
    private void loginOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/LoginView.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setStage((Stage) loginButton.getScene().getWindow()); // Set the stage instance

        // Get the current scene and set its root to the new scene's root
        Scene currentScene = loginButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void createOnAction(ActionEvent e) throws IOException {
    }
}
