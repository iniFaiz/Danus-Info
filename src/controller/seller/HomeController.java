package controller.seller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HomeController {

    @FXML
    private Label lblProductCount;

    private Connection conn;

    public HomeController() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        if (conn == null) {
            throw new IllegalStateException("Connection is null. Please call setConnection() before initialize().");
        }
        lblProductCount.setText("Produk yang tersedia: " + getProductCount());
    }

    @FXML
    public void handleBackToDashboardAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/seller/sellerDashboard.fxml"));
            Stage stage = (Stage) lblProductCount.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getProductCount() {
        int count = 0;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM danus");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}