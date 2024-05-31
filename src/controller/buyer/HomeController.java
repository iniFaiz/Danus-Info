package controller.buyer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label; // Import for Label
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    @FXML
    private Label lblProductCount;

    public void initialize() {
        // Update lblProductCount with the number of products
        // You need to implement getProductCount() method to return the number of products
        lblProductCount.setText("Danusan yang tersedia: " + getProductCount());
    }

    @FXML
    public void handleBackToDashboardAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/buyer/buyerDashboard.fxml"));
            Stage stage = (Stage) lblProductCount.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getProductCount() {
        // Implement this method to return the number of products
        // This is a placeholder return statement
        return 0;
    }
}