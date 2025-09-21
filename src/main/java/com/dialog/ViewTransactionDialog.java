package com.dialog;

import com.component.TransactionComponent;
import com.model.Transaction;
import com.model.User;
import com.tajutechgh.controller.DashboardController;
import com.tajutechgh.util.SqlUtil;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.time.Month;
import java.util.List;

public class ViewTransactionDialog extends CustomDialog{

    private DashboardController dashboardController;
    private String monthName;

    public ViewTransactionDialog(DashboardController dashboardController, String monthName) {

        super(dashboardController.getUser());
        this.dashboardController = dashboardController;
        this.monthName = monthName;

        setTitle("View Transactions");
//        setWidth(815);
//        setHeight(500);

        ScrollPane transactionScrollPane = createTransactionScrollPane();

        getDialogPane().setContent(transactionScrollPane);
    }

    private ScrollPane createTransactionScrollPane() {

        VBox transactionVBox = new VBox(20);

        ScrollPane scrollPane = new ScrollPane(transactionVBox);

        scrollPane.setMinHeight(getHeight() - 40);
        scrollPane.setFitToWidth(true);

        List<Transaction> monthTransactions = SqlUtil.getAllTransactionByUserInYear(
                dashboardController.getUser().getId(),
                dashboardController.getCurrentYear(),
                Month.valueOf(monthName).getValue()
        );

        if (monthTransactions != null){

            for (Transaction transaction : monthTransactions){

                TransactionComponent transactionComponent = new TransactionComponent(
                        dashboardController,
                        transaction,
                        dashboardController.getUser()
                );

                transactionComponent.getStyleClass().addAll("border-light-gray");

                transactionVBox.getChildren().add(transactionComponent);
            }
        }

        return scrollPane;
    }
}
