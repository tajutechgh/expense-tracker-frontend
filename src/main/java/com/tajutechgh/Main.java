package com.tajutechgh;

import com.tajutechgh.utils.ViewNavigator;
import com.tajutechgh.views.LonginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Expense Tracker");

        ViewNavigator.setMainStage(stage);

        new LonginView().show();
    }
}