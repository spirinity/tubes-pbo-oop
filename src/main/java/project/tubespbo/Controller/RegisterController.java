package project.tubespbo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import project.tubespbo.Models.Nasabah;
import project.tubespbo.Util.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField namaLengkapField;

    @FXML
    private TextField alamatField;

    @FXML
    private Label messageLabel;

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
        loginController.setStage((Stage) loginButton.getScene().getWindow());

        Scene currentScene = loginButton.getScene();
        currentScene.setRoot(root);
    }
    @FXML
    public void createOnAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = namaLengkapField.getText();
        String address = alamatField.getText();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || address.isEmpty()) {
            messageLabel.setText("Mohon untuk melengkapi isi data!");
            return;
        }

        if (createUser(username, password, fullName, address)) {
            messageLabel.setText("Akun sukses terbuat!");

        } else {
            messageLabel.setText("Gagal untuk membuat akun!");
        }
    }

    private boolean createUser(String username, String password, String fullName, String address) {
        String query = "INSERT INTO users (username, password, role, nama_lengkap, alamat) VALUES (?, ?, 'nasabah', ?, ?)";

        DatabaseConnection dbConnection = new DatabaseConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, fullName);
            pstmt.setString(4, address);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

