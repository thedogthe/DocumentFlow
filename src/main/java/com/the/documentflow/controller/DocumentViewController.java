package com.the.documentflow.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.the.documentflow.model.*;

public class DocumentViewController {
    @FXML private Label typeLabel;
    @FXML private Label numberLabel;
    @FXML private Label dateLabel;
    @FXML private Label userLabel;
    @FXML private Label amountLabel;
    @FXML private VBox specificFieldsContainer;
    @FXML private TextArea descriptionArea;

    private Document document;

    public void setDocument(Document document) {
        this.document = document;
        updateUI();
    }

    private void updateUI() {
        typeLabel.setText(document.getType());
        numberLabel.setText(document.getNumber());
        dateLabel.setText(document.getDate().toString());
        userLabel.setText(document.getUser());
        amountLabel.setText(String.format("%.2f", document.getAmount()));

        descriptionArea.setText(document.getDescription());

        // Clear specific fields container
        specificFieldsContainer.getChildren().clear();

        // Add specific fields based on document type
        if (document instanceof Invoice) {
            Invoice invoice = (Invoice) document;
            addFieldToContainer("Валюта:", invoice.getCurrency());
            addFieldToContainer("Курс:", String.valueOf(invoice.getExchangeRate()));
            addFieldToContainer("Товар:", invoice.getProduct());
            addFieldToContainer("Количество:", String.valueOf(invoice.getQuantity()));
        } else if (document instanceof Payment) {
            Payment payment = (Payment) document;
            addFieldToContainer("Сотрудник:", payment.getEmployee());
        } else if (document instanceof PaymentRequest) {
            PaymentRequest pr = (PaymentRequest) document;
            addFieldToContainer("Контрагент:", pr.getCounterparty());
            addFieldToContainer("Валюта:", pr.getCurrency());
            addFieldToContainer("Курс:", String.valueOf(pr.getExchangeRate()));
            addFieldToContainer("Комиссия:", String.valueOf(pr.getCommission()));
        }
    }

    private void addFieldToContainer(String labelText, String value) {
        HBox hbox = new HBox(5);
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");
        Label valueLabel = new Label(value);
        hbox.getChildren().addAll(label, valueLabel);
        specificFieldsContainer.getChildren().add(hbox);
    }

    @FXML
    private void handleClose() {
        descriptionArea.getScene().getWindow().hide();
    }
}