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

public class PaymentController {
    @FXML private TextField numberField;
    @FXML private DatePicker datePicker;
    @FXML private TextField userField;
    @FXML private TextField amountField;
    @FXML private TextField employeeField;

    private ObservableList<Document> documents;
    private Stage dialogStage;

    public static void showPaymentDialog(ObservableList<Document> documents) {
        try {
            FXMLLoader loader = new FXMLLoader(PaymentController.class.getResource("/com/the/documentflow/view/PaymentForm.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Создать платёжку");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PaymentController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDocuments(documents);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setDocuments(ObservableList<Document> documents) {
        this.documents = documents;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            Payment payment = new Payment(
                    numberField.getText(),
                    datePicker.getValue(),
                    userField.getText(),
                    Double.parseDouble(amountField.getText()),
                    employeeField.getText()
            );
            documents.add(payment);
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        // Реализация валидации аналогична InvoiceController
        return true;
    }
}