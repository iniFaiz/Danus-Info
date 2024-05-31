package controller.buyer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.event.ActionEvent;
import model.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DanusanMenuController {

    @FXML
    private TableView<Product> tableView;

    @FXML
    public void initialize() {
        // Create a Product object
        Product product = new Product("Risol", 3000.0, 10, "Food", 5);

        // Add the Product object to an ObservableList
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(product);

        // Set the items of the TableView
        tableView.setItems(products);

        // Declare and initialize the columns
        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<Product, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<Product, Integer> NumberSalesColumn = new TableColumn<>("Number Sales");
        TableColumn<Product, Void> actionColumn = new TableColumn<>("Action");

        // Set the cell value factories for the columns
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        NumberSalesColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumberSales()).asObject());

        actionColumn.setCellFactory(new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<Product, Void>() {

                    private final Button buyButton = new Button("Buy");

                    {
                        buyButton.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            System.out.println("Buying " + product.getName());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(buyButton);
                        }
                    }
                };
            }
        });
        tableView.getColumns().clear();
        tableView.getColumns().addAll(nameColumn, priceColumn, quantityColumn, categoryColumn, NumberSalesColumn, actionColumn);
    }
}