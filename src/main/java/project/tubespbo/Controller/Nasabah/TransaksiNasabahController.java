package project.tubespbo.Controller.Nasabah;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
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
public class TransaksiNasabahController {

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
    private void dashboardOnAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Nasabah/DashboardUserView.fxml"));
        Parent root = loader.load();
        DashboardNasabahController dashboardNasabahController = loader.getController();
        dashboardNasabahController.setStage((Stage) dashboardButton.getScene().getWindow());
        Scene currentScene = dashboardButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void transaksiOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Nasabah/TransaksiUserView.fxml"));
        Parent root = loader.load();
        TransaksiNasabahController transaksiNasabahController = loader.getController();
        transaksiNasabahController.setStage((Stage) transaksiButton.getScene().getWindow());
        Scene currentScene = transaksiButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void historiTransaksiOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Nasabah/HistoriTransaksiUserView.fxml"));
        Parent root = loader.load();
        HistoriTransaksiNasabahController historiTransaksiNasabahController = loader.getController();
        historiTransaksiNasabahController.setStage((Stage) historiTransaksiButton.getScene().getWindow());
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
            stage.setTitle("Menambahkan Transaksi");
            stage.showAndWait();
            loadTransaksi();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteTransaksiOnAction() {
        Transaksi selectedItem = transaksiTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialog Konfirmasi");
        alert.setHeaderText("Delete Transaksi");
        alert.setContentText("Apakah yakin ingin menghapus transaksi ini?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            String query = "DELETE FROM transaksi WHERE id = ?";
            DatabaseConnection dbConnection = new DatabaseConnection();
            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, selectedItem.getId());
                pstmt.executeUpdate();
                loadTransaksi();

            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
}
