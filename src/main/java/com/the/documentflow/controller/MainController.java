package com.the.documentflow.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import com.the.documentflow.model.*;
import java.io.*;
import java.time.LocalDate;

public class MainController {
    @FXML private ListView<Document> documentListView;
    @FXML private Button addInvoiceBtn;
    @FXML private Button addPaymentBtn;
    @FXML private Button addPaymentRequestBtn;
    @FXML private Button viewBtn;
    @FXML private Button saveBtn;
    @FXML private Button loadBtn;
    @FXML private Button deleteBtn;

    private ObservableList<Document> documents = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        documentListView.setItems(documents);
        documentListView.setCellFactory(lv -> new ListCell<Document>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    if (getItem() != null) {
                        getItem().setSelected(newVal);
                    }
                });
            }

            @Override
            protected void updateItem(Document item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    checkBox.setSelected(item.isSelected());
                    setGraphic(checkBox);
                    setText(item.getDescription());
                }
            }
        });
    }

    @FXML
    private void handleAddInvoice() {
        // Открытие диалога для создания накладной
        InvoiceController.showInvoiceDialog(documents);
    }

    @FXML
    private void handleAddPayment() {
        // Открытие диалога для создания платежки
        PaymentController.showPaymentDialog(documents);
    }

    @FXML
    private void handleAddPaymentRequest() {
        // Открытие диалога для создания заявки на оплату
        PaymentRequestController.showPaymentRequestDialog(documents);
    }

    @FXML
    private void handleView() {
        Document selected = documentListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            DocumentViewController.showDocumentView(selected);
        }
    }


    @FXML
    private void handleSave() {
        Document selected = documentListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Сохранить документ");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));
            File file = fileChooser.showSaveDialog(documentListView.getScene().getWindow());

            if (file != null) {
                try (PrintWriter writer = new PrintWriter(file)) {
                    writer.println(selected.getType());
                    writer.println(selected.getNumber());
                    writer.println(selected.getDate());
                    writer.println(selected.getUser());
                    writer.println(selected.getAmount());

                    if (selected instanceof Invoice) {
                        Invoice invoice = (Invoice) selected;
                        writer.println(invoice.getCurrency());
                        writer.println(invoice.getExchangeRate());
                        writer.println(invoice.getProduct());
                        writer.println(invoice.getQuantity());
                    } else if (selected instanceof Payment) {
                        Payment payment = (Payment) selected;
                        writer.println(payment.getEmployee());
                    } else if (selected instanceof PaymentRequest) {
                        PaymentRequest pr = (PaymentRequest) selected;
                        writer.println(pr.getCounterparty());
                        writer.println(pr.getCurrency());
                        writer.println(pr.getExchangeRate());
                        writer.println(pr.getCommission());
                    }
                } catch (IOException e) {
                    showAlert("Ошибка", "Не удалось сохранить файл", e.getMessage());
                }
            }
        }
    }

    @FXML
    private void handleLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить документ");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));
        File file = fileChooser.showOpenDialog(documentListView.getScene().getWindow());

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String type = reader.readLine();
                String number = reader.readLine();
                LocalDate date = LocalDate.parse(reader.readLine());
                String user = reader.readLine();
                double amount = Double.parseDouble(reader.readLine());

                switch (type) {
                    case "Накладная":
                        String currency = reader.readLine();
                        double exchangeRate = Double.parseDouble(reader.readLine());
                        String product = reader.readLine();
                        double quantity = Double.parseDouble(reader.readLine());
                        documents.add(new Invoice(number, date, user, amount, currency, exchangeRate, product, quantity));
                        break;
                    case "Платёжка":
                        String employee = reader.readLine();
                        documents.add(new Payment(number, date, user, amount, employee));
                        break;
                    case "Заявка на оплату":
                        String counterparty = reader.readLine();
                        String prCurrency = reader.readLine();
                        double prExchangeRate = Double.parseDouble(reader.readLine());
                        double commission = Double.parseDouble(reader.readLine());
                        documents.add(new PaymentRequest(number, date, user, amount, counterparty, prCurrency, prExchangeRate, commission));
                        break;
                }
            } catch (IOException e) {
                showAlert("Ошибка", "Не удалось загрузить файл", e.getMessage());
            }
        }
    }

    @FXML
    private void handleDelete() {
        // Создаем список для удаления
        ObservableList<Document> toRemove = documents.filtered(Document::isSelected);

        // Удаляем выбранные элементы
        documents.removeAll(toRemove);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}