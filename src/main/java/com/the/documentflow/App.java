package com.the.documentflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Полный путь к FXML файлу
        URL fxmlLocation = getClass().getResource("/com/the/documentflow/view/MainView.fxml");

        if (fxmlLocation == null) {
            System.err.println("FXML file not found at: /com/the/documentflow/view/MainView.fxml");
            System.err.println("Actual classpath: " + System.getProperty("java.class.path"));
            throw new RuntimeException("FXML file not found");
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();

        primaryStage.setTitle("Document Flow");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}