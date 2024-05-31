package controller.seller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import model.Order;

import java.sql.Date;

public class OrdersController {

    @FXML
    private TableView<Order> tableOrdersPage; // Replace '?' with 'Order'

    @FXML
    private TableColumn<Order, String> productNameColumn; // Replace '?' with 'String'

    @FXML
    private TableColumn<Order, Double> pricePaidColumn; // Replace '?' with 'Double'

    @FXML
    private TableColumn<Order, Date> orderDateColumn; // Replace '?' with 'Date'

    @FXML
    private TableColumn<Order, String> orderStatusColumn; // Replace '?' with 'String'

    @FXML
    private TableColumn<Order, String> buyerNameColumn; // Replace '?' with 'String'

    @FXML
    private TableColumn<Order, Void> actionColumn; // Replace '?' with 'Void'

    @FXML
    public void initialize() {
        // Initialize your controller here. For example, you can set up the table columns and load the data.

        actionColumn.setCellFactory(new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
            @Override
            public TableCell<Order, Void> call(TableColumn<Order, Void> param) {
                return new TableCell<Order, Void>() {
                    final Button btnRetrieve = new Button("Retrieved");
                    final Button btnDelete = new Button("Delete");

                    {
                        btnRetrieve.setOnAction(event -> {
                            // Add your logic for the Retrieve button click here
                        });

                        btnDelete.setOnAction(event -> {
                            // Add your logic for the Delete button click here
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(btnRetrieve, btnDelete));
                        }
                    }
                };
            }
        });
    }
}