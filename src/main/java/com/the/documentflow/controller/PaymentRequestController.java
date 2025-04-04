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
            // Используем getResourceAsStream для более надежной загрузки
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PaymentRequestController.class.getResource("/com/the/documentflow/view/PaymentRequestForm.fxml"));
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
            showAlert("Ошибка", "Не удалось загрузить форму",
                    "Проверьте наличие файла PaymentRequestForm.fxml в resources/com/the/documentflow/view/");
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
            PaymentRequest paymentRequest = new PaymentRequest(
                    numberField.getText(),
                    datePicker.getValue(),
                    userField.getText(),
                    Double.parseDouble(amountField.getText()),
                    counterpartyField.getText(),
                    currencyField.getText(),
                    Double.parseDouble(exchangeRateField.getText()),
                    Double.parseDouble(commissionField.getText())
            );
            documents.add(paymentRequest);
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        // Валидация всех полей
        if (numberField.getText() == null || numberField.getText().trim().isEmpty()) {
            errorMessage.append("Не заполнен номер!\n");
        }
        if (datePicker.getValue() == null) {
            errorMessage.append("Не заполнена дата!\n");
        }
        if (userField.getText() == null || userField.getText().trim().isEmpty()) {
            errorMessage.append("Не заполнен пользователь!\n");
        }
        if (!isValidDouble(amountField.getText())) {
            errorMessage.append("Неверная сумма!\n");
        }
        if (counterpartyField.getText() == null || counterpartyField.getText().trim().isEmpty()) {
            errorMessage.append("Не заполнен контрагент!\n");
        }
        if (currencyField.getText() == null || currencyField.getText().trim().isEmpty()) {
            errorMessage.append("Не заполнена валюта!\n");
        }
        if (!isValidDouble(exchangeRateField.getText())) {
            errorMessage.append("Неверный курс валюты!\n");
        }
        if (!isValidDouble(commissionField.getText())) {
            errorMessage.append("Неверная комиссия!\n");
        }

        if (!errorMessage.isEmpty()) {
            showAlert("Ошибка ввода", "Исправьте следующие ошибки:", errorMessage.toString());
            return false;
        }
        return true;
    }

    private boolean isValidDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}