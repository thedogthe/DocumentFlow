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
        // Получаем выбранные документы
        ObservableList<Document> selectedDocuments = documents.filtered(Document::isSelected);

        if (selectedDocuments.isEmpty()) {
            showAlert("Ошибка", "Нет выбранных документов", "Выберите документы для сохранения");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить документы");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));
        File file = fileChooser.showSaveDialog(documentListView.getScene().getWindow());

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                // Первая строка - количество документов
                writer.println(selectedDocuments.size());

                for (Document doc : selectedDocuments) {
                    writer.println("=== DOCUMENT START ===");
                    writer.println(doc.getType());
                    writer.println(doc.getNumber());
                    writer.println(doc.getDate());
                    writer.println(doc.getUser());
                    writer.println(doc.getAmount());

                    if (doc instanceof Invoice) {
                        Invoice invoice = (Invoice) doc;
                        writer.println(invoice.getCurrency());
                        writer.println(invoice.getExchangeRate());
                        writer.println(invoice.getProduct());
                        writer.println(invoice.getQuantity());
                    } else if (doc instanceof Payment) {
                        Payment payment = (Payment) doc;
                        writer.println(payment.getEmployee());
                    } else if (doc instanceof PaymentRequest) {
                        PaymentRequest pr = (PaymentRequest) doc;
                        writer.println(pr.getCounterparty());
                        writer.println(pr.getCurrency());
                        writer.println(pr.getExchangeRate());
                        writer.println(pr.getCommission());
                    }
                    writer.println("=== DOCUMENT END ===");
                }
            } catch (IOException e) {
                showAlert("Ошибка", "Не удалось сохранить файл", e.getMessage());
            }
        }
    }

    @FXML
    private void handleLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить документы");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));
        File file = fileChooser.showOpenDialog(documentListView.getScene().getWindow());

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                // Читаем количество документов
                int docCount = Integer.parseInt(reader.readLine());

                for (int i = 0; i < docCount; i++) {
                    // Пропускаем разделитель
                    String separator = reader.readLine();
                    if (!"=== DOCUMENT START ===".equals(separator)) {
                        throw new IOException("Неверный формат файла");
                    }

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

                    // Пропускаем конечный разделитель
                    separator = reader.readLine();
                    if (!"=== DOCUMENT END ===".equals(separator)) {
                        throw new IOException("Неверный формат файла");
                    }
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