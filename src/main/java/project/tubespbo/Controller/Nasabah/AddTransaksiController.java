package project.tubespbo.Controller.Nasabah;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.tubespbo.Models.Sampah;
import project.tubespbo.Util.DatabaseConnection;
import project.tubespbo.Util.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AddTransaksiController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        stage.setWidth(600);
        stage.setHeight(450);
    }

    @FXML
    private ComboBox<Sampah> sampahComboBox;

    @FXML
    private TextField beratSampah;

    @FXML
    private Label messageLabel;

    private ObservableList<Sampah> sampahList;
    @FXML
    private void initialize() {
        loadSampah();
    }

    private void loadSampah() {
        sampahList = FXCollections.observableArrayList();
        String query = "SELECT id, nama_sampah as nama, harga_sampah as harga FROM sampah";
        DatabaseConnection dbConnection = new DatabaseConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Sampah sampah = new Sampah(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getInt("harga")
                );
                sampahList.add(sampah);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sampahComboBox.setItems(sampahList);
    }

    @FXML
    private void tambahOnAction() {
        Sampah selectedSampah = sampahComboBox.getSelectionModel().getSelectedItem();
        if (selectedSampah == null || beratSampah.getText().isEmpty()) {
            messageLabel.setText("Please select sampah and enter weight.");
            return;
        }

        int berat = Integer.parseInt(beratSampah.getText());
        double totalHarga = selectedSampah.getHarga() * berat;

        DatabaseConnection dbConnection = new DatabaseConnection();
        try (Connection  conn = dbConnection.getConnection()) {
            String query = "INSERT INTO transaksi (user_id, sampah_id, berat, harga, status, tanggal) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Session.getInstance().getCurrentUser().getId());
            pstmt.setInt(2, selectedSampah.getId());
            pstmt.setInt(3, berat);
            pstmt.setDouble(4, totalHarga);
            pstmt.setString(5, "Pending");
            pstmt.setDate(6, new java.sql.Date(new Date().getTime()));
            pstmt.executeUpdate();

            messageLabel.setText("Transaksi berhasil ditambahkan.");
            closeWindow();
        } catch (SQLException ex) {
            ex.printStackTrace();
            messageLabel.setText("Failed to add transaction.");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) beratSampah.getScene().getWindow();
        stage.close();
    }
}
