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
import project.tubespbo.Models.Admin;
import project.tubespbo.Models.Entity;
import project.tubespbo.Util.DatabaseConnection;
import project.tubespbo.Util.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagementAdminController {

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
    private Label jumlahAdmin;

    @FXML
    private TableView<Admin> userTableView;

    @FXML
    private TableColumn<Admin, String> usernameColumn;

    @FXML
    private TableColumn<Admin, String> passwordColumn;

    @FXML
    private TableColumn<Admin, String> roleColumn;

    @FXML
    private TableColumn<Admin, String> namaLengkapColumn;

    @FXML
    private TableColumn<Admin, String> alamatColumn;

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
        ManagementSampahController managementSampahController= loader.getController();
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
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        namaLengkapColumn.setCellValueFactory(new PropertyValueFactory<>("namaLengkap"));
        alamatColumn.setCellValueFactory(new PropertyValueFactory<>("alamat"));

        Entity currentUser = Session.getInstance().getCurrentUser();

        usernameLabel.setText(currentUser.getUsername() + " (Admin)");
        loadUser();
    }

    private void loadUser() {
        userTableView.getItems().clear();
        String query = "SELECT id, username, password, role, nama_lengkap as namaLengkap, alamat FROM users WHERE role ='admin'";
        DatabaseConnection dbConnection = new DatabaseConnection();
        int adminCount = 0;
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Admin admin = new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("namaLengkap"),
                        rs.getString("alamat")
                );
                userTableView.getItems().add(admin);
                adminCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jumlahAdmin.setText("" + adminCount);
    }

    @FXML
    private void tambahOnAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/tubespbo/Views/Admin/AddAdminView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Menambahkan Admin");
            stage.showAndWait();
            loadUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteOnAction() {
        Admin selectedItem = userTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialog Konfirmasi");
        alert.setHeaderText("Delete Admin");
        alert.setContentText("Apakah yakin ingin menghapus pengguna admin ini?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            String query = "DELETE FROM users WHERE id = ?";
            DatabaseConnection dbConnection = new DatabaseConnection();
            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, selectedItem.getId());
                pstmt.executeUpdate();
                loadUser();

            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
}
