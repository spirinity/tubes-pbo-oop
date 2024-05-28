package project.tubespbo.Controller.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.tubespbo.Models.Entity;
import project.tubespbo.Models.Sampah;
import project.tubespbo.Util.DatabaseConnection;
import project.tubespbo.Util.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class AddSampahController {

    @FXML
    private TextField namaField;

    @FXML
    private TextField hargaField;

    @FXML
    private Label messageLabel;

    @FXML
    public void tambahOnAction() {
        String nama = namaField.getText();
        int harga;

        try {
            harga = Integer.parseInt(hargaField.getText());
        } catch (NumberFormatException e) {
            messageLabel.setText("Mohon untuk menlengkapi isi data!");
            return;
        }

        String query = "INSERT INTO sampah (nama_sampah, harga_sampah) VALUES (?, ?)";
        DatabaseConnection dbConnection = new DatabaseConnection(); // Create an instance
        try (Connection conn = dbConnection.getConnection(); // Call the method on the instance
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nama);
            pstmt.setDouble(2, harga);
            pstmt.executeUpdate();
            messageLabel.setText("Sampah berhasil ditambah!");
            closeWindow();
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Gagal untuk menambahkan sampah!");
        }
    }

    @FXML
    public void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) namaField.getScene().getWindow();
        stage.close();
    }
}
