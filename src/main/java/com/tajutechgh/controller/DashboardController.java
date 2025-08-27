package com.tajutechgh.controller;

import com.dialog.CreateNewCategoryDialog;
import com.tajutechgh.view.DashboardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DashboardController {

    private DashboardView dashboardView;

    public DashboardController(DashboardView dashboardView) {

        this.dashboardView = dashboardView;

        initialize();
    }

    private void initialize() {

        addMenuActions();
    }

    private void addMenuActions() {

        dashboardView.getCreateteCategoryMenuItem().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                new CreateNewCategoryDialog().showAndWait();
            }
        });
    }
}
