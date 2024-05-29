package project.tubespbo.Controller.Nasabah;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import project.tubespbo.Controller.Admin.*;
import project.tubespbo.Models.Entity;
import project.tubespbo.Models.Sampah;
import project.tubespbo.Models.Transaksi;
import project.tubespbo.Util.DatabaseConnection;
import project.tubespbo.Util.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
public class TransaksiUserController {

    private Stage stage;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button transaksiButton;

    @FXML
    private Button historiTransaksiButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private TableView<Transaksi> transaksiTableView;

    @FXML
    private TableColumn<Transaksi, String> usernameColumn;

    @FXML
    private TableColumn<Transaksi, String> sampahColumn;

    @FXML
    private TableColumn<Transaksi, Integer> beratColumn;

    @FXML
    private TableColumn<Transaksi, Integer> hargaColumn;

    @FXML
    private TableColumn<Transaksi, String> statusColumn;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Nasabah/DashboardUserView.fxml"));
        Parent root = loader.load();
        DashboardAdminController dashboardAdminController = loader.getController();
        dashboardAdminController.setStage((Stage) dashboardButton.getScene().getWindow()); // Set the stage instance
        // Get the current scene and set its root to the new scene's root
        Scene currentScene = dashboardButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void transaksiOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Nasabah/TransaksiUserView.fxml"));
        Parent root = loader.load();
        TransaksiUserController transaksiUserController = loader.getController();
        transaksiUserController.setStage((Stage) transaksiButton.getScene().getWindow()); // Set the stage instance
        // Get the current scene and set its root to the new scene's root
        Scene currentScene = transaksiButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void historiTransaksiOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Nasabah/HistoriTransaksiUserView.fxml"));
        Parent root = loader.load();
        HistoriTransaksiUserController historiTransaksiUserController = loader.getController();
        historiTransaksiUserController.setStage((Stage) historiTransaksiButton.getScene().getWindow()); // Set the stage instance
        // Get the current scene and set its root to the new scene's root
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
    }

    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUsername()));
        sampahColumn.setCellValueFactory(new PropertyValueFactory<>("sampah"));
        beratColumn.setCellValueFactory(new PropertyValueFactory<>("berat"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        tanggalColumn.setCellValueFactory(new PropertyValueFactory<>("tanggal"));

        Entity currentUser = Session.getInstance().getCurrentUser();

        // Display the username in the label
        usernameLabel.setText(currentUser.getUsername() + " (Nasabah)");
        loadTransaksi();
    }

    private void loadTransaksi() {
        transaksiTableView.getItems().clear();
        String query = "SELECT transaksi.id, transaksi.berat, transaksi.harga, transaksi.status, transaksi.tanggal, users.username, sampah.nama_sampah as nama, sampah.harga_sampah as harga " +
                "FROM transaksi " +
                "JOIN users ON transaksi.user_id = users.id " +
                "JOIN sampah ON transaksi.sampah_id = sampah.id " +
                "WHERE user_id = ? AND status = 'Pending'";
        DatabaseConnection dbConnection = new DatabaseConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, Session.getInstance().getCurrentUser().getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Transaksi transaksi = new Transaksi(
                        rs.getInt("id"),
                        Session.getInstance().getCurrentUser(),
                        new Sampah(rs.getInt("id"), rs.getString("nama"), rs.getInt("harga")),
                        rs.getInt("berat"),
                        rs.getInt("harga"),
                        rs.getString("status"),
                        rs.getDate("tanggal")
                );
                transaksiTableView.getItems().add(transaksi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addTransaksiOnAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Nasabah/AddTransaksiView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Menambahkan Admin");
            stage.showAndWait();
            loadTransaksi();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
