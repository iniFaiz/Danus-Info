package controller.seller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Danus;
import model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TambahProductController {

    @FXML
    private TextField namaproductField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField categoryField;

    private Connection conn;
    private Danus selectedDanus;
    private Product addedProduct; // The product that has been added

    private DanusanMenuController danusanMenuController;

    public TambahProductController(DanusanMenuController danusanMenuController) {
        this.danusanMenuController = danusanMenuController;
    }

    public TambahProductController() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSelectedDanus(Danus danus) {
        this.selectedDanus = danus;
        System.out.println("setSelectedDanus called, selectedDanus: " + selectedDanus);
    }

    @FXML
    public void handleConfirm() {
        if (selectedDanus == null) {
            // Handle the case where selectedDanus is not set
            System.out.println("selectedDanus is not set");
            return;
        }

        String productName = namaproductField.getText();
        double productPrice = Double.parseDouble(priceField.getText());
        int productQuantity = Integer.parseInt(quantityField.getText());
        String productCategory = categoryField.getText();

        addedProduct = new Product(productName, productPrice, productQuantity, productCategory, 0); // Replace with actual product

        try {
            String sql = "INSERT INTO product (productName, price, quantity, category, nr_sales, danus_id) VALUES (?, ?, ?, ?, 0, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productName);
            pstmt.setDouble(2, productPrice);
            pstmt.setInt(3, productQuantity);
            pstmt.setString(4, productCategory);
            pstmt.setInt(5, selectedDanus.getId());
            pstmt.executeUpdate();

            // Add the product to the selected Danus
            selectedDanus.addProduct(addedProduct);

            // Add the selected Danus to the danusList in DanusanMenuController
            danusanMenuController.addDanus(selectedDanus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getNewProduct() {
        String name = namaproductField.getText();
        double price = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        String category = categoryField.getText();

        return new Product(name, price, quantity, category);
    }
    public void handleCancel() {
        // Your code here
    }
}