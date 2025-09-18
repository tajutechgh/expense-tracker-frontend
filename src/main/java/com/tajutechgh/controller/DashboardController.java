package com.tajutechgh.controller;

import com.component.TransactionComponent;
import com.dialog.CreateNewCategoryDialog;
import com.dialog.CreateOrEditTransactionDialog;
import com.dialog.ViewOrEditTransactionCategoryDialog;
import com.model.MonthlyFinance;
import com.model.Transaction;
import com.model.User;
import com.tajutechgh.util.SqlUtil;
import com.tajutechgh.view.DashboardView;
import com.tajutechgh.view.LoginView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class DashboardController {

    private final int recentTransactionPageSize = 5;

    private User user;

    private DashboardView dashboardView;

    private int currentPageNum;
    private int currentYear;

    private List<Transaction> recentTransactions;
    private List<Transaction> allTransactionsByYear;

    public DashboardController(DashboardView dashboardView) {

        this.dashboardView = dashboardView;

        currentYear = dashboardView.getYearComboBox().getValue();

        fetchUserData();

        initialize();
    }

    public void fetchUserData() {

        // loading animations
        dashboardView.getLoadAnimationPane().setVisible(true);

        // remove all children from the dashboard
        dashboardView.getRecentTransactionBox().getChildren().clear();

        user = SqlUtil.getUserByEmail(dashboardView.getEmail());

        // get transactions for the year
        allTransactionsByYear = SqlUtil.getAllTransactionByUserInYear(user.getId(), currentYear);

        dashboardView.getTransactionTable().setItems(calculateMonthlyFinances());

        // get recent transactions by user
        createRecentTransactionsComponent();

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    Thread.sleep(1500);

                    dashboardView.getLoadAnimationPane().setVisible(false);

                }catch (InterruptedException exception){

                    exception.printStackTrace();
                }
            }
        }).start();
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

    private ObservableList<MonthlyFinance> calculateMonthlyFinances(){

        double[] incomeCounter = new double[12];
        double[] expenseCounter = new double[12];

        for (Transaction transactionByYear : allTransactionsByYear){

            LocalDate transactionDate = transactionByYear.getTransactionDate();

            if (transactionByYear.getTransactionTyper().equalsIgnoreCase("income")){

                incomeCounter[transactionDate.getMonth().getValue() - 1] += transactionByYear.getTransactionAmount();

            }else {

                expenseCounter[transactionDate.getMonth().getValue() - 1] += transactionByYear.getTransactionAmount();
            }
        }

        ObservableList<MonthlyFinance> monthlyFinances = FXCollections.observableArrayList();

        for (int i = 0; i < 12; i++){

            MonthlyFinance monthlyFinance = new MonthlyFinance(

                    Month.of(i + 1).name(),

                    new BigDecimal(String.valueOf(incomeCounter[i])),

                    new BigDecimal(String.valueOf(expenseCounter[i]))
            );

            monthlyFinances.add(monthlyFinance);
        }

        return monthlyFinances;
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

        dashboardView.getLogoutMenuItem().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                new LoginView().show();
            }
        });
    }

    private void addRecentTransactionActions() {

        dashboardView.getAddTransactionButton().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                new CreateOrEditTransactionDialog(DashboardController.this, false).showAndWait();
            }
        });
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
