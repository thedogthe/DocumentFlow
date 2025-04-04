module com.the.documentflow {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.the.documentflow to javafx.fxml;

    opens com.the.documentflow.controller to javafx.fxml;
    exports com.the.documentflow;
}