package com.component;

import com.dialog.CreateOrEditTransactionDialog;
import com.model.Transaction;
import com.model.TransactionCategory;
import com.model.User;
import com.tajutechgh.controller.DashboardController;
import com.tajutechgh.util.SqlUtil;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.util.List;

public class TransactionComponent extends HBox {

    private List<TransactionCategory> listTransactionCategories;

    private Label transactionCategoryLabel;
    private Label transactionNameLabel;
    private Label transactionDateLabel;
    private Label transactionAmountLabel;
    private Button editButton;
    private Button deleteButton;

    private DashboardController dashboardController;
    private Transaction transaction;
    private User user;

    public TransactionComponent(DashboardController dashboardController, Transaction transaction, User user) {

        this.dashboardController = dashboardController;
        this.transaction = transaction;
        this.user = user;

        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().addAll("main-background", "rounded-border", "padding-10px");

        listTransactionCategories = SqlUtil.getAllTransactionCategoriesByUser(user);

        VBox categoryNameDateSection = createCategoryNameDateSection();

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        transactionAmountLabel = new Label("$" + transaction.getTransactionAmount());
        transactionAmountLabel.getStyleClass().add("text-size-md");

        if (transaction.getTransactionTyper().equalsIgnoreCase("expense")){

            transactionAmountLabel.getStyleClass().add("text-light-red");

        }else {

            transactionAmountLabel.getStyleClass().add("text-light-green");
        }

        HBox actionButtonSection = createActionButtons();

        getChildren().addAll(
                categoryNameDateSection,
                region,
                transactionAmountLabel,
                actionButtonSection
        );
    }

    private VBox createCategoryNameDateSection() {

        VBox categoryNameDateSection = new VBox();

        for (TransactionCategory category : listTransactionCategories){
            if (category.getId() == transaction.getTransactionCategoryId()){
                transactionCategoryLabel = new Label(category.getCategoryName());
                transactionCategoryLabel.setTextFill(Paint.valueOf("#" + category.getCategoryColor()));
            }
        }

        transactionNameLabel = new Label(transaction.getTransactionName());
        transactionNameLabel.getStyleClass().addAll("text-light-gray", "text-size-md");

        transactionDateLabel = new Label(transaction.getTransactionDate().toString());
        transactionDateLabel.getStyleClass().addAll("text-light-gray");

        categoryNameDateSection.getChildren().addAll(
                transactionCategoryLabel,
                transactionNameLabel,
                transactionDateLabel
        );

        return categoryNameDateSection;
    }

    private HBox createActionButtons() {

        HBox actionButtonSection = new HBox(20);

        actionButtonSection.setAlignment(Pos.CENTER);

        editButton = new Button("Edit");
        editButton.getStyleClass().addAll("text-size-md", "rounded-border", "bg-light-green", "text-white");

        editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                new CreateOrEditTransactionDialog(dashboardController, TransactionComponent.this, true).showAndWait();
            }
        });

        deleteButton = new Button("Delete");
        deleteButton.getStyleClass().addAll("text-size-md", "rounded-border", "bg-light-red", "text-white");

        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if (!SqlUtil.deleteTransaction(transaction.getId()))  return;

                // remove the component from the dashboard
                setVisible(false);
                setManaged(false);

                if (getParent() instanceof VBox){

                    ((VBox) getParent()).getChildren().remove(TransactionComponent.this);
                }
            }
        });

        actionButtonSection.getChildren().addAll(
                editButton,
                deleteButton
        );

        return actionButtonSection;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
