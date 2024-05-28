package project.tubespbo.Controller.Nasabah;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import project.tubespbo.Controller.Admin.*;

import java.io.IOException;;

public class DashboardUserController {

    private Stage stage;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button transaksiButton;

    @FXML
    private Button historiTransaksiButton;

    @FXML
    private Button logoutButton;


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
        DashboardUserController dashboardUserController = loader.getController();
        dashboardUserController.setStage((Stage) dashboardButton.getScene().getWindow()); // Set the stage instance
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
        Scene currentScene = historiTransaksiButton .getScene();
        currentScene.setRoot(root);
    }

    @FXML
    private void logoutOnAction(ActionEvent e) throws IOException {
    }

}
