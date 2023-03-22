package com.example.project3;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;

public class DateTest extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        TextArea outputTextArea = new TextArea();
        Scene scene = new Scene(new StackPane(outputTextArea), 400, 300);
        stage.setScene(scene);
        stage.show();
        runTests(outputTextArea);
    }

    private void runTests(TextArea outputTextArea) {
        Platform.runLater(() -> {
            Date date = new Date("03/20/2023");
            Assert.assertTrue(date.isValid(outputTextArea));

            Date invalidDate = new Date("02/29/2023");
            Assert.assertFalse(invalidDate.isValid(outputTextArea));

            Date validDate1 = new Date("01/01/2000");
            Assert.assertTrue(validDate1.isValid(outputTextArea));

            Date validDate2 = new Date("12/31/2999");
            Assert.assertTrue(validDate2.isValid(outputTextArea));

            Date invalidMonth = new Date("13/20/2023");
            Assert.assertFalse(invalidMonth.isValid(outputTextArea));

            Date invalidDay = new Date("02/31/2023");
            Assert.assertFalse(invalidDay.isValid(outputTextArea));

            Date invalidYear = new Date("02/20/-1");
            Assert.assertFalse(invalidYear.isValid(outputTextArea));

            Date invalidFormat = new Date("2023-03-20");
            Assert.assertFalse(invalidFormat.isValid(outputTextArea));

            stage.close();
        });
    }

    @Test
    public void test() {
        // This method is empty on purpose, because the tests are run in the JavaFX application thread.
        // If you remove this method, the test will not run correctly.
    }
}