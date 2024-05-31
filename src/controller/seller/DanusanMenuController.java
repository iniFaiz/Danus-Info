package controller.seller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.event.ActionEvent;
import model.Danus;
import model.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;


public class DanusanMenuController {
    private ObservableList<Danus> danusList;

    public DanusanMenuController() {
        danusList = FXCollections.observableArrayList();
    }



    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private TableColumn<Product, Integer> NumberSalesColumn;

    @FXML
    private TableColumn<Product, Void> actionColumn;

    @FXML
    private TableView<Danus> danusTableView;


    @FXML
    private void handleDanusClick() {
        // Get the selected Danus
        selectedDanus = danusTableView.getSelectionModel().getSelectedItem();
        System.out.println("handleDanusClick called, selectedDanus: " + selectedDanus);
    }

    @FXML
    private Button btnProduct;

    private Product selectedProduct;

    private Danus selectedDanus;

    private Connection conn;

    public void setConnection(Connection conn) {
        this.conn = conn;
        // Load the data from this database
    }

    @FXML
    public void initialize() {
        // Initialize your TableView and TableColumns here
        btnProduct.setOnAction(event -> handleTambahDanus());

        danusTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click
                Danus selectedRow = danusTableView.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    // Set the selected Danus
                    selectedDanus = selectedRow;
                }
            }
        });

        // Call handleDanusClick() when the DanusanMenu is opened
        handleDanusClick();

        actionColumn.setCellFactory(new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<Product, Void>() {

                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            System.out.println("Deleting " + product.getName());
                            // Add your delete logic here
                            // For example, you can remove the product from the ObservableList
                            getTableView().getItems().remove(product);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            }
        });
        danusTableView.setItems(danusList);
        System.out.println("danusTableView items: " + danusTableView.getItems());
    }

    public Danus getSelectedDanus() {
        return selectedDanus;
    }

    public void addDanus(Danus danus) {
        danusList.add(danus);
    }

    @FXML
    private void handleTambahDanus() {
        try {
            // Load the FXML file for the pop up
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/seller/marketplace/tambahproduct.fxml"));

            // Create a new instance of TambahProductController and set it as the controller
            TambahProductController controller = new TambahProductController(this);
            fxmlLoader.setController(controller);

            // Set the selectedDanus in the controller
            if (selectedDanus != null) {
                controller.setSelectedDanus(selectedDanus);
            } else {
                System.out.println("No Danus selected");
                return;
            }

            // Create a new stage for the pop up
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(fxmlLoader.load()));

            // Show the pop up and wait for it to be closed
            stage.showAndWait();

            // Get the new Product from the dialog
            Product newProduct = controller.getNewProduct();

            // Add the new Product to the selected Danus
            if (selectedDanus != null) {
                selectedDanus.addProduct(newProduct);
            }

            addDanus(selectedDanus);

            // You can add more logic here if needed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}