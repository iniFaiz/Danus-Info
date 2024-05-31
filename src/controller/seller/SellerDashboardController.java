package controller.seller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.Optional;

public class SellerDashboardController {

    @FXML
    private Label lblUsrName;

    @FXML
    private Pane dashContent;

    @FXML
    private Button lblLogOut; // Corrected from btnLogOut to lblLogOut to match the FXML fx:id

    @FXML
    private Button btnOrders;

    // This method is called after all @FXML annotated members have been injected
    @FXML
    public void initialize() {
        handleHomeClick();
        btnOrders.setOnAction(event -> openOrdersScene());
    }

    @FXML
    private void handleOrdersClick() {
        // Logic for Orders button click
    }

    @FXML
    private void btnLogOutOnClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Logging Out");
        alert.setContentText("Do you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // User chose OK, navigate to the login page
            navigateToLoginPage();
        } else {
            // User chose CANCEL or closed the dialog
            alert.close();
        }
    }


    @FXML
    private void handleDanusanMenuClick() {
        try {
            // Load the danusan menu view
            Parent danusanMenuContent = FXMLLoader.load(getClass().getResource("/view/seller/marketplace/danusan_menu.fxml"));

            // Replace the existing content with the danusan menu view
            dashContent.getChildren().setAll(danusanMenuContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMarketplaceClick() {
        try {
            // Load the marketplace view
            Parent marketplaceContent = FXMLLoader.load(getClass().getResource("/view/seller/marketplace/marketplace.fxml"));

            // Replace the existing content with the marketplace view
            dashContent.getChildren().setAll(marketplaceContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleHomeClick() {
        try {
            Parent homeContent = FXMLLoader.load(getClass().getResource("/view/seller/home/home.fxml"));
            dashContent.getChildren().setAll(homeContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToLoginPage() {
        try {
            // Load the login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = loader.load();

            // Get the current stage (using any node, here assuming btnLogOut is a button in your scene)
            Stage stage = (Stage) lblLogOut.getScene().getWindow();

            // Set the scene with the login view
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, maybe show an error dialog
        }
    }

    public void setUsername(String username) {
        lblUsrName.setText(username);
    }

    private void openOrdersScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seller/order/orders.fxml"));
            Parent ordersContent = loader.load();
            dashContent.getChildren().setAll(ordersContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}