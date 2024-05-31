package controller.buyer;

import database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.scene.layout.StackPane;

public class BuyerDashboardController implements Initializable {
    private DatabaseConnection database;

    @FXML
    private Label lblUsrName;

    @FXML
    private Button danusanMenuButton;

    @FXML
    private Pane mainPane;

    @FXML
    private Button lblLogOut;

    @FXML
    private StackPane dashContent;

    @FXML
    private ListView<String> danusanList;

    @FXML
    private TextArea danusDetail;

    private BuyerDashboardController buyerDashboardController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buyerDashboardController = this;
        setDatabase(DatabaseConnection.getInstance());
        assert lblLogOut != null : "fx:id=\"lblLogOut\" was not injected: check your FXML file 'BuyerDashboard.fxml'.";
        loadHome();
    }

    public void setDatabase(DatabaseConnection database) {
        this.database = database;
    }

    @FXML
    public void showDanusanMenu() {
        try {
            Parent danusanMenuContent = FXMLLoader.load(getClass().getResource("/view/buyer/marketplace/danusan_menu.fxml"));
            dashContent.getChildren().setAll(danusanMenuContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleListViewClick(MouseEvent event) {
        String selectedItem = danusanList.getSelectionModel().getSelectedItem();
        danusDetail.setText("Detail for " + selectedItem);
        buyerDashboardController.showDanusanMenu();
    }

    @FXML
    private void btnLogOutOnClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Logging Out");
        alert.setContentText("Do you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            clearSession();
            navigateToLoginPage();
        } else {
            alert.close();
        }
    }

    private void navigateToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = loader.load();
            if (lblLogOut != null && lblLogOut.getScene() != null && lblLogOut.getScene().getWindow() instanceof Stage) {
                Stage stage = (Stage) lblLogOut.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        lblUsrName.setText(username);
    }

    @FXML
    public void handleOrdersClick() {
        try {
            Pane ordersPane = FXMLLoader.load(getClass().getResource("/view/buyer/orders/orders.fxml"));
            dashContent.getChildren().setAll(ordersPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDanusanMenuClick() {
        try {
            Parent danusanMenuContent = FXMLLoader.load(getClass().getResource("/view/buyer/marketplace/danusan_menu.fxml"));
            dashContent.getChildren().setAll(danusanMenuContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleHomeClick() {
        try {
            Parent homeContent = FXMLLoader.load(getClass().getResource("/view/buyer/home/home.fxml"));
            dashContent.getChildren().setAll(homeContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHome() {
        try {
            Parent homeContent = FXMLLoader.load(getClass().getResource("/view/buyer/home/home.fxml"));
            dashContent.getChildren().setAll(homeContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleMarketplaceClick() {
        loadMarketplace();
    }

    private void loadMarketplace() {
        try {
            Parent marketplaceContent = FXMLLoader.load(getClass().getResource("/view/buyer/marketplace/marketplace.fxml"));
            dashContent.getChildren().setAll(marketplaceContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearSession() {
        try {
            database.clearSessionData();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblLogOut.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}