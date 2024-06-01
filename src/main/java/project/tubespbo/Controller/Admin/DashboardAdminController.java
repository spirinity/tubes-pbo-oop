package project.tubespbo.Controller.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import project.tubespbo.Models.Entity;
import project.tubespbo.Util.Session;

import java.io.IOException;

public class DashboardAdminController {
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
    }

    public void initialize() {
        Entity currentUser = Session.getInstance().getCurrentUser();
        usernameLabel.setText(currentUser.getUsername() + " (Admin)");
    }

}
