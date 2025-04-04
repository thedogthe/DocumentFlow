package com.the.documentflow.controller;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import com.the.documentflow.model.*;
import javafx.collections.ObservableList;
import java.io.IOException;

public class PaymentRequestController {
    @FXML private TextField numberField;
    @FXML private DatePicker datePicker;
    @FXML private TextField userField;
    @FXML private TextField amountField;
    @FXML private TextField counterpartyField;
    @FXML private TextField currencyField;
    @FXML private TextField exchangeRateField;
    @FXML private TextField commissionField;

    private ObservableList<Document> documents;
    private Stage dialogStage;

    public static void showPaymentRequestDialog(ObservableList<Document> documents) {
        try {
            FXMLLoader loader = new FXMLLoader(PaymentRequestController.class.getResource("/com/the/documentflow/view/PaymentRequestForm.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Создать заявку на оплату");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PaymentRequestController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDocuments(documents);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDocuments(ObservableList<Document> documents) {
    }

    private void setDialogStage(Stage dialogStage) {
        
    }

    // Остальные методы аналогичны другим контроллерам
}