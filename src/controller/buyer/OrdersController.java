package controller.buyer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import model.Order; // Ganti dengan model Order Anda

import java.time.LocalDate;

public class OrdersController {
    @FXML
    private TableView<Order> tableOrdersPage;

    @FXML
    private TableColumn<Order, String> productNameColumn;

    @FXML
    private TableColumn<Order, Double> pricePaidColumn;

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order, String> orderStatusColumn;

    @FXML
    private TableColumn<Order, String> namaDanusColumn;

    // ObservableList untuk menyimpan data Order
    private ObservableList<Order> orderData;

    public void initialize() {
        // Create an Order object
        Order order = new Order("Risol", 3000.0, LocalDate.of(2024, 5, 26), "ONGOING", "DANUS 1");

        // Add the Order object to an ObservableList
        ObservableList<Order> orders = FXCollections.observableArrayList();
        orders.add(order);

        // Set the items of the TableView
        tableOrdersPage.setItems(orders);

        // Set the cell value factories for the columns
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        pricePaidColumn.setCellValueFactory(cellData -> cellData.getValue().pricePaidProperty().asObject());
        orderDateColumn.setCellValueFactory(cellData -> cellData.getValue().orderDateProperty().asString());
        orderStatusColumn.setCellValueFactory(cellData -> cellData.getValue().orderStatusProperty());
        namaDanusColumn.setCellValueFactory(cellData -> cellData.getValue().namaDanusProperty());
    }
}