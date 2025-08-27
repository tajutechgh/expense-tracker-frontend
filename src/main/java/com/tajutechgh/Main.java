package com.tajutechgh;

import com.tajutechgh.util.ViewNavigator;
import com.tajutechgh.view.DashboardView;
import com.tajutechgh.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Expense Tracker");

        ViewNavigator.setMainStage(stage);

//        new LoginView().show();

        new DashboardView("koo@gmail.com").show();
    }
}