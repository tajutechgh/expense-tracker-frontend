package com.tajutechgh.controller;

import com.component.TransactionComponent;
import com.dialog.CreateNewCategoryDialog;
import com.dialog.CreateOrEditTransactionDialog;
import com.dialog.ViewOrEditTransactionCategoryDialog;
import com.model.Transaction;
import com.model.User;
import com.tajutechgh.util.SqlUtil;
import com.tajutechgh.view.DashboardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;

public class DashboardController {

    private final int recentTransactionPageSize = 5;

    private User user;

    private DashboardView dashboardView;

    private int currentPageNum;

    private List<Transaction> recentTransactions;

    public DashboardController(DashboardView dashboardView) {

        this.dashboardView = dashboardView;

        fetchUserData();

        initialize();
    }

    private void fetchUserData() {

        user = SqlUtil.getUserByEmail(dashboardView.getEmail());

        createRecentTransactionsComponent();
    }

    private void createRecentTransactionsComponent(){

        recentTransactions = SqlUtil.getRecentTransactionByUser(user.getId(), currentPageNum, recentTransactionPageSize);

        if (recentTransactions == null) return;;

        for (Transaction transaction : recentTransactions){

            dashboardView.getRecentTransactionBox().getChildren().add(

                    new TransactionComponent(DashboardController.this, transaction, user)
            );
        }
    }

    private void initialize() {

        addMenuActions();

        addRecentTransactionActions();
    }

    private void addMenuActions() {

        dashboardView.getCreateteCategoryMenuItem().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                new CreateNewCategoryDialog(user).showAndWait();
            }
        });

        dashboardView.getViewCategoriesMenuItem().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                new ViewOrEditTransactionCategoryDialog(user, DashboardController.this).showAndWait();
            }
        });
    }

    private void addRecentTransactionActions() {

        dashboardView.getAddTransactionButton().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                new CreateOrEditTransactionDialog(user, false).showAndWait();
            }
        });
    }
}
