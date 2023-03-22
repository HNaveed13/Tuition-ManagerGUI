package com.example.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A launcher class to initialize and create the Tuition Manager GUI.
 * @author Sharukh Khan, Hamad Naveed
 */

public class TuitionManagerMain extends Application {
    /**
     * Initializes the Tuition Manager scene by loading the TuitionManagerView.fxml file.
     * @param stage application stage
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TuitionManagerMain.class.getResource("TuitionManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Tuition Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Calls the JavaFX Application's launch() method, which starts up the project.
     */
    public static void main(String[] args) {
        launch();
    }
}