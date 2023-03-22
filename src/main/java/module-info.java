module com.example.project3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires junit;


    opens com.example.project3 to javafx.fxml;
    exports com.example.project3;
}