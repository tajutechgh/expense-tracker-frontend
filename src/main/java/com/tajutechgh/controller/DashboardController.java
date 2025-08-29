package com.tajutechgh.controller;

import com.dialog.CreateNewCategoryDialog;
import com.model.User;
import com.tajutechgh.util.SqlUtil;
import com.tajutechgh.view.DashboardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DashboardController {

    private User user;

    private DashboardView dashboardView;

    public DashboardController(DashboardView dashboardView) {

        this.dashboardView = dashboardView;

        fetchUserData();

        initialize();
    }

    private void fetchUserData() {

        user = SqlUtil.getUserByEmail(dashboardView.getEmail());
    }

    private void initialize() {

        addMenuActions();
    }

    private void addMenuActions() {

        dashboardView.getCreateteCategoryMenuItem().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                new CreateNewCategoryDialog(user).showAndWait();
            }
        });
    }
}
