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

public class ManagementSampahController {

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
    private TableView<Sampah> sampahTableView;

    @FXML
    private TableColumn<Sampah, String> namaColumn;

    @FXML
    private TableColumn<Sampah, Double> hargaColumn;

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
        dashboardAdminController.setStage((Stage) dashboardButton.getScene().getWindow()); // Set the stage instance
        // Get the current scene and set its root to the new scene's root
        Scene currentScene = dashboardButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void managementSampahOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/ManagementSampahView.fxml"));
        Parent root = loader.load();
        ManagementSampahController managementSampahController= loader.getController();
        managementSampahController.setStage((Stage) managementSampahButton.getScene().getWindow()); // Set the stage instance
        // Get the current scene and set its root to the new scene's root
        Scene currentScene = managementSampahButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void managementUserOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/ManagementUserAdminView.fxml"));
        Parent root = loader.load();
        ManagementUserController managementUserController = loader.getController();
        managementUserController.setStage((Stage) managementUserButton.getScene().getWindow()); // Set the stage instance
        // Get the current scene and set its root to the new scene's root
        Scene currentScene = managementUserButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void transaksiOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/TransaksiAdminView.fxml"));
        Parent root = loader.load();
        TransaksiAdminController transaksiAdminController = loader.getController();
        transaksiAdminController.setStage((Stage) transaksiButton.getScene().getWindow()); // Set the stage instance
        // Get the current scene and set its root to the new scene's root
        Scene currentScene = transaksiButton.getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void historiTransaksiOnAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/HistoriTransaksiAdminView.fxml"));
        Parent root = loader.load();
        HistoriTransaksiAdminController historiTransaksiAdminController = loader.getController();
        historiTransaksiAdminController.setStage((Stage) historiTransaksiButton.getScene().getWindow()); // Set the stage instance
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
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));

        Entity currentUser = Session.getInstance().getCurrentUser();

        // Display the username in the label
        usernameLabel.setText(currentUser.getUsername() + " (Admin)");
        loadTrashItems();
    }

    private void loadTrashItems() {
        sampahTableView.getItems().clear();
        String query = "SELECT id, nama_sampah AS nama, harga_sampah AS harga FROM sampah";
        DatabaseConnection dbConnection = new DatabaseConnection(); // Create an instance
        try (Connection conn = dbConnection.getConnection(); // Call the method on the instance
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Sampah item = new Sampah(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getInt("harga")
                );
                sampahTableView.getItems().add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void tambahOnAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/AddSampahView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Menambahkan Sampah");
            stage.showAndWait();
            loadTrashItems(); // Reload trash items after adding a new one
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteOnAction() {
        Sampah selectedItem = sampahTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialog Konfirmasi");
        alert.setHeaderText("Delete Sampah");
        alert.setContentText("Apakah yakin ingin menghapus sampah ini?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            String query = "DELETE FROM sampah WHERE id = ?";
            DatabaseConnection dbConnection = new DatabaseConnection();
            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, selectedItem.getId());
                pstmt.executeUpdate();
                loadTrashItems();

            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
}
