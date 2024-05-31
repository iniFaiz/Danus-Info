package app;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import controller.LoginController;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Image icon = new Image(getClass().getResourceAsStream("/view/img/brand/LOGO APS DANUS.png"));
        primaryStage.getIcons().add(icon);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();

        primaryStage.setTitle("Danus-Info");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }
}