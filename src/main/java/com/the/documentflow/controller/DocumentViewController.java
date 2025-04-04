package com.the.documentflow.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.the.documentflow.model.*;

import java.io.IOException;

public class DocumentViewController {
    @FXML private TextArea documentDetailsArea;

    private Stage dialogStage;

    public static void showDocumentView(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader(DocumentViewController.class.getResource("/com/the/documentflow/view/DocumentView.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Просмотр документа");

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DocumentViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.showDocument(document);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void showDocument(Document document) {
        StringBuilder sb = new StringBuilder();
        sb.append("Тип: ").append(document.getType()).append("\n");
        sb.append("Номер: ").append(document.getNumber()).append("\n");
        sb.append("Дата: ").append(document.getDate()).append("\n");
        sb.append("Пользователь: ").append(document.getUser()).append("\n");
        sb.append("Сумма: ").append(document.getAmount()).append("\n");

        if (document instanceof Invoice invoice) {
            sb.append("Валюта: ").append(invoice.getCurrency()).append("\n");
            sb.append("Курс валюты: ").append(invoice.getExchangeRate()).append("\n");
            sb.append("Товар: ").append(invoice.getProduct()).append("\n");
            sb.append("Количество: ").append(invoice.getQuantity()).append("\n");
        } else if (document instanceof Payment payment) {
            sb.append("Сотрудник: ").append(payment.getEmployee()).append("\n");
        } else if (document instanceof PaymentRequest pr) {
            sb.append("Контрагент: ").append(pr.getCounterparty()).append("\n");
            sb.append("Валюта: ").append(pr.getCurrency()).append("\n");
            sb.append("Курс валюты: ").append(pr.getExchangeRate()).append("\n");
            sb.append("Комиссия: ").append(pr.getCommission()).append("\n");
        }

        documentDetailsArea.setText(sb.toString());
    }
}