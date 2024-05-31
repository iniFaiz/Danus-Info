package controller.buyer;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MarketplaceController implements Initializable {
    @FXML
    private ListView<String> danusanList;
    @FXML
    private TableView<TableData> tableView;
    @FXML
    private TableColumn<TableData, String> danusanColumn;
    @FXML
    private TableColumn<TableData, String> lokasiColumn;
    @FXML
    private StackPane dashContent;
    @FXML
    private TextArea danusDetail; // Add this line

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        // Add an item to the ListView
        danusanList.getItems().add("DANUS 1");

        // Define how to populate the cells of the TableView
        danusanColumn.setCellValueFactory(new PropertyValueFactory<>("danusan"));
        lokasiColumn.setCellValueFactory(new PropertyValueFactory<>("lokasi"));

        // Add an item to the TableView
        tableView.getItems().add(new TableData("DANUS 1", "Selasar, Gedung E"));

        // Add a click event handler to the TableView
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click
                loadDanusanMenu();
            }
        });
    }

    private void loadDanusanMenu() {
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
        // Tampilkan detail Danus 1 di TextArea
        danusDetail.setText("Detail for " + selectedItem);
        // Tampilkan DanusanMenu di BuyerDashboard
        showDanusanMenu();
    }

    private void showDanusanMenu() {
        // Add your implementation here
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