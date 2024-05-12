package project.tubespbo.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankSampahApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankSampahApplication.class.getResource("/project/tubespbo/Views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 280, 435);
        stage.setTitle("Sistem Bank Sampah");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}