package project.tubespbo.Controller.Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.tubespbo.Models.*;
import project.tubespbo.Util.DatabaseConnection;
import project.tubespbo.Util.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class HistoriTransaksiAdminController {

    private Stage stage;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button managementSampahButton;

    @FXML
    private Button managementUserButton;

    @FXML
    private Button transaksiButton;
    @FXML
    private Button historiTransaksiButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private TableView<Transaksi> historiTransaksiTableView;

    @FXML
    private TableColumn<Transaksi, String> usernameColumn;

    @FXML
    private TableColumn<Transaksi, String> sampahColumn;

    @FXML
    private TableColumn<Transaksi, Integer> beratColumn;

    @FXML
    private TableColumn<Transaksi, Integer> hargaColumn;

    @FXML
    private TableColumn<Transaksi, Date> tanggalColumn;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        stage.setWidth(600);
        stage.setHeight(450);
    }

    @FXML
    private void dashboardOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/DashboardAdminView.fxml"));
        Parent root = loader.load();
        DashboardAdminController dashboardAdminController = loader.getController();
        dashboardAdminController.setStage((Stage) dashboardButton.getScene().getWindow());
        Scene currentScene = dashboardButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void managementSampahOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/ManagementSampahView.fxml"));
        Parent root = loader.load();
        ManagementSampahController managementSampahController = loader.getController();
        managementSampahController.setStage((Stage) managementSampahButton.getScene().getWindow());
        Scene currentScene = managementSampahButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void managementUserOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/ManagementAdminView.fxml"));
        Parent root = loader.load();
        ManagementAdminController managementAdminController = loader.getController();
        managementAdminController.setStage((Stage) managementUserButton.getScene().getWindow());
        Scene currentScene = managementUserButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void transaksiOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/TransaksiAdminView.fxml"));
        Parent root = loader.load();
        TransaksiAdminController transaksiAdminController = loader.getController();
        transaksiAdminController.setStage((Stage) transaksiButton.getScene().getWindow());
        Scene currentScene = transaksiButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void historiTransaksiOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/HistoriTransaksiAdminView.fxml"));
        Parent root = loader.load();
        HistoriTransaksiAdminController historiTransaksiAdminController = loader.getController();
        historiTransaksiAdminController.setStage((Stage) historiTransaksiButton.getScene().getWindow());
        Scene currentScene = historiTransaksiButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void logoutOnAction(ActionEvent e) throws IOException {
        Session.getInstance().clearSession();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/LoginView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(300);
        stage.setHeight(435);
        stage.setResizable(false);
    }

    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUsername()));
        sampahColumn.setCellValueFactory(new PropertyValueFactory<>("sampah"));
        beratColumn.setCellValueFactory(new PropertyValueFactory<>("berat"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));
        tanggalColumn.setCellValueFactory(new PropertyValueFactory<>("tanggal"));

        Entity currentUser = Session.getInstance().getCurrentUser();

        usernameLabel.setText(currentUser.getUsername() + " (Admin)");
        loadHistoriTransaksi();
    }

    private void loadHistoriTransaksi() {
        ObservableList<Transaksi> transaksiList = FXCollections.observableArrayList();
        String query = "SELECT transaksi.id, transaksi.berat, transaksi.harga, transaksi.status, transaksi.tanggal, users.username, users.role, users.nama_lengkap, users.alamat, sampah.nama_sampah as nama, sampah.harga_sampah as harga " +
                "FROM transaksi " +
                "JOIN users ON transaksi.user_id = users.id " +
                "JOIN sampah ON transaksi.sampah_id = sampah.id " + "WHERE status = 'Terkonfirmasi'";

        DatabaseConnection dbConnection = new DatabaseConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int berat = rs.getInt("berat");
                int harga = rs.getInt("harga");
                String status = rs.getString("status");
                Date tanggal = rs.getDate("tanggal");
                String username = rs.getString("username");
                String role = rs.getString("role");
                String namaLengkap = rs.getString("nama_lengkap");
                String alamat = rs.getString("alamat");

                int sampahId = rs.getInt("id");
                String sampahNama = rs.getString("nama");
                int sampahHarga = rs.getInt("harga");
                Sampah sampah = new Sampah(sampahId, sampahNama, sampahHarga);

                Entity user;
                if ("admin".equalsIgnoreCase(role)) {
                    user = new Admin(null, username, null, namaLengkap, alamat);
                } else if ("nasabah".equalsIgnoreCase(role)) {
                    user = new Nasabah(null, username, null, namaLengkap, alamat);
                } else {
                    continue;
                }

                Transaksi transaksi = new Transaksi(id, user, sampah, berat, harga, status, tanggal);
                transaksiList.add(transaksi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        historiTransaksiTableView.setItems(transaksiList);
    }
}