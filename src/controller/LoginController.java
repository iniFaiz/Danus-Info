package controller;

import database.DatabaseConnection;
import controller.buyer.BuyerDashboardController;
import controller.seller.SellerDashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import model.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.control.Button;
import java.sql.*;

public class LoginController {
    private DatabaseConnection database;

    @FXML
    private StackPane dashContent;

    @FXML
    private Button registerSellerButton;

    @FXML
    private Button lblLogOut;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void initialize() {
        this.database = DatabaseConnection.getInstance();
    }

    @FXML
    public void handleSellerLoginAction(ActionEvent event) {
        User user = loginUser(usernameField.getText(), passwordField.getText());
        if (user instanceof Seller) {
            navigateTo("/view/seller/sellerDashboard.fxml", usernameField.getText());
        } else {
            showLoginFailedMessage();
        }
    }

    @FXML
    public void handleBuyerLoginAction(ActionEvent event) {
        User user = loginUser(usernameField.getText(), passwordField.getText());
        if (user instanceof Buyer) {
            navigateTo("/view/buyer/buyerDashboard.fxml", usernameField.getText());
        } else {
            showLoginFailedMessage();
        }
    }

    @FXML
    public void handleRegisterSellerButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText("Untuk registerasi silahkan menghubungi nomor dibawah ini\n\n" +
                "081269420666 a.n Ryan Gosling\n");

        alert.showAndWait();
    }

    public User loginUser(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM Buyer WHERE username = ? AND password = ? UNION SELECT * FROM Seller WHERE username = ? AND password = ?";
        try (Connection conn = database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            ResultSet rs = pstmt.executeQuery();

            // if a row is returned, a user exists
            if (rs.next()) {
                String role = rs.getString("role");
                if ("Buyer".equals(role)) {
                    user = new Buyer(username, password);
                } else if ("Seller".equals(role)) {
                    user = new Seller(username, password);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    private void navigateTo(String fxmlPath, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            if ("/view/buyer/buyerDashboard.fxml".equals(fxmlPath)) {
                BuyerDashboardController dashboardController = loader.getController();
                dashboardController.setUsername(username);
            } else if ("/view/seller/sellerDashboard.fxml".equals(fxmlPath)) {
                SellerDashboardController dashboardController = loader.getController();
                dashboardController.setUsername(username);
            }
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showLoginFailedMessage() {
        System.out.println("Login failed. Please try again.");
    }
}