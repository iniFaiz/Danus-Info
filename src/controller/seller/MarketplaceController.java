package controller.seller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Danus;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Added import statement
import java.util.ResourceBundle;


public class MarketplaceController implements Initializable {
    @FXML
    private TableView<TableData> tableView;
    @FXML
    private TableColumn<TableData, String> danusanColumn;
    @FXML
    private TableColumn<TableData, String> lokasiColumn;
    @FXML
    private StackPane dashContent;

    private DanusanMenuController danusanMenuController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        danusanMenuController = new DanusanMenuController();
        danusanColumn.setCellValueFactory(new PropertyValueFactory<>("danusan"));
        lokasiColumn.setCellValueFactory(new PropertyValueFactory<>("lokasi"));

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    deleteButton.setOnAction(event -> {
                        TableData tableData = getTableView().getItems().get(getIndex());
                        deleteDatabase(tableData.getDanusan());
                    });

                    setGraphic(deleteButton);
                }
            }
        });

        loadDanusData();

        tableView.setRowFactory(tv -> {
            TableRow<TableData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    TableData clickedRow = row.getItem();
                    loadDanusanMenu(clickedRow.getDanusan());
                }
            });
            return row ;
        });
    }

    private void loadDanusData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            ObservableList<TableData> data = FXCollections.observableArrayList();
            String query = "SELECT * FROM danus";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String danusan = rs.getString("nama");
                String lokasi = rs.getString("lokasi");
                data.add(new TableData(danusan, lokasi));
            }
            tableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDanusanMenu(String danusan) {
        try {
            // Create a new database for this danusan if it doesn't exist
            String databaseUrl = "jdbc:sqlite:" + danusan + ".db";
            Connection conn = DriverManager.getConnection(databaseUrl);
            // You might want to create tables for this new database here

            // Load the FXML file for the danusan menu
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/seller/marketplace/danusan_menu.fxml"));
            Parent danusanMenuContent = fxmlLoader.load();

            // Get the controller of the danusan menu and pass the connection to it
            DanusanMenuController controller = fxmlLoader.getController();
            controller.setConnection(conn);

            dashContent.getChildren().setAll(danusanMenuContent);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    public class DatabaseConnection {
        private static final String DATABASE_URL = "jdbc:sqlite:mydatabase.db";

        public static Connection getConnection() throws SQLException {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new SQLException(e);
            }
            return DriverManager.getConnection(DATABASE_URL);
        }
    }


    @FXML
    private void handleTambahDanus() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/seller/marketplace/tambahdanus.fxml"));
            TambahDanusDialog controller = new TambahDanusDialog(danusanMenuController);
            fxmlLoader.setController(controller);

            // Create a new stage for the pop up
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(fxmlLoader.load()));

            // Show the pop up and wait for it to be closed
            stage.showAndWait();

            // Get the new Danus from the dialog
            Danus newDanus = controller.getNewDanus();

            // Insert the new Danus into the database
            try (Connection conn = DatabaseConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO danus (nama, lokasi) VALUES (?, ?)");
                stmt.setString(1, newDanus.getNamaDanus());
                stmt.setString(2, newDanus.getLokasi());
                stmt.executeUpdate();
            }

            // Reload the TableView
            loadDanusData();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TableColumn<TableData, Void> actionColumn;


    private void deleteDatabase(String danusan) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM danus WHERE nama = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, danusan);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Deleted the danus: " + danusan);
            } else {
                System.out.println("Failed to delete the danus.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadDanusData();
    }

    public static class TableData {
        private final SimpleStringProperty danusan;
        private final SimpleStringProperty lokasi;

        public TableData(String danusan, String lokasi) {
            this.danusan = new SimpleStringProperty(danusan);
            this.lokasi = new SimpleStringProperty(lokasi);
        }

        public String getDanusan() {
            return danusan.get();
        }

        public String getLokasi() {
            return lokasi.get();
        }
    }
}