package controller.seller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Danus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.io.IOException;
import java.sql.Connection;

public class TambahDanusDialog extends Dialog<Pair<String, String>> {
    @FXML
    private TextField tfNamaDanus;
    @FXML
    private TextField tfLokasi;

    private DanusanMenuController danusanMenuController;

    public TambahDanusDialog(DanusanMenuController danusanMenuController) {
        this.danusanMenuController = danusanMenuController;

        setTitle("Buat Danus Baru!");
        setHeaderText("Masukkan detail Danus:");

        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return new Pair<>(tfNamaDanus.getText(), tfLokasi.getText());
            }
            return null;
        });
    }

    @FXML
    private void handleConfirm() {
        // Create a new Danus with the input fields
        Danus newDanus = new Danus(tfNamaDanus.getText(), tfLokasi.getText());

        // Add the new Danus to the danusList in DanusanMenuController
        danusanMenuController.addDanus(newDanus);

        // Close the dialog
        Stage stage = (Stage) tfNamaDanus.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        // Close the dialog
        Stage stage = (Stage) tfNamaDanus.getScene().getWindow();
        stage.close();
    }

    public void showDialog() {
        try {
            // Load the FXML file for the dialog
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/seller/marketplace/tambahdanus.fxml"));
            fxmlLoader.setController(this);

            // Create a new stage for the dialog
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(fxmlLoader.load()));

            // Show the dialog
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // No need to initialize TextField here as it's done via FXML
    }

    public Danus getNewDanus() {
        return new Danus(tfNamaDanus.getText(), tfLokasi.getText());
    }
}