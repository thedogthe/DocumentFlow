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

public class InvoiceController {
    @FXML private TextField numberField;
    @FXML private DatePicker datePicker;
    @FXML private TextField userField;
    @FXML private TextField amountField;
    @FXML private TextField currencyField;
    @FXML private TextField exchangeRateField;
    @FXML private TextField productField;
    @FXML private TextField quantityField;

    private ObservableList<Document> documents;
    private Stage dialogStage;

    public static void showInvoiceDialog(ObservableList<Document> documents) {
        try {
            FXMLLoader loader = new FXMLLoader(InvoiceController.class.getResource("/com/the/documentflow/view/InvoiceForm.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Создать накладную");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            InvoiceController controller = loader.getController();
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
            Invoice invoice = new Invoice(
                    numberField.getText(),
                    datePicker.getValue(),
                    userField.getText(),
                    Double.parseDouble(amountField.getText()),
                    currencyField.getText(),
                    Double.parseDouble(exchangeRateField.getText()),
                    productField.getText(),
                    Double.parseDouble(quantityField.getText())
            );
            documents.add(invoice);
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (numberField.getText() == null || numberField.getText().length() == 0) {
            errorMessage += "Не заполнен номер!\n";
        }
        if (datePicker.getValue() == null) {
            errorMessage += "Не заполнена дата!\n";
        }
        if (userField.getText() == null || userField.getText().length() == 0) {
            errorMessage += "Не заполнен пользователь!\n";
        }
        if (amountField.getText() == null || amountField.getText().length() == 0) {
            errorMessage += "Не заполнена сумма!\n";
        } else {
            try {
                Double.parseDouble(amountField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Сумма должна быть числом!\n";
            }
        }
        // Аналогичные проверки для остальных полей

        if (errorMessage.length() == 0) {
            return true;
        } else {
            showAlert("Неверные данные", "Пожалуйста, исправьте ошибки", errorMessage);
            return false;
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}