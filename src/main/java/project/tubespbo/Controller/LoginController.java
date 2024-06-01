package project.tubespbo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.tubespbo.Controller.Admin.DashboardAdminController;
import project.tubespbo.Controller.Nasabah.DashboardNasabahController;
import project.tubespbo.Util.DatabaseConnection;
import project.tubespbo.Util.Session;

import java.io.IOException;
import java.sql.Connection;

import project.tubespbo.Models.Admin;
import project.tubespbo.Models.Nasabah;
import project.tubespbo.Models.Entity;
public class LoginController {

    public void initialize() {
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
    private TextField passwordTextField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button loginButton;

    @FXML
    private CheckBox showCheckBox;

    private Connection connection;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        stage.setWidth(280);
        stage.setHeight(435);
    }

    @FXML
    public void loginOnAction() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Entity entity;
        entity = new Admin(null , username, password, null, null);
        if (entity.authenticate()) {
            Session.getInstance().setCurrentUser(entity);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/DashboardAdminView.fxml"));
            Parent root = loader.load();
            DashboardAdminController dashboardAdminController = loader.getController();
            dashboardAdminController.setStage((Stage) loginButton.getScene().getWindow());
            Scene currentScene = loginButton.getScene();
            currentScene.setRoot(root);
            return;
        }

        entity = new Nasabah(null, username, password, null, null);
        if (entity.authenticate()) {
            Session.getInstance().setCurrentUser(entity);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Nasabah/DashboardUserView.fxml"));
            Parent root = loader.load();
            DashboardNasabahController dashboardNasabahController = loader.getController(); // Change to your actual admin dashboard controller
            dashboardNasabahController.setStage((Stage) loginButton.getScene().getWindow());
            Scene currentScene = loginButton.getScene();
            currentScene.setRoot(root);
            return;
        }

        messageLabel.setText("Username atau password salah!");
    }



    @FXML
    private void createOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/RegisterView.fxml"));
        Parent root = loader.load();
        RegisterController registerController = loader.getController();
        registerController.setStage((Stage) createAccountButton.getScene().getWindow());
        Scene currentScene = createAccountButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void showOnAction(ActionEvent e) {
        if (showCheckBox.isSelected()) {
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
            passwordField.setVisible(false);
        } else {
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordTextField.setVisible(false);
        }
    }
}
