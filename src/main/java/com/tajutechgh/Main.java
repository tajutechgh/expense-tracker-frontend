package com.tajutechgh;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(new VBox(), 300, 300);

        stage.setTitle("Mohammed Abdul-Aziz");
        stage.setScene(scene);
        stage.show();
    }
}