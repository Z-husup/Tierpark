package com.prog.tierpark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.prog.tierpark.database.DatabaseManager.initializeDatabase;

public class TierparkApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        initializeDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(TierparkApplication.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}