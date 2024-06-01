package project.tubespbo.Controller.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.tubespbo.Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddAdminController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField namaLengkapField;

    @FXML
    private TextField alamatField;

    @FXML
    private Label messageLabel;

    @FXML
    public void tambahOnAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String namaLengkap = namaLengkapField.getText();
        String alamat =  alamatField.getText();

        try {
             password = (passwordField.getText());
        } catch (NumberFormatException e) {
            messageLabel.setText("Mohon untuk menlengkapi isi data!");
            return;
        }

        String query = "INSERT INTO users (username, password, role, nama_lengkap, alamat) VALUES (?, ?, 'admin', ?, ?)";
        DatabaseConnection dbConnection = new DatabaseConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, namaLengkap);
            pstmt.setString(4, alamat);
            pstmt.executeUpdate();
            messageLabel.setText("Admin berhasil ditambah!");
            closeWindow();
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Gagal untuk menambahkan Admin!");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
