package com.tajutechgh;

import com.tajutechgh.util.ViewNavigator;
import com.tajutechgh.view.LonginView;
import com.tajutechgh.view.RegisterView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Expense Tracker");

        ViewNavigator.setMainStage(stage);

        new RegisterView().show();
    }
}